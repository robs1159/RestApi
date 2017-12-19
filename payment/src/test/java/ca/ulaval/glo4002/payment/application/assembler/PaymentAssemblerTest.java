package ca.ulaval.glo4002.payment.application.assembler;

import ca.ulaval.glo4002.payment.application.dto.PaymentDto;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.test.utilis.builders.builders.dto.PaymentDtoBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaymentAssemblerTest {

    private static final int DELTA_FLOAT_TEST = 0;
    private PaymentDto validPaymentDto;

    @Before
    public void setup() {
        validPaymentDto = new PaymentDtoBuilder().withValidValues().build();
    }

    @Test
    public void givenPaymentDto_whenCreateNewPaymentFromDto_thenAssignValuesFromDtoToPayment() {
        Payment payment = PaymentAssembler.createPaymentFromDto(validPaymentDto);

        Assert.assertEquals(validPaymentDto.clientId, payment.getClientId());
        Assert.assertEquals(validPaymentDto.amount, payment.getAmount().floatValue(), DELTA_FLOAT_TEST);
        Assert.assertEquals(validPaymentDto.paymentMethod.source, payment.getPaymentMethod().getSource());
        Assert.assertEquals(validPaymentDto.paymentMethod.account, payment.getPaymentMethod().getAccount());
    }
}
