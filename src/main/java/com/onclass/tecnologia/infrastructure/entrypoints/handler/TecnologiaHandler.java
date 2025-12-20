package com.onclass.tecnologia.infrastructure.entrypoints.handler;

import com.onclass.tecnologia.domain.api.TecnologiaServicePort;
import com.onclass.tecnologia.domain.enums.TechnicalMessage;
import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TecnologiaHandler {

    private final TecnologiaServicePort tecnologiaServicePort;

    public Mono<ServerResponse> registrar(ServerRequest request) {
        return request.bodyToMono(TecnologiaDTO.class)
                .map(dto -> new Tecnologia(
                        null, dto.getNombre(), dto.getDescripcion()))
                .flatMap(tecnologiaServicePort::registrar)
                .flatMap(t -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(TechnicalMessage.TECNOLOGIA_CREADA.getMessage()));
    }
}
