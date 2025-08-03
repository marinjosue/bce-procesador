/**
 * Proyecto bce-procesador.
 *
 * <p>Clase HeaderInsertRequest 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.record.request;

import java.time.LocalDate;
import lombok.Builder;

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
 * @author
 * @version 1.0.0 $
 * @since 28/7/2025
 */
@Builder
public record HeaderInsertRequest(
    String documentName,
    Long fileSize,
    LocalDate uploadDate,
    String observation,
    String dailyNote
) {}

