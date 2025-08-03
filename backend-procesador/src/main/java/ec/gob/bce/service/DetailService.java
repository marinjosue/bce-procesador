/**
 * Proyecto bce-procesador.
 *
 * <p>Clase DetailService 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.service;

import org.springframework.web.multipart.MultipartFile;

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
public interface DetailService {
  /**
   * Carga los detalles de un archivo de Excel, es decir, los valores asociados a
   * cada variable para cada registro.
   *
   * @param headerId Identificador del encabezado asociado al archivo.
   * @param file Archivo de Excel que contiene los detalles.
   */
  void insertDetails(Long headerId, MultipartFile file);
}
