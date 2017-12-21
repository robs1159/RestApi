package ca.ulaval.glo4002.billing.interfaces.rest;

import ca.ulaval.glo4002.billing.application.BillApplicationService;
import ca.ulaval.glo4002.billing.application.BillApplicationServiceFactory;
import ca.ulaval.glo4002.billing.application.dto.*;
import ca.ulaval.glo4002.billing.application.repositories.BillItemAsANegativeValueException;
import ca.ulaval.glo4002.billing.application.repositories.BillNotFoundException;
import ca.ulaval.glo4002.billing.domain.BillId;
import ca.ulaval.glo4002.billing.domain.exceptions.BillAlreadyAcceptedException;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.domain.exceptions.ProductNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class BillResource {

    //TODO:Ajout de test unitaire sur la ressource
    private BillApplicationService billService;

    @Context
    UriInfo uriInfo;

    public BillResource() {
        this.billService = (new BillApplicationServiceFactory()).getBillApplicationService();
    }

    @Path("bills")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBillingPost(BillDto billDto) throws ClientNotFoundException, ProductNotFoundException, BillItemAsANegativeValueException {
        BillDto createdBill = billService.createBill(billDto);
        return Response.created(uriInfo.getAbsolutePath()).entity(createdBill).build();
    }

    @Path("bills/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptQuote(@PathParam("id") Long billId) throws BillNotFoundException, BillAlreadyAcceptedException {
        BillId billIdToAccept = new BillId(billId);
        AcceptedBillToReturnDto acceptedBill = billService.acceptQuote(billIdToAccept);

        return Response.status(200).entity(acceptedBill).build();
    }

    @Path("bills/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuote(@PathParam("id") Long billId) throws BillNotFoundException, BillAlreadyAcceptedException {
        BillId billIdToDelete = new BillId(billId);

        BillDto billDto = billService.deleteQuote(billIdToDelete);

        return Response.status(202).entity(new ClientDto()).build();
    }

    @Path("payments")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPaymentPost(PaymentDto paymentDto) throws ClientNotFoundException {
        PaymentToReturnDto paymentToReturnDto = billService.createPayment(paymentDto);
        return Response.created(uriInfo.getAbsolutePath()).entity(paymentToReturnDto).build();
    }
}