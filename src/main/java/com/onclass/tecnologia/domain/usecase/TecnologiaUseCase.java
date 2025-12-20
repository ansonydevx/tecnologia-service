package com.onclass.tecnologia.domain.usecase;

import com.onclass.tecnologia.domain.api.TecnologiaServicePort;
import com.onclass.tecnologia.domain.enums.TechnicalMessage;
import com.onclass.tecnologia.domain.exceptions.BusinessException;
import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.domain.spi.TecnologiaPersistencePort;
import reactor.core.publisher.Mono;

public class TecnologiaUseCase implements TecnologiaServicePort {

    private final TecnologiaPersistencePort persistencePort;

    public TecnologiaUseCase(TecnologiaPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<Tecnologia> registrar(Tecnologia tecnologia) {
        return validar(tecnologia)
                .then(Mono.defer(() ->
                        persistencePort.existsByNombre(tecnologia.nombre())
                                .flatMap(exists -> {
                                    if (Boolean.TRUE.equals(exists)) {
                                        return Mono.error(
                                                new BusinessException(TechnicalMessage.TECNOLOGIA_DUPLICADA));
                                    }
                                    return persistencePort.save(tecnologia);
                                })
                ));
    }

    private Mono<Void> validar(Tecnologia t) {
        if (t.nombre() == null || t.nombre().length() > 50)
            return Mono.error(new BusinessException(
                    TechnicalMessage.NOMBRE_INVALIDO));
        if (t.descripcion() == null || t.descripcion().length() > 90)
            return Mono.error(new BusinessException(
                    TechnicalMessage.DESCRIPCION_INVALIDA));

        return Mono.empty();
    }
}
