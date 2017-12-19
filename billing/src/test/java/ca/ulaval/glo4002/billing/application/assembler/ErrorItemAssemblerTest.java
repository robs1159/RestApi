package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorItemAssemblerTest {

    private static final String ERROR_TEXT = "error";
    private static final String DESCRIPTION_TEXT = "description";
    private static final String ENTITY_TEXT = "entity";

    @Test
    public void givenGoodParameters_whenCreatingAnErrorItemDto_thenReturnAnErrorItemDto() throws Exception {
        ErrorItemDto createdErrorItemDto = ErrorItemAssembler.createErrorItemDto(ERROR_TEXT, DESCRIPTION_TEXT, ENTITY_TEXT);

        assertEquals(createdErrorItemDto.error, ERROR_TEXT);
        assertEquals(createdErrorItemDto.description, DESCRIPTION_TEXT);
        assertEquals(createdErrorItemDto.entity, ENTITY_TEXT);
    }

}