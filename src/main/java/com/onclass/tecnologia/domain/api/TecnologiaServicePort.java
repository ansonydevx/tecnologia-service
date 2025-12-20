package com.onclass.tecnologia.domain.api;

import com.onclass.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface TecnologiaServicePort {
    
    Mono<Tecnologia> registrar(Tecnologia tecnologia);
}
