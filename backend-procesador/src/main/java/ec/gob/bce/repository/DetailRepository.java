/**
 * Proyecto bce-procesador.
 *
 * <p>Clase DetailRepository 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.repository;

import java.util.List;
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
public class DetailRepository {
  private final JdbcTemplate jdbc;

  public DetailRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public void insertBatch(List<Object[]> batch) {

    String sql = """    
            INSERT INTO rdpe_detalle (
                fec_detalle, cod_cabecera_id, cod_variable_id,
                cod_periodo_id, cod_estado_id, val_variable,
                fec_login, usr_login
            ) VALUES (?, ?, ?, ?, ?, ?, NOW(), 'usr_login')
        """;
    jdbc.batchUpdate(sql, batch);
  }
}
