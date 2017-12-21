package ca.ulaval.glo4002.billing.interfaces.rest;

import ca.ulaval.glo4002.billing.application.PaymentApplicationService;
import ca.ulaval.glo4002.billing.application.PaymentApplicationServiceFactory;
import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/test")
public class PaymentResource {

    private PaymentApplicationService paymentService;

    @Context
    UriInfo uriInfo;

    //TODO:Mauvaise utilisation de l'injection Guice
    public PaymentResource() {
        this.paymentService = (new PaymentApplicationServiceFactory()).getPaymentApplicationService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPaymentPost(PaymentDto paymentDto) throws ClientNotFoundException {
        PaymentToReturnDto paymentToReturnDto = paymentService.createPayment(paymentDto);
        return Response.created(uriInfo.getAbsolutePath()).entity(paymentToReturnDto).build();
    }
}
