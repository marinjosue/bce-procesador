/**
 * Proyecto bce-procesador.
 *
 * <p>Clase FileFacade 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.facade;

import ec.gob.bce.record.request.FileUploadPayload;
import ec.gob.bce.record.response.FileUploadResponse;
import java.io.IOException;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DE LA INTERFACE --.
 *
 * <p>Historial de cambios:
 *
 * <ul>
 *   <li>1.0.0 - Descripción del cambio inicial - Oljer Cando - 28/7/2025
 *       <!-- Añadir nuevas entradas de cambios aquí -->
 * </ul>
 *
 * @author
 * @version 1.0.0
 * @since 28/7/2025
 */
public interface FileFacade {

  /**
   * Procesa la carga de un archivo Excel. La carga de un archivo Excel implica la creación de un
   * encabezado y la inserción de los detalles en la base de datos.
   *
   * @param request Información del archivo a subir.
   * @return Respuesta con la ID del encabezado creado y un mensaje de confirmación.
   */
  FileUploadResponse handleUpload(FileUploadPayload request);
}
