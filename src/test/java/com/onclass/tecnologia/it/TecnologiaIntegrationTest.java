package com.onclass.tecnologia.it;

import com.onclass.tecnologia.infrastructure.adapters.persistence.repository.TecnologiaRepository;
import com.onclass.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TecnologiaIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @BeforeEach
    void setUp() {
        tecnologiaRepository.deleteAll().block();
    }

    @Test
    void registrarTecnologia_ok() {
        TecnologiaDTO request = new TecnologiaDTO();
        request.setNombre("Spring WebFlux");
        request.setDescripcion("Programacion reactiva con Spring");

        webTestClient.post()
                .uri("/tecnologias")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("Tecnología registrada correctamente");

        StepVerifier.create(tecnologiaRepository.findByNombre("Spring WebFlux"))
                .expectNextMatches(t ->
                        t.getNombre().equals("Spring WebFlux") &&
                                t.getDescripcion().equals("Programacion reactiva con Spring")
                )
                .verifyComplete();
    }
}
