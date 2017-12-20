package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.builders.dto.PaymentDtoBuilder;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.PaymentMethod;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

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
}
