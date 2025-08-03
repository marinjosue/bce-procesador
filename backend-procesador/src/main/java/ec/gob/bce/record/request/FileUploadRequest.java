/**
 * Proyecto bce-procesador.
 *
 * <p>Clase FileUploadRequest 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
 * @author
 * @version 1.0.0 $
 * @since 28/7/2025
 */
public record FileUploadRequest(
    @NotNull(message = "El ID del documento no puede ser nulo") Long documentId,
    @NotNull(message = "La fecha de subida no puede ser nula") LocalDateTime uploadDate,
    @NotBlank(message = "La observación no puede estar vacía") String observation,
    @NotBlank(message = "La nota diaria no puede estar vacía") String dailyNote,
    @NotNull(message = "El archivo es obligatorio")
    MultipartFile document) {}
