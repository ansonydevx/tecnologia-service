package com.onclass.tecnologia.infrastructure.entrypoints.handler;

import com.onclass.tecnologia.domain.api.TecnologiaServicePort;
import com.onclass.tecnologia.domain.enums.TechnicalMessage;
import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import com.onclass.tecnologia.infrastructure.entrypoints.dto.TecnologiaExistsRequest;
import com.onclass.tecnologia.infrastructure.entrypoints.dto.TecnologiaResumenDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TecnologiaHandler {

    private final TecnologiaServicePort tecnologiaServicePort;

    public Mono<ServerResponse> registrar(ServerRequest request) {
        return request.bodyToMono(TecnologiaDTO.class)
                .map(dto -> new Tecnologia(null, dto.getNombre(), dto.getDescripcion()))
                .flatMap(tecnologiaServicePort::registrar)
                .flatMap(t -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(TechnicalMessage.TECNOLOGIA_CREADA.getMessage()));
    }

    public Mono<ServerResponse> existen(ServerRequest request) {
        return request.bodyToMono(TecnologiaExistsRequest.class)
                .flatMap(req -> tecnologiaServicePort.existenPorIds(req.ids()))
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }

    public Mono<ServerResponse> obtenerPorIds(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<List<Long>>() {})
                .flatMap(ids -> ServerResponse.ok()
                            .body(
                                    tecnologiaServicePort.obtenerPorIds(ids),
                                    TecnologiaResumenDTO.class
                            ));
    }
}
