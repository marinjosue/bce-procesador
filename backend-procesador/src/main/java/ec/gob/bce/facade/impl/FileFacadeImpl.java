/**
 * Proyecto bce-procesador.
 *
 * <p>Clase FileFacadeImpl 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.facade.impl;

import ec.gob.bce.facade.FileFacade;
import ec.gob.bce.record.request.FileUploadPayload;
import ec.gob.bce.record.request.HeaderInsertRequest;
import ec.gob.bce.record.response.FileUploadResponse;
import ec.gob.bce.service.DetailService;
import ec.gob.bce.service.HeaderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DE LA CLASE --.
 *
 * <p>Historial de cambios:
 *
 * <ul>
 *   <li>1.0.0 - Descripción del cambio inicial - Oljer Cando - 28/7/2025
 *       <!-- Añadir nuevas entradas de cambios aquí -->
 * </ul>
 *
 * @author Oljer Cando
 * @version 1.0.0 $
 * @since 28/7/2025
 */
@Component
@RequiredArgsConstructor
public class FileFacadeImpl implements FileFacade {

  private final HeaderService headerService;
  private final DetailService detailService;

  @Transactional
  @Override
  public FileUploadResponse handleUpload(FileUploadPayload payload) {
    validateFile(payload.file());
    HeaderInsertRequest headerRequest = mapToHeaderRequest(payload);
    Long headerId = headerService.insertHeader(headerRequest);
    detailService.insertDetails(headerId, payload.file());
    return new FileUploadResponse(headerId, "Archivo cargado correctamente.");
  }

  private void validateFile(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("El archivo no debe estar vacio.");
    }
  }

  /**
   * Convierte un payload de carga de archivo en un request de creacion de cabecera.
   *
   * @param payload el payload de carga de archivo
   * @return el request de creacion de cabecera
   */
  private HeaderInsertRequest mapToHeaderRequest(FileUploadPayload payload) {
    return HeaderInsertRequest.builder()
        .documentName(payload.file().getOriginalFilename())
        .fileSize(payload.file().getSize())
        .uploadDate(payload.uploadDate().toLocalDate())
        .observation(payload.observation())
        .dailyNote(payload.dailyNote())
        .build();
  }
}
