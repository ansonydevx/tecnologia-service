package com.onclass.tecnologia.domain.usecase;

import com.onclass.tecnologia.domain.api.TecnologiaServicePort;
import com.onclass.tecnologia.domain.enums.TechnicalMessage;
import com.onclass.tecnologia.domain.exceptions.BusinessException;
import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.onclass.tecnologia.infrastructure.entrypoints.dto.TecnologiaResumenDTO;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class TecnologiaUseCase implements TecnologiaServicePort {

    private final TecnologiaPersistencePort persistencePort;
    private final TransactionalOperator tx;

    public TecnologiaUseCase(
            TecnologiaPersistencePort persistencePort,
            TransactionalOperator tx)
    {
        this.persistencePort = persistencePort;
        this.tx = tx;
    }

    @Override
    public Mono<Tecnologia> registrar(Tecnologia tecnologia) {
        return validar(tecnologia)
                .flatMap(t -> verificarDuplicidad(t)
                        .then(Mono.defer(() -> persistencePort.save(t))));
    }

    @Override
    public Mono<Boolean> existenPorIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Mono.just(false);
        }

        return persistencePort.countByIds(ids)
                .map(count -> count.equals((long) ids.size()));
    }

    @Override
    public Flux<TecnologiaResumenDTO> obtenerPorIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }

        return persistencePort.findAllByIdIn(ids)
                .map(tecnologia ->
                        new TecnologiaResumenDTO(
                                tecnologia.id(),
                                tecnologia.nombre()
                        ));
    }

    @Override
    public Mono<Void> eliminarPorIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return Mono.empty();
        }

        return Flux.fromIterable(ids)
                .concatMap(persistencePort::deleteById)
                .then()
                .as(tx::transactional);
    }


    private Mono<Tecnologia> validar(Tecnologia t) {
        if (t.nombre() == null || t.nombre().isEmpty() || t.nombre().length() > 50) {
            return Mono.error(new BusinessException(TechnicalMessage.NOMBRE_INVALIDO));
        }
        if (t.descripcion() == null || t.descripcion().isEmpty() || t.descripcion().length() > 90) {
            return Mono.error(new BusinessException(TechnicalMessage.DESCRIPCION_INVALIDA));
        }
        return Mono.just(t);
    }

    private Mono<Void> verificarDuplicidad(Tecnologia t) {
        return persistencePort.existsByNombre(t.nombre())
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? Mono.error(new BusinessException(TechnicalMessage.TECNOLOGIA_DUPLICADA))
                        : Mono.empty()
                );
    }
}
