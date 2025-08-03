/**
 * Proyecto bce-procesador.
 *
 * <p>Clase FileUploadControllerImpl 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.controller.impl;

import ec.gob.bce.controller.FileUploadController;
import ec.gob.bce.facade.FileFacade;
import ec.gob.bce.record.request.FileUploadPayload;
import ec.gob.bce.record.response.FileUploadResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

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
/** Implementación del controlador para la carga de archivos. */
@RestController
@Slf4j
@RequiredArgsConstructor
public class FileUploadControllerImpl implements FileUploadController {
  private final FileFacade fileFacade;

  @Override
  public ResponseEntity<FileUploadResponse> uploadFile(@ModelAttribute FileUploadPayload request) {
    return ResponseEntity.ok(fileFacade.handleUpload(request));
  }
}
