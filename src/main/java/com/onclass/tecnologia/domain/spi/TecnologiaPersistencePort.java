package com.onclass.tecnologia.domain.spi;

import com.onclass.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TecnologiaPersistencePort {

    Mono<Long> countByIds(List<Long> ids);
    Mono<Boolean> existsByNombre(String nombre);
    Mono<Tecnologia> save(Tecnologia tecnologia);
}
