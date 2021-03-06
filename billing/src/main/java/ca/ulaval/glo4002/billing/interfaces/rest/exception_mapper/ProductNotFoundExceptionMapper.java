package ca.ulaval.glo4002.billing.interfaces.rest.exception_mapper;

import ca.ulaval.glo4002.billing.application.ErrorFactory;
import ca.ulaval.glo4002.billing.application.ErrorItemFactory;
import ca.ulaval.glo4002.billing.application.dto.ErrorDto;
import ca.ulaval.glo4002.billing.application.dto.ErrorItemDto;
import ca.ulaval.glo4002.billing.domain.ProductId;
import ca.ulaval.glo4002.billing.domain.exceptions.ProductNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ProductNotFoundExceptionMapper implements ExceptionMapper<ProductNotFoundException> {

    private static final String ERROR_TYPE = "not found";
    private static final String ENTITY = "product";

    @Override
    public Response toResponse(ProductNotFoundException e) {
        ErrorDto errorDto = this.createErrorDto(e.getProductId());
        return Response.status(Response.Status.BAD_REQUEST).entity(errorDto).build();
    }

    private ErrorDto createErrorDto(ProductId productId) {
        String description = ENTITY + " " + productId + " " + ERROR_TYPE;
        ErrorItemDto errorItemDto = ErrorItemFactory.createErrorItemDto(ERROR_TYPE, description, ENTITY);

        return ErrorFactory.createErrorDto(errorItemDto);
    }
}
