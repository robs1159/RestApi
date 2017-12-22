package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.EntrieDto;
import ca.ulaval.glo4002.billing.application.dto.LedgerDto;
import ca.ulaval.glo4002.billing.builders.BillBuilder;
import ca.ulaval.glo4002.billing.domain.*;
import org.junit.Before;
import org.junit.Test;

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
        Bill bill = new BillBuilder().withValidValues().build();
        bill.acceptQuote(ZonedDateTime.now());

        List<Bill> bills = new ArrayList<>();
        bills.add(bill);
        List<Entrie> entriesFromBills = billAssembler.createEntriesFromBills(bills);

        assertTrue(entriesFromBills.get(0).getTypeTransaction() == TransactionType.INVOICE);
    }

    @Test
    public void GiverANotActiveBill_WhencreateEntriesFromBills_ThenReturnEntrieList() {
        Bill bill = new BillBuilder().withValidValues().build();

        bill.acceptQuote(ZonedDateTime.now());
        bill.deleteQuote();

        List<Bill> bills = new ArrayList<>();
        bills.add(bill);
        List<Entrie> entriesFromBills = billAssembler.createEntriesFromBills(bills);

        assertTrue(entriesFromBills.get(0).getTypeTransaction() == TransactionType.INVOICE_CANCELLED);
        assertTrue(entriesFromBills.get(0).getAmount() == bill.calculateTotal().floatValue());
        assertTrue(entriesFromBills.get(0).getDate().compareTo(bill.getEffectiveDate().toInstant()) == 0);
        assertTrue(entriesFromBills.get(0).getClientId() == bill.getClientId());
        assertTrue(entriesFromBills.get(0).getTypeOperation() == OperationType.CREDIT);
    }

    @Test
    public void GiverALedger_WhentoLedgerDto_ThenReturnLedgerDtoList() {
        Ledger ledger = new Ledger(0, new ArrayList<Entrie>());

        List<Ledger> ledgers = new ArrayList<>();
        ledgers.add(ledger);

        List<LedgerDto> ledgerDtos = billAssembler.toLedgerDto(ledgers);

        assertTrue(ledgerDtos.get(0).accountid == 0);
    }

    @Test
    public void GiverAEntrie_WhentoEntrieDto_ThenReturnEntrieDto() {
        Entrie entrie = new Entrie(ZonedDateTime.now().toInstant(), TransactionType.INVOICE, new ClientId(), OperationType.DEBIT, 10);

        EntrieDto entrieDto = billAssembler.toEntrieDto(entrie);

        assertTrue(entrieDto.amount == entrie.getAmount());
        assertTrue(entrieDto.clientId == entrie.getClientId().getClientId());
        assertTrue(entrieDto.date == entrie.getDate());
        assertTrue(entrieDto.typeOperation == entrie.getTypeOperation());
        assertTrue(entrieDto.typeTransaction == entrie.getTypeTransaction());
    }
}