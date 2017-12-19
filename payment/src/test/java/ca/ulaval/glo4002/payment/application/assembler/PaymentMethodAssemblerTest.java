package ca.ulaval.glo4002.payment.application.assembler;

import ca.ulaval.glo4002.payment.application.dto.PaymentMethodDto;
import ca.ulaval.glo4002.payment.domain.payment.PaymentMethod;
import ca.ulaval.glo4002.payment.test.utilis.builders.builders.dto.PaymentMethodDtoBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaymentMethodAssemblerTest {

    private PaymentMethodDto validPaymentMethodDto;

    @Before
    public void setup(){
        validPaymentMethodDto = new PaymentMethodDtoBuilder().withValidValues().build();
    }

    @Test
    public void givenPaymentMethodDto_whenCreateNewPaymentMethodFromDto_thenAssignValuesFromDtoToPaymentMethod(){
        PaymentMethod paymentMethod = PaymentMethodAssembler.createPaymentMethodFromDto(validPaymentMethodDto);

        Assert.assertEquals(validPaymentMethodDto.account, paymentMethod.getAccount());
        Assert.assertEquals(validPaymentMethodDto.source, paymentMethod.getSource());
    }
}
