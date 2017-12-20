package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.assembler.PaymentAssembler;
import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.builders.PaymentBuilder;
import ca.ulaval.glo4002.billing.builders.dto.PaymentDtoBuilder;
import ca.ulaval.glo4002.billing.domain.Client;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import ca.ulaval.glo4002.billing.domain.Payment;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.billing.domain.repositories.PaymentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class PaymentApplicationServiceTest {

    private static final int DELTA_FLOAT_TEST = 0;
    private static final String PAYMENTS_URI = "/payments/";

    private PaymentDto validPaymentDto;
    private PaymentToReturnDto validPaymentToReturnDto;
    private Payment validPayment;
    private PaymentApplicationService paymentService;
    private PaymentRepository paymentRepository;
    private ClientRepository clientRepository;
    private PaymentAssembler paymentAssembler;
    private Client client;

    @Before
    public void setup() {
        paymentRepository = mock(PaymentRepository.class);
        clientRepository = mock(ClientRepository.class);
        paymentAssembler = mock(PaymentAssembler.class);
        client = new Client(new ClientId(), DueTerm.DAYS30);

        paymentService = new PaymentApplicationService(paymentRepository, clientRepository, paymentAssembler);
        validPaymentDto = new PaymentDtoBuilder().withValidValues().build();
        validPayment = new PaymentBuilder().withValidValues().build();
        validPaymentToReturnDto = new PaymentToReturnDto();
        validPaymentToReturnDto.id = validPayment.getPaymentId().getUniqueId();
        validPaymentToReturnDto.url = PAYMENTS_URI + validPayment.getPaymentId().getUniqueId();
        willReturn(validPayment).given(paymentAssembler).createPaymentFromDto(validPaymentDto);
    }

    @Test
    public void givenPaymentNotExistAndValidParam_whenCreatePayment_thenShouldCreateValidPayment() throws ClientNotFoundException {
        Payment payment = paymentService.createPayment(validPaymentDto);

        Assert.assertEquals(validPaymentDto.clientId.getClientId(), payment.getClientId().getClientId());
        Assert.assertEquals(validPaymentDto.amount, payment.getAmount().floatValue(), DELTA_FLOAT_TEST);
        Assert.assertEquals(validPaymentDto.paymentMethod.account, payment.getPaymentMethod().getAccount());
        Assert.assertEquals(validPaymentDto.paymentMethod.source, payment.getPaymentMethod().getSource());
    }

    @Test
    public void givenPaymentWithValidParam_whenPaymentResponseRest_thenPaymentReturnToDto() throws ClientNotFoundException {
        willReturn(validPaymentToReturnDto).given(paymentAssembler).toDto(validPayment);

        PaymentToReturnDto paymentToReturnDto = paymentService.toReturnDto(validPayment);

        Assert.assertEquals(validPaymentToReturnDto.id, paymentToReturnDto.id);
        Assert.assertEquals(validPaymentToReturnDto.url, paymentToReturnDto.url);
    }
}
