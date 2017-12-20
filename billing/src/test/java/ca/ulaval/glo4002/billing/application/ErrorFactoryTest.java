package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.dto.ErrorDto;
import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ErrorFactoryTest {

    @Test
    public void givenASingleErrorItemDto_whenCreatingAnErrorDto_thenReturnAnErrorDtoWithAListOfOneError() throws Exception {
        ErrorItemDto errorItemDto = new ErrorItemDto();
        errorItemDto.description = "Test d'erreur";
        ErrorDto errorDto = ErrorFactory.createErrorDto(errorItemDto);

        assertTrue(errorDto.errors.get(0).description == errorItemDto.description);
    }
}