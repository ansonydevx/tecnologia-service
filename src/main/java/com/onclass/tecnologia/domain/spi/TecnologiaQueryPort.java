package com.onclass.tecnologia.domain.spi;

import reactor.core.publisher.Mono;

import java.util.List;

public interface TecnologiaQueryPort {
    Mono<Boolean> existenTecnologias(List<Long> tecnologiaIds);
}
