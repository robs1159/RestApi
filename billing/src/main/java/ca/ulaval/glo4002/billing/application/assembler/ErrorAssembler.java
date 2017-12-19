package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.ErrorDto;
import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;

import java.util.ArrayList;
import java.util.List;

public class ErrorAssembler {

    public static ErrorDto createErrorDto(ErrorItemDto errorItem) {
        ErrorDto errorDto = new ErrorDto();
        List<ErrorItemDto> errors = new ArrayList<>();
        errors.add(errorItem);
        errorDto.errors = errors;

        return errorDto;
    }

    public static ErrorDto createErrorDtoFromList(List<ErrorItemDto> errorItemList) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.errors = errorItemList;

        return errorDto;
    }
}
