package com.onclass.tecnologia.infrastructure.adapters.persistence;

import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.onclass.tecnologia.infrastructure.adapters.persistence.mapper.TecnologiaEntityMapper;
import com.onclass.tecnologia.infrastructure.adapters.persistence.repository.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TecnologiaPersistenceAdapter implements TecnologiaPersistencePort {

    private final TecnologiaRepository repository;
    private final TecnologiaEntityMapper mapper;

    @Override
    public Mono<Long> countByIds(List<Long> ids) {
        return repository.countByIdIn(ids);
    }

    @Override
    public Mono<Boolean> existsByNombre(String nombre) {
        return repository.findByNombre(nombre)
                .map(e -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Tecnologia> save(Tecnologia tecnologia) {
        return repository.save(mapper.toEntity(tecnologia))
                .map(mapper::toModel);
    }

    @Override
    public Flux<Tecnologia> findAllByIdIn(List<Long> ids) {
        return repository.findAllByIdIn(ids)
                .map(mapper::toModel);
    }


}
