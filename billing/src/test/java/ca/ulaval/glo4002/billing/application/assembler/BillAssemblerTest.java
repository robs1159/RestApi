package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;
import org.junit.Before;
import org.junit.Test;

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
        Long validId = new Long(1);

        when(aMockedBill.getId()).thenReturn(aMockedBillId);
        when(aMockedBillId.getId()).thenReturn(validId);

        AcceptedBillToReturnDto acceptedBillToReturnDto = billAssembler.toAcceptedDto(aMockedBill);

        assertTrue(acceptedBillToReturnDto.dueTerm == aMockedBill.getDueTerm());
        assertTrue(acceptedBillToReturnDto.effectiveDate == aMockedBill.getEffectiveDate());
        assertTrue(acceptedBillToReturnDto.expectedPayment == aMockedBill.getExpectedPayment());
        assertNotNull(acceptedBillToReturnDto);
    }
}