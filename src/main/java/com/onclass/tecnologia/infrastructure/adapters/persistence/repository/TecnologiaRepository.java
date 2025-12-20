package com.onclass.tecnologia.infrastructure.adapters.persistence.repository;

import com.onclass.tecnologia.infrastructure.adapters.persistence.TecnologiaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TecnologiaRepository extends ReactiveCrudRepository<TecnologiaEntity, Long> {

    Mono<TecnologiaEntity> findByNombre(String nombre);
    Mono<Long> countByIdIn(List<Long> ids);
}
