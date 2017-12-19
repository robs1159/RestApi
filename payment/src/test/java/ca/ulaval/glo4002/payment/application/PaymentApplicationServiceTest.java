package ca.ulaval.glo4002.payment.application;

import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.payment.application.dto.PaymentDto;
import ca.ulaval.glo4002.payment.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.payment.domain.payment.Payment;
import ca.ulaval.glo4002.payment.domain.payment.repositories.CrmRepository;
import ca.ulaval.glo4002.payment.domain.payment.repositories.PaymentRepository;
import ca.ulaval.glo4002.payment.test.utilis.builders.builders.PaymentBuilder;
import ca.ulaval.glo4002.payment.test.utilis.builders.builders.dto.PaymentDtoBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class PaymentApplicationServiceTest {

    private static final int DELTA_FLOAT_TEST = 0;
    private static final String PAYMENTS_URI = "/payments/";

    private PaymentDto validPaymentDto;
    private Payment validPayment;
    private PaymentApplicationService paymentService;
    private PaymentRepository paymentRepository;
    private CrmRepository crmRepository;

    @Before
    public void setup() {
        paymentRepository = mock(PaymentRepository.class);
        crmRepository = mock(CrmRepository.class);

        validPaymentDto = new PaymentDtoBuilder().withValidValues().build();
        validPayment = new PaymentBuilder().withValidValues().build();
        paymentService = new PaymentApplicationService(paymentRepository, crmRepository);
    }

    @Test
    public void givenPaymentNotExistAndValidParam_whenCreatePayment_thenShouldCreateValidPayment() throws ClientNotFoundException {
        Payment payment = paymentService.createPayment(validPaymentDto);

        Assert.assertEquals(validPaymentDto.clientId, payment.getClientId());
        Assert.assertEquals(validPaymentDto.amount, payment.getAmount().floatValue(), DELTA_FLOAT_TEST);
        Assert.assertEquals(validPaymentDto.paymentMethod.account, payment.getPaymentMethod().getAccount());
        Assert.assertEquals(validPaymentDto.paymentMethod.source, payment.getPaymentMethod().getSource());
    }

    @Test
    public void givenPaymentWithValidParam_whenPaymentResponseRest_thenPaymentReturnToDto() {
        PaymentToReturnDto paymentToReturnDto = paymentService.toReturnDto(validPayment);
        String paymentUriWithId = PAYMENTS_URI + validPayment.getPaymentId().getUniqueId();

        Assert.assertEquals(validPayment.getPaymentId().getUniqueId(), paymentToReturnDto.id);
        Assert.assertEquals(paymentUriWithId, paymentToReturnDto.url);
    }
}
