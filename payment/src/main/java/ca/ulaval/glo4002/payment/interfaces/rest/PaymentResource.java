package ca.ulaval.glo4002.payment.interfaces.rest;

import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.payment.application.PaymentApplicationService;
import ca.ulaval.glo4002.payment.application.dto.PaymentDto;
import ca.ulaval.glo4002.payment.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.modules.PaymentApplicationServiceModule;
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

        public PaymentResource() {
            Injector injector = Guice.createInjector(new PaymentApplicationServiceModule());
            this.paymentService = injector.getInstance(PaymentApplicationService.class);
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response createPaymentPost(PaymentDto paymentDto) throws ClientNotFoundException {
            Payment createdPayment = paymentService.createPayment(paymentDto);
            paymentService.payBills(createdPayment);
            PaymentToReturnDto paymentToReturnDto = paymentService.toReturnDto(createdPayment);

            return Response.created(uriInfo.getAbsolutePath()).entity(paymentToReturnDto).build();
        }
}
