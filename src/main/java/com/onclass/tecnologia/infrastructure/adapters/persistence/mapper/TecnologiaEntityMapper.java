package com.onclass.tecnologia.infrastructure.adapters.persistence.mapper;

import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.infrastructure.adapters.persistence.TecnologiaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TecnologiaEntityMapper {
    Tecnologia toModel(TecnologiaEntity entity);
    TecnologiaEntity toEntity(Tecnologia tecnologia);
}
