package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillToReturnDto;
import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillId;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BillAssemblerTest {

    private Bill aMockedBill;
    private BillId aMockedBillId;

    @Before
    public void setup() {
        aMockedBill = mock(Bill.class);
        aMockedBillId = mock(BillId.class);
    }

    @Test
    public void givenABill_whenAssemblingTheBill_thenReturnABillDto() throws Exception {

        when(aMockedBill.getId()).thenReturn(aMockedBillId);

        BillAssembler billAssembler = new BillAssembler();
        BillToReturnDto billToReturnDto = billAssembler.toDto(aMockedBill);

        verify(aMockedBillId, times(2)).getBillId();
        verify(aMockedBill).calculateTotal();
        verify(aMockedBill).getDueTerm();
        assertNotNull(billToReturnDto);
    }

    @Test
    public void givenABill_whenAssemblingTheAcceptedBill_thenReturnAcceptedBillDto() throws Exception {
        Long validId = new Long(1);

        when(aMockedBill.getId()).thenReturn(aMockedBillId);
        when(aMockedBillId.getBillId()).thenReturn(validId);
        BillAssembler billAssembler = new BillAssembler();

        AcceptedBillToReturnDto acceptedBillToReturnDto = billAssembler.toAcceptedDtoReturn(aMockedBill);
        verify(aMockedBill).getId();
        verify(aMockedBill).getDueTerm();
        verify(aMockedBill).getEffectiveDate();
        verify(aMockedBill).getExpectedPayment();
        assertNotNull(acceptedBillToReturnDto);
    }
}