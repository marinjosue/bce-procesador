/**
 * Proyecto bce-procesador.
 *
 * <p>Clase HeaderRepository 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.repository;

import ec.gob.bce.record.request.FileUploadRequest;
import ec.gob.bce.record.request.HeaderInsertRequest;
import java.time.LocalDate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
@Repository
public class HeaderRepository {
  private final JdbcTemplate jdbc;

  public HeaderRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public Long insert(HeaderInsertRequest request) {
    String sql = """
            INSERT INTO rdpe_cabecera (
                fec_registro, nom_documento, val_peso,
                txt_observacion, txt_nota, fec_login, usr_login
            ) VALUES (?, ?, ?, ?, ?, NOW(), 'usr_login')
            RETURNING cod_cabecera_id
        """;

    return jdbc.queryForObject(sql, Long.class,
        LocalDate.now(),
        request.documentName(),
        request.fileSize(),
        request.observation(),
        request.dailyNote()
    );
  }
}
