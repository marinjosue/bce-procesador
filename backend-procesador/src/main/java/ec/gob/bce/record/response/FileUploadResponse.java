/**
 * Proyecto bce-procesador.
 *
 * <p>Clase FileUploadResponse 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.record.response;

import lombok.Builder;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DEL RECORD --.
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
@Builder
public record FileUploadResponse(Long headerId, String message) {}
