package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;

public class ErrorItemAssembler {

    public static ErrorItemDto createErrorItemDto(String error, String description, String entity) {
        ErrorItemDto errorItemDto = new ErrorItemDto();
        errorItemDto.error = error;
        errorItemDto.description = description;
        errorItemDto.entity = entity;

        return errorItemDto;
    }
}
