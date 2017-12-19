package ca.ulaval.glo4002.billing.interfaces.rest;

import ca.ulaval.glo4002.billing.application.BillApplicationService;
import ca.ulaval.glo4002.billing.application.BillApplicationServiceFactory;
import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.BillToReturnDto;
import ca.ulaval.glo4002.billing.application.exceptions.BillAlreadyAcceptedException;
import ca.ulaval.glo4002.billing.application.exceptions.BillItemAsANegativeValueException;
import ca.ulaval.glo4002.billing.application.exceptions.BillNotFoundException;
import ca.ulaval.glo4002.billing.domain.bill.BillId;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/bills")
public class BillResource {

    private BillApplicationService billService;

    @Context
    UriInfo uriInfo;

    public BillResource() {
        this.billService = BillApplicationServiceFactory.getBillApplicationService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBillingPost(BillDto billDto) throws ClientNotFoundException, ProductNotFoundException, BillItemAsANegativeValueException {
        BillToReturnDto createdBill = billService.createBill(billDto);
        return Response.created(uriInfo.getAbsolutePath()).entity(createdBill).build();
    }

    @Path("/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptQuote(@PathParam("id") Long billId) throws BillNotFoundException, BillAlreadyAcceptedException {
        BillId billIdToAccept = new BillId(billId);

        AcceptedBillToReturnDto acceptedBill = billService.acceptQuote(billIdToAccept);

        return Response.accepted().entity(acceptedBill).build();
    }
}