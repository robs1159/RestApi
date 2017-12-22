package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.builders.BillBuilder;
import ca.ulaval.glo4002.billing.builders.BillItemBuilder;
import ca.ulaval.glo4002.billing.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BillAssemblerTest {

    private Bill aMockedBill;
    private BillId aMockedBillId;
    private BillAssembler billAssembler;

    @Before
    public void setup() {
        aMockedBill = mock(Bill.class);
        aMockedBillId = mock(BillId.class);
        billAssembler = new BillAssembler();
    }

    @Test
    public void givenABill_whenAssemblingTheBill_thenReturnABillDto() throws Exception {
        when(aMockedBill.getId()).thenReturn(aMockedBillId);

        BillDto billDto = billAssembler.toDto(aMockedBill);

        assertTrue(billDto.dueTerm == aMockedBill.getDueTerm());
        assertTrue(billDto.total == aMockedBill.calculateTotal());
        assertNotNull(billDto);
    }

    @Test
    public void givenABill_whenAssemblingTheAcceptedBill_thenReturnAcceptedBillDto() throws Exception {
        Bill bill = new BillBuilder().withValidValues().build();
        bill.acceptQuote(ZonedDateTime.now());

        AcceptedBillToReturnDto acceptedBillToReturnDto = billAssembler.toAcceptedDto(bill);
        assertTrue(acceptedBillToReturnDto.dueTerm == bill.getDueTerm());
        assertTrue(acceptedBillToReturnDto.effectiveDate.compareTo(bill.getEffectiveDate().toInstant()) == 0);
        assertTrue(acceptedBillToReturnDto.expectedPayment.compareTo(bill.getExpectedPayment().toInstant()) == 0);
        assertNotNull(acceptedBillToReturnDto);
    }

    @Test
    public void GiverABill_WhencreateEntriesFromBills_ThenReturnEntrieList() {
        BillItem billItem1 = new BillItemBuilder().withValidValues().build();
        BillItem billItem2 = new BillItemBuilder().withValidValues().build();
        List<BillItem> billItems = new ArrayList<>();
        billItems.add(billItem1);
        billItems.add(billItem2);
        Bill bill = new Bill(new BillId(), new ClientId(), LocalDateTime.now().toString(), DueTerm.IMMEDIATE, new BigDecimal(10), billItems);
        bill.acceptQuote(ZonedDateTime.now());

        List<Bill> bills = new ArrayList<>();
        bills.add(bill);
        List<Entrie> entriesFromBills = billAssembler.createEntriesFromBills(bills);

        assertTrue(entriesFromBills.get(0).getTypeTransaction() == TransactionType.INVOICE);
    }
}