package ec.gob.bce.utils;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    public static int getIntFromCell(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            throw new IllegalArgumentException("La celda está vacía.");
        }

        return switch (cell.getCellType()) {
            case NUMERIC -> {
                double val = cell.getNumericCellValue();
                if (val % 1 != 0) {
                    throw new IllegalArgumentException("El valor no es entero: " + val);
                }
                if (val < Integer.MIN_VALUE || val > Integer.MAX_VALUE) {
                    throw new ArithmeticException("Valor fuera de rango int: " + val);
                }
                yield (int) val;
            }
            case STRING -> {
                String text = cell.getStringCellValue().trim();
                if (!text.matches("-?\\d+")) {
                    throw new IllegalArgumentException("Texto no es un entero válido: '" + text + "'");
                }
                try {
                    yield Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    throw new ArithmeticException("Número fuera de rango int: '" + text + "'");
                }
            }
            case FORMULA -> {
                try {
                    double val = cell.getNumericCellValue();
                    if (val % 1 != 0) {
                        throw new IllegalArgumentException("Fórmula no da entero: " + val);
                    }
                    yield (int) val;
                } catch (Exception e) {
                    throw new IllegalArgumentException("Fórmula inválida.");
                }
            }
            default -> throw new IllegalArgumentException("Tipo de celda no soportado: " + cell.getCellType());
        };
    }
}
