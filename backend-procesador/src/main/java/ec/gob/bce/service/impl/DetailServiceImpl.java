package ec.gob.bce.service.impl;

import ec.gob.bce.repository.DetailRepository;
import ec.gob.bce.repository.VariableRepository;
import ec.gob.bce.service.DetailService;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


import ec.gob.bce.utils.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Servicio que gestiona la carga de detalles (valores por variable) para un archivo procesado.
 *
 * <p>Historial de cambios:
 *
 * <ul>
 *   <li>1.0.0 - Implementación inicial con validación de variables - Oljer Cando - 28/7/2025
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

  private final VariableRepository variableRepository;
  private final DetailRepository detailRepository;

  private static final int BATCH_SIZE = 500;

  @Override
  public void insertDetails(Long headerId, MultipartFile file) {
    Map<String, Integer> variableMap = variableRepository.getNormalizedVariableNameToIdMap();
    List<Object[]> batch = new ArrayList<>();

    AtomicInteger totalRows = new AtomicInteger(0);
    AtomicInteger totalInserted = new AtomicInteger(0);
    AtomicInteger totalErrors = new AtomicInteger(0);
    int colInitial = 0;



    try (InputStream stream = file.getInputStream();
        Workbook wb = new XSSFWorkbook(stream)) {
      Sheet sheet = wb.getSheetAt(0);
      //Row headerRow = sheet.getRow(0);
      Row headerRow = findHeaderRow(sheet);
      validateHeaderExists(headerRow);

      List<String> columnNames = getNormalizedHeaderNames(headerRow);
      validateUnknownColumns(columnNames, variableMap);
      List<String> validColumns = buildValidColumns(columnNames, variableMap);
      for (int fila = 1; fila <= sheet.getLastRowNum(); fila++) {
        System.out.println("fila==="+fila);
        Row row = sheet.getRow(fila);

        if (row == null) continue;

        totalRows.incrementAndGet();
        processRow(colInitial,row, fila, headerId, validColumns, variableMap, batch, totalInserted, totalErrors);
      }

      flushBatch(batch);
      logTotals(totalRows.get(), totalInserted.get(), totalErrors.get());

    } catch (Exception e) {
      throw new RuntimeException("Error al procesar las filas del detalle " + e.getMessage(), e);
    }
  }

  private void validateHeaderExists(Row headerRow) {
    if (headerRow == null) {
      throw new IllegalStateException("Missing header row");
    }
  }

  private List<String> getNormalizedHeaderNames(Row headerRow) {
    List<String> columnNames = new ArrayList<>();
    for (Cell cell : headerRow) {
      columnNames.add(normalize(cell.getStringCellValue()));
    }
    return columnNames;
  }

  private void validateUnknownColumns(List<String> columnNames, Map<String, Integer> variableMap) {
    List<String> unknown = findUnknownVariables(columnNames, variableMap);
    if (!unknown.isEmpty()) {
      throw new IllegalArgumentException(
          "Variables desconocidas en la cabecera del archivo: " + String.join(", ", unknown));
    }
  }

  private List<String> buildValidColumns(
      List<String> columnNames, Map<String, Integer> variableMap) {
    return columnNames.stream().map(name -> variableMap.containsKey(name) ? name : null).toList();
  }

  private void processRow(int codigoColumna,
      Row row,
      int rowIndex,
      Long headerId,
      List<String> validColumns,
      Map<String, Integer> variableMap,
      List<Object[]> batch,
      AtomicInteger inserted,
      AtomicInteger errors) {
    for (int col = row.getFirstCellNum() ; col < row.getLastCellNum(); col++) {
      String variableKey = validColumns.get(col-row.getFirstCellNum());
      if (variableKey == null) continue;

      BigDecimal value = extractValue(row.getCell(col));
      System.out.println("value===="+value);
      if (value == null) {
        errors.incrementAndGet();
        continue;
      }

      Integer variableId = variableMap.get(variableKey);
      if (variableId == null) {
        errors.incrementAndGet();
        continue;
      }

      batch.add(new Object[] {LocalDate.now(), headerId, variableId, 1, 1, value});
      inserted.incrementAndGet();

      if (batch.size() >= BATCH_SIZE) {
        flushBatch(batch);
      }
    }
  }

  private void flushBatch(List<Object[]> batch) {
    if (!batch.isEmpty()) {
      detailRepository.insertBatch(batch);
      batch.clear();
    }
  }

  private void logTotals(int total, int inserted, int errors) {
    System.out.printf("Total rows: %d, Inserted: %d, Errors: %d%n", total, inserted, errors);
  }

  /**
   * Detecta columnas del archivo que no existen como variables en la base de datos.
   *
   * @param columnNames nombres extraídos del Excel
   * @param variableMap mapa de variables válidas (nombre → id)
   * @return lista de nombres no reconocidos
   */
  private List<String> findUnknownVariables(
      List<String> columnNames, Map<String, Integer> variableMap) {
    return columnNames.stream().filter(col -> !variableMap.containsKey(normalize(col))).toList();
  }

  private BigDecimal extractValue(Cell cell) {
    return switch (cell.getCellType()) {
      case NUMERIC -> BigDecimal.valueOf(cell.getNumericCellValue());
      case STRING -> {
        try {
          yield new BigDecimal(cell.getStringCellValue().trim());
        } catch (NumberFormatException e) {
          yield null;
        }
      }
      default -> null;
    };
  }

  private String normalize(String input) {
    return input == null ? "" : input.trim().toLowerCase().replaceAll("[^a-z0-9]", "");
  }

  private Row findHeaderRow(Sheet sheet) {
    for (int i = 0; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      if (row != null && !isRowEmpty(row)) {
        System.out.println("row.getRowNum()==="+row.getRowNum());
        return row; // Primera fila no vacía → se considera encabezado
      }
    }
    throw new IllegalStateException("No se encontró una fila de encabezado válida.");
  }


  private boolean isRowEmpty(Row row) {
    if (row == null) return true;
    for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
      Cell cell = row.getCell(i);
      if (cell != null && cell.getCellType() != CellType.BLANK) {
        if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().trim().isEmpty()) {
          return false;
        } else if (cell.getCellType() != CellType.STRING) {
          return false;
        }
      }
    }
    return true;
  }
}
