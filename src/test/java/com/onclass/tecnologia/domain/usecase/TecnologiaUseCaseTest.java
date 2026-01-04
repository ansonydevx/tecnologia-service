package com.onclass.tecnologia.domain.usecase;

import com.onclass.tecnologia.domain.enums.TechnicalMessage;
import com.onclass.tecnologia.domain.exceptions.BusinessException;
import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.domain.spi.TecnologiaPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class TecnologiaUseCaseTest {

    private TecnologiaPersistencePort persistencePort;
    private TecnologiaUseCase useCase;
    private TransactionalOperator tx;

    @BeforeEach
    void setup() {
        persistencePort = Mockito.mock(TecnologiaPersistencePort.class);
        tx = Mockito.mock(TransactionalOperator.class);

        when(tx.transactional(Mockito.<Mono<?>>any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        useCase = new TecnologiaUseCase(persistencePort, tx);
    }

    @Test
    void deberiaRegistrarTecnologiaCorrectamente() {
        Tecnologia t = new Tecnologia(null, "Java", "Lenguaje backend");

        when(persistencePort.existsByNombre("Java"))
                .thenReturn(Mono.just(false));
        when(persistencePort.save(t))
                .thenReturn(Mono.just(t));

        StepVerifier.create(useCase.registrar(t))
                .expectNext(t)
                .verifyComplete();
    }

    @Test
    void deberiaFallarSiNombreNulo() {
        Tecnologia t = new Tecnologia(null, null, "Lenguaje backend");

        StepVerifier.create(useCase.registrar(t))
                .expectErrorMatches(ex ->
                        ex instanceof BusinessException &&
                                ((BusinessException) ex)
                                        .getTechnicalMessage()
                                        .equals(TechnicalMessage.NOMBRE_INVALIDO))
                .verify();

        verifyNoInteractions(persistencePort);
    }

    @Test
    void deberiaFallarSiDescripcionMayorA90() {
        String desc = "a".repeat(91);
        Tecnologia t = new Tecnologia(null, "Java", desc);

        StepVerifier.create(useCase.registrar(t))
                .expectErrorMatches(ex ->
                        ((BusinessException) ex)
                                .getTechnicalMessage()
                                .equals(TechnicalMessage.DESCRIPCION_INVALIDA))
                .verify();
    }

    @Test
    void deberiaFallarSiTecnologiaDuplicada() {
        Tecnologia t = new Tecnologia(null, "Java", "desc");

        when(persistencePort.existsByNombre("Java"))
                .thenReturn(Mono.just(true));

        StepVerifier.create(useCase.registrar(t))
                .expectErrorMatches(ex ->
                        ((BusinessException) ex)
                                .getTechnicalMessage()
                                .equals(TechnicalMessage.TECNOLOGIA_DUPLICADA))
                .verify();
    }
}
