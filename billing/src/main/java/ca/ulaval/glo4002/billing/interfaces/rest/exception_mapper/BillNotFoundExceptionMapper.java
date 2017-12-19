package ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper;

import ca.ulaval.glo4002.billing.application.dto.ErrorDto;
import ca.ulaval.glo4002.billing.application.exceptions.BillNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class BillNotFoundExceptionMapper implements ExceptionMapper<BillNotFoundException> {
    @Override
    public Response toResponse(BillNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorDto()).build();
    }
}
