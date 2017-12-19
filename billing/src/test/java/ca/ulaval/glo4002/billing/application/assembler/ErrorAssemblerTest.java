package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.ErrorDto;
import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ErrorAssemblerTest {

    @Test
    public void givenASingleErrorItemDto_whenCreatingAnErrorDto_thenReturnAnErrorDtoWithAListOfOneError() throws Exception {
        ErrorDto errorDto = ErrorAssembler.createErrorDto(new ErrorItemDto());

        assertTrue(errorDto.errors.size() == 1);
    }

    @Test
    public void givenAListOfThreeErrorItemDto_whenCreatingAnErrorDto_thenReturnAnErrorDtoWithAListOfThreeErrors() throws Exception {
        List<ErrorItemDto> errorItemDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            errorItemDtos.add(new ErrorItemDto());
        }

        ErrorDto errorDto = ErrorAssembler.createErrorDtoFromList(errorItemDtos);

        assertTrue(errorDto.errors.size() == 3);

    }
}