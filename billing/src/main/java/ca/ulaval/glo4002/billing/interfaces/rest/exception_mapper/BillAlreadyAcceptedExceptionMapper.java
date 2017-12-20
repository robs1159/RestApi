package ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper;

import ca.ulaval.glo4002.billing.application.ErrorFactory;
import ca.ulaval.glo4002.billing.application.ErrorItemFactory;
import ca.ulaval.glo4002.billing.application.dto.ErrorDto;
import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;
import ca.ulaval.glo4002.billing.domain.exceptions.BillAlreadyAcceptedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class BillAlreadyAcceptedExceptionMapper implements ExceptionMapper<BillAlreadyAcceptedException> {

    private static final String ERROR_TYPE = "wrong status";
    private static final String ENTITY = "invoice";
    private static final String DESCRIPTION = "Invoice already accepted";

    @Override
    public Response toResponse(BillAlreadyAcceptedException e) {
        ErrorDto errorDto = this.createErrorDto();
        return Response.status(Response.Status.BAD_REQUEST).entity(errorDto).build();
    }

    private ErrorDto createErrorDto() {
        ErrorItemDto errorItemDto = ErrorItemFactory.createErrorItemDto(ERROR_TYPE, DESCRIPTION, ENTITY);

        return ErrorFactory.createErrorDto(errorItemDto);
    }
}
