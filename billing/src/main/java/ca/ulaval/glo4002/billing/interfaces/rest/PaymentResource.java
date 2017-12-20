package ca.ulaval.glo4002.billing.interfaces.rest;

import ca.ulaval.glo4002.billing.application.PaymentApplicationService;
import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.modules.PaymentApplicationServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/payments")
public class PaymentResource {

    private PaymentApplicationService paymentService;

    @Context
    UriInfo uriInfo;

    //TODO:Mauvaise utilisation de l'injection Guice
    public PaymentResource() {
        //TODO :Pourquoi ne pas avoir utilisé votre module
        Injector injector = Guice.createInjector(new PaymentApplicationServiceModule());
        this.paymentService = injector.getInstance(PaymentApplicationService.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPaymentPost(PaymentDto paymentDto) throws ClientNotFoundException {
        //TODO : Pourquoi ne pas avoir groupé c'est 3 méthodes en une dans votre service. C'est le rôle du service de faire ça pas de la ressource.
        Payment createdPayment = paymentService.createPayment(paymentDto);
        paymentService.payBills(createdPayment);
        PaymentToReturnDto paymentToReturnDto = paymentService.toReturnDto(createdPayment);

        return Response.created(uriInfo.getAbsolutePath()).entity(paymentToReturnDto).build();
    }
}
