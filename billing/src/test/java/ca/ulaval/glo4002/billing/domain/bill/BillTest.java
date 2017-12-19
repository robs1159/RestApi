package ca.ulaval.glo4002.billing.domain.bill;

import ca.ulaval.glo4002.billing.application.exceptions.BillAlreadyAcceptedException;
import ca.ulaval.glo4002.billing.test.utils.builders.builders.BillBuilder;
import ca.ulaval.glo4002.billing.test.utils.builders.builders.BillItemBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BillTest {

    private static final LocalDateTime ACCEPTED_DATE = LocalDateTime.now();
    private static final BigDecimal AMOUNT_PAID_TO_ADD = new BigDecimal(50);
    private static final int AMOUNT_TO_PAY_DIVIDE = 2;

    private Bill validBill;

    @Before
    public void setupValidItems() {
        validBill = new BillBuilder().withValidValues().build();
    }

    @Test
    public void givenAConstructorWithoutId_whenMakingANewBill_thenTheIdIsCreated() {
        Bill bill = new Bill(validBill.getClientId(), validBill.getCreationDate(), validBill.getDueTerm(), validBill.getItems());

        Assert.assertTrue(bill.getId().getBillId() >= 0);
    }

    @Test
    public void givenValidQuoteWithValidQuoteItems_whenCalculateTotal_thenTotalShouldBeEqualToQuoteItemsTotal() {
        BillItem billItem1 = new BillItemBuilder().withValidValues().withPrice(BigDecimal.valueOf(10.89)).withQuantity(3).build();
        BillItem billItem2 = new BillItemBuilder().withValidValues().withPrice(BigDecimal.valueOf(3.67)).withQuantity(5).build();
        List<BillItem> billItems = new ArrayList<>();
        billItems.add(billItem1);
        billItems.add(billItem2);
        BigDecimal total = billItem1.getPrice().multiply(new BigDecimal(billItem1.getQuantity()));
        total = total.add(billItem2.getPrice().multiply(new BigDecimal(billItem2.getQuantity())));
        Bill unBill = new BillBuilder().withValidValues().withItems(billItems).build();

        Assert.assertTrue(unBill.calculateTotal().equals(total));
    }

    @Test
    public void givenValidQuote_whenAcceptedByClient_thenEffectiveDateShouldBeCurrentDate() {
        validBill.acceptQuote(ACCEPTED_DATE);

        Assert.assertTrue(validBill.isEffectiveDate(ACCEPTED_DATE));
    }

    @Test
    public void givenValidQuote_whenAcceptedByClient_thenExpectedPaymentShouldNotBeDue() {
        validBill.acceptQuote(ACCEPTED_DATE);

        Assert.assertFalse(validBill.isPaymentDue(ACCEPTED_DATE.plusDays(validBill.getDueTerm().inDays())));
    }

    @Test
    public void givenValidQuote_whenAcceptedByClientInThePast_thenExpectedPaymentShouldBeDueNow() {
        LocalDateTime acceptedDateInThePast = ACCEPTED_DATE.minusDays(validBill.getDueTerm().inDays()).minusDays(1);

        validBill.acceptQuote(acceptedDateInThePast);

        Assert.assertTrue(validBill.isPaymentDue(LocalDateTime.now()));
    }

    @Test(expected = BillAlreadyAcceptedException.class)
    public void givenValidQuoteAndQuoteIsAlreadyAccepted_whenAcceptedASecondTime_thenEffectiveDateDontChange() {
        LocalDateTime newAcceptedDateTenDaysLater = ACCEPTED_DATE.plusDays(10);
        validBill.acceptQuote(ACCEPTED_DATE);

        validBill.acceptQuote(newAcceptedDateTenDaysLater);
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
    public void givenValidQuote_whenPaymentDoneOnQuote_thenIsPaidShouldBeTrue() {
        BigDecimal amountToPay = validBill.calculateTotal();
        Bill bill = new BillBuilder().withValidValues().withAmountPaid(amountToPay).build();
        Assert.assertTrue(bill.isPaid());
    }

    @Test
    public void givenValidQuote_whenClientPaysBill_thenAmountPaidShouldNotBeZeroAndIsPaidShouldBeTrue() {
        validBill.pay(AMOUNT_PAID_TO_ADD);
        Assert.assertNotEquals(BigDecimal.ZERO, validBill.getAmountPaid());
        Assert.assertTrue(validBill.isPaid());
    }

    @Test
    public void givenValidQuote_whenClientPaysBillWithHalfAmountToPay_thenAmountPaidShouldNotBeZeroAndIsPaidShouldBeFalse() {
        BigDecimal amountToPay = validBill.calculateTotal();
        amountToPay = amountToPay.divide(new BigDecimal(AMOUNT_TO_PAY_DIVIDE));
        validBill.pay(amountToPay);
        Assert.assertNotEquals(BigDecimal.ZERO, validBill.getAmountPaid());
        Assert.assertFalse(validBill.isPaid());
    }
}
