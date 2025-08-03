/**
 * Proyecto bce-procesador.
 *
 * <p>Clase HeaderServiceImpl 28/7/2025.
 *
 * <p>Copyright 2025 Epicas.
 *
 * <p>Todos los derechos reservados.
 */
package ec.gob.bce.service.impl;

import ec.gob.bce.record.request.HeaderInsertRequest;
import ec.gob.bce.repository.HeaderRepository;
import ec.gob.bce.service.HeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
 * @author Oljer Cando
 * @version 1.0.0 $
 * @since 28/7/2025
 */
@Service
@RequiredArgsConstructor
public class HeaderServiceImpl implements HeaderService {

  private final HeaderRepository repository;

  @Override
  public Long insertHeader(HeaderInsertRequest request) {
    return repository.insert(request);
  }
}
