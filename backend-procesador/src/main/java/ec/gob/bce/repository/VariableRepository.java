/**
 * Proyecto bce-procesador.
 *
 * <p>Clase VariableRepository 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.repository;

import java.util.HashMap;
import java.util.Map;
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
public class VariableRepository {
  private final JdbcTemplate jdbc;

  public VariableRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public Map<String, Integer> getNormalizedVariableNameToIdMap() {
    String sql = "SELECT nom_variable, cod_variable_id FROM rdpe_variable";
    return jdbc.query(
        sql,
        rs -> {
          Map<String, Integer> map = new HashMap<>();
          while (rs.next()) {
            String raw = rs.getString("nom_variable");
            String normalized = normalize(raw);
            map.put(normalized, rs.getInt("cod_variable_id"));
          }
          return map;
        });
  }

  private String normalize(String input) {
    return input == null ? "" : input
        .trim()
        .toLowerCase()
        .replaceAll("[^a-z0-9]", "");
  }

}
