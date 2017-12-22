package ca.ulaval.glo4002.billing.domain;

import ca.ulaval.glo4002.billing.builders.BillBuilder;
import ca.ulaval.glo4002.billing.domain.exceptions.BillAlreadyAcceptedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BillTest {

    private static final ZonedDateTime ACCEPTED_DATE = ZonedDateTime.now();
    private static final BigDecimal AMOUNT_PAID_TO_ADD = new BigDecimal(50);
    private static final int AMOUNT_TO_PAY_DIVIDE = 2;

    private Bill validBill;
    private BillItem validBillItem;

    @Before
    public void setupValidItems() {
        validBill = mock(Bill.class);
        validBillItem = mock(BillItem.class);

        willReturn(new BigDecimal(10)).given(validBillItem).getPrice();
        willReturn(10).given(validBillItem).getQuantity();
        willReturn(new BigDecimal(100)).given(validBillItem).calculatePrice();
    }

    @Test
    public void givenAConstructorWithoutId_whenMakingANewBill_thenTheIdIsCreated() {
        Bill bill = new Bill(validBill.getClientId(), validBill.getCreationDate(), validBill.getDueTerm(), validBill.getItems());

        assertTrue(bill.getId().getId() >= 0);
    }

    @Test
    public void givenValidQuoteWithValidQuoteItems_whenCalculateTotal_thenTotalShouldBeEqualToQuoteItemsTotal() {
        List<BillItem> billItems = new ArrayList<>();
        billItems.add(validBillItem);
        billItems.add(validBillItem);
        Bill unBill = new BillBuilder().withValidValues().withItems(billItems).build();

        BigDecimal total = validBillItem.calculatePrice().multiply(new BigDecimal(billItems.size()));

        assertTrue(unBill.calculateTotal().equals(total));
    }

    @Test
    public void givenValidQuote_whenAcceptedByClient_thenEffectiveDateShouldBeCurrentDate() {
        Bill bill = new BillBuilder().withValidValues().build();
        bill.acceptQuote(ACCEPTED_DATE);

        assertEquals(bill.getEffectiveDate(), ACCEPTED_DATE);
    }

    @Test(expected = BillAlreadyAcceptedException.class)
    public void givenValidQuoteAndQuoteIsAlreadyAccepted_whenAcceptedASecondTime_thenShouldThrowAnException() {
        Bill bill = new BillBuilder().withValidValues().build();
        ZonedDateTime newAcceptedDateTenDaysLater = ACCEPTED_DATE.plusDays(10);
        bill.acceptQuote(ACCEPTED_DATE);

        bill.acceptQuote(newAcceptedDateTenDaysLater);
    }

    @Test
    public void givenValidQuote_whenNoPaymentDoneOnQuote_thenAmountPaidShouldBeZero() {
        Bill bill = new BillBuilder().withValidValues().withAmountPaid(BigDecimal.ZERO).build();
        Assert.assertEquals(BigDecimal.ZERO, bill.getAmountPaid());
    }

    @Test
    public void givenValidQuote_whenPaymentDoneOnQuote_thenAmountPaidShouldNotBeZero() {
        Bill bill = new BillBuilder().withValidValues().withAmountPaid(BigDecimal.TEN).build();
        Assert.assertNotEquals(BigDecimal.ZERO, bill.getAmountPaid());
    }

    @Test
    public void givenValidQuote_whenNoPaymentDoneOnQuote_thenIsPaidShouldBeFalse() {
        Assert.assertFalse(validBill.isPaid());
    }

    @Test
    public void givenValidQuote_whenAmountIsAllPayed_thenIsPaidShouldBeTrue() {
        Bill bill = new BillBuilder().withValidValues().build();
        BigDecimal amountToPay = bill.calculateTotal();
        bill = new BillBuilder().withValidValues().withAmountPaid(amountToPay).build();

        Assert.assertTrue(bill.isPaid());
    }

    @Test
    public void givenValidQuote_whenClientPaysBillWithHalfAmountToPay_thenAmountPaidShouldNotBeZeroAndIsPaidShouldBeFalse() {
        Bill bill = new BillBuilder().withValidValues().build();
        BigDecimal amountToPay = bill.calculateTotal();
        amountToPay = amountToPay.divide(new BigDecimal(AMOUNT_TO_PAY_DIVIDE));

        bill.pay(amountToPay);

        Assert.assertNotEquals(BigDecimal.ZERO, bill.getAmountPaid());
        Assert.assertFalse(bill.isPaid());
    }

    @Test(expected = BillAlreadyAcceptedException.class)
    public void givenClientAcceptBill_whenAcceptQuote_thenErrorIsThrown() throws BillAlreadyAcceptedException {
        Bill bill = new BillBuilder().withValidValues().build();
        bill.acceptQuote(ZonedDateTime.now());
        bill.acceptQuote(ZonedDateTime.now());
    }
}
