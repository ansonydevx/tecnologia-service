package com.onclass.tecnologia.infrastructure.entrypoints.router;

import com.onclass.tecnologia.infrastructure.entrypoints.handler.TecnologiaHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/tecnologias",
                    beanClass = TecnologiaHandler.class,
                    beanMethod = "registrar",
                    method = org.springframework.web.bind.annotation.RequestMethod.POST,
            ),
            @RouterOperation(
                    path = "/tecnologias/exists",
                    beanClass = TecnologiaHandler.class,
                    beanMethod = "existen",
                    method = org.springframework.web.bind.annotation.RequestMethod.POST
            ),
            @RouterOperation(
                    path = "/tecnologias/by-ids",
                    beanClass = TecnologiaHandler.class,
                    beanMethod = "obtenerPorIds",
                    method = org.springframework.web.bind.annotation.RequestMethod.POST
            ),
            @RouterOperation(
                    path = "/tecnologias/delete-by-ids",
                    beanClass = TecnologiaHandler.class,
                    beanMethod = "eliminarPorIds",
                    method = org.springframework.web.bind.annotation.RequestMethod.POST
            ),
    })
    public RouterFunction<ServerResponse> routerFunction(TecnologiaHandler handler) {
        return RouterFunctions.route()
                .POST("/tecnologias", handler::registrar)
                .POST("/tecnologias/exists", handler::existen)
                .POST("/tecnologias/by-ids", handler::obtenerPorIds)
                .POST("/tecnologias/delete-by-ids", handler::eliminarPorIds)
                .build();
    }
}
