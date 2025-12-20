package com.onclass.tecnologia.domain.exceptions;

import com.onclass.tecnologia.domain.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final TechnicalMessage technicalMessage;

    public BusinessException(TechnicalMessage technicalMessage) {
        super(technicalMessage.toString());
        this.technicalMessage = technicalMessage;
    }
}
