package com.onclass.tecnologia.application.config;

import com.onclass.tecnologia.domain.api.TecnologiaServicePort;
import com.onclass.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.onclass.tecnologia.domain.usecase.TecnologiaUseCase;
import com.onclass.tecnologia.infrastructure.adapters.persistence.TecnologiaPersistenceAdapter;
import com.onclass.tecnologia.infrastructure.adapters.persistence.mapper.TecnologiaEntityMapper;
import com.onclass.tecnologia.infrastructure.adapters.persistence.repository.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

    private final TecnologiaRepository tecnologiaRepository;
    private final TecnologiaEntityMapper tecnologiaEntityMapper;
    private final TransactionalOperator transactionalOperator;

    @Bean
    public TecnologiaPersistencePort tecnologiaPersistencePort() {
        return new TecnologiaPersistenceAdapter(tecnologiaRepository, tecnologiaEntityMapper);
    }

    @Bean
    public TecnologiaServicePort tecnologiaServicePort(TecnologiaPersistencePort tecnologiaPersistencePort) {
        return new TecnologiaUseCase(tecnologiaPersistencePort, transactionalOperator);
    }
}
