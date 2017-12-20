package ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper;


import ca.ulaval.glo4002.billing.application.ErrorFactory;
import ca.ulaval.glo4002.billing.application.ErrorItemFactory;
import ca.ulaval.glo4002.billing.application.dto.ErrorDto;
import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ClientNotFoundExceptionMapper implements ExceptionMapper<ClientNotFoundException> {

    private static final String ERROR_TYPE = "not found";
    private static final String ENTITY = "client";

    @Override
    public Response toResponse(ClientNotFoundException e) {
        ErrorDto errorDto = this.createErrorDto(e.getClientId());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorDto).build();
    }

    private ErrorDto createErrorDto(ClientId clientId) {
        String description = ENTITY + " " + clientId + " " + ERROR_TYPE;
        ErrorItemDto errorItemDto = ErrorItemFactory.createErrorItemDto(ERROR_TYPE, description, ENTITY);

        return ErrorFactory.createErrorDto(errorItemDto);
    }
}
