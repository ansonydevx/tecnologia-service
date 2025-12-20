package com.onclass.tecnologia.domain.api;

import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TecnologiaQueryServicePort {
    Mono<Boolean> existenPorIds(List<Long> ids);
}
