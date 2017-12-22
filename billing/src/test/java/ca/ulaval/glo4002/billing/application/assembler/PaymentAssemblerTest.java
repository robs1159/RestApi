package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.builders.dto.PaymentDtoBuilder;
import ca.ulaval.glo4002.billing.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PaymentAssemblerTest {

    private static final int DELTA_FLOAT_TEST = 0;
    private PaymentDto validPaymentDto;
    private Payment validPayment;
    private PaymentAssembler paymentAssembler;

    @Before
    public void setup() {
        validPaymentDto = new PaymentDtoBuilder().withValidValues().build();
        validPayment = new Payment(new ClientId(), 10, new PaymentMethod());
        paymentAssembler = new PaymentAssembler();
    }

    @Test
    public void givenPaymentDto_whenCreateNewPaymentFromDto_thenAssignValuesFromDtoToPayment() {
        Payment payment = paymentAssembler.createPaymentFromDto(validPaymentDto);

        assertEquals(validPaymentDto.clientId, payment.getClientId());
        assertEquals(validPaymentDto.amount, payment.getAmount().floatValue(), DELTA_FLOAT_TEST);
        assertEquals(validPaymentDto.paymentMethod.source, payment.getPaymentMethod().getSource());
        assertEquals(validPaymentDto.paymentMethod.account, payment.getPaymentMethod().getAccount());
    }

    @Test
    public void givenPayment_whentoDto_thenreturnPaymentToReturnDto() {
        PaymentToReturnDto paymentToReturnDto = paymentAssembler.toDto(validPayment);

        assertEquals(paymentToReturnDto.id, validPayment.getPaymentId().getUniqueId());
        assertEquals(paymentToReturnDto.url, paymentAssembler.buildPaymentURI(validPayment.getPaymentId()));
    }

    @Test
    public void giverAPaymentList_WhencreateEntriesFromPayments_ThenReturnEntrieList() {
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(validPayment);
        List<Entrie> entries = paymentAssembler.createEntriesFromPayments(paymentList);

        assertTrue(entries.get(0).getTypeOperation() == OperationType.DEBIT);
        assertTrue(entries.get(0).getClientId() == validPayment.getClientId());
        assertTrue(entries.get(0).getDate().compareTo(validPayment.getDate().toInstant()) == 0);
        assertTrue(entries.get(0).getAmount() == validPayment.getAmount().floatValue());
        assertTrue(entries.get(0).getTypeTransaction() == TransactionType.PAYMENT);
        assertTrue(entries.get(0).getTypeOperation() == OperationType.DEBIT);
    }
}
