package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.PaymentMethodDto;
import ca.ulaval.glo4002.billing.builders.dto.PaymentMethodDtoBuilder;
import ca.ulaval.glo4002.billing.domain.PaymentMethod;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaymentMethodAssemblerTest {

    private PaymentMethodDto validPaymentMethodDto;

    @Before
    public void setup() {
        validPaymentMethodDto = new PaymentMethodDtoBuilder().withValidValues().build();
    }

    @Test
    public void givenPaymentMethodDto_whenCreateNewPaymentMethodFromDto_thenAssignValuesFromDtoToPaymentMethod() {
        PaymentMethod paymentMethod = (new PaymentMethodAssembler()).createPaymentMethodFromDto(validPaymentMethodDto);

        Assert.assertEquals(validPaymentMethodDto.account, paymentMethod.getAccount());
        Assert.assertEquals(validPaymentMethodDto.source, paymentMethod.getSource());
    }
}
