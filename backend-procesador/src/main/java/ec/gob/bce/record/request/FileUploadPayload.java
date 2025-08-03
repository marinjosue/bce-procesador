/**
 * Proyecto bce-procesador.
 *
 * <p>Clase FileUploadPayload 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

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
public record FileUploadPayload(
    @NotNull(message = "Upload date is required") LocalDateTime uploadDate,
    @NotNull(message = "File size is required") Long fileSize,
    String documentName,
    @NotBlank(message = "La observación no puede estar vacía") String observation,
    @NotBlank(message = "La nota diaria no puede estar vacía") String dailyNote,
    @NotNull(message = "File is required") MultipartFile file) {}
