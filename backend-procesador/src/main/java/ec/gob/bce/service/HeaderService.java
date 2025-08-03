/**
 * Proyecto bce-procesador.
 *
 * <p>Clase HeaderService 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.service;

import ec.gob.bce.record.request.HeaderInsertRequest;

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
public interface HeaderService {
  /**
   * Inserta un encabezado en la base de datos.
   *
   * @param request Información del encabezado a insertar.
   * @return Identificador del encabezado insertado.
   */
  Long insertHeader(HeaderInsertRequest request);
}
