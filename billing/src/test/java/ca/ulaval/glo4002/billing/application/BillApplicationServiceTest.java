package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.assembler.BillAssembler;
import ca.ulaval.glo4002.billing.application.assembler.PaymentAssembler;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.repositories.BillNotFoundException;
import ca.ulaval.glo4002.billing.builders.BillBuilder;
import ca.ulaval.glo4002.billing.builders.dto.BillDtoBuilder;
import ca.ulaval.glo4002.billing.domain.*;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.domain.exceptions.ProductNotFoundException;
import ca.ulaval.glo4002.billing.domain.repositories.BillRepository;
import ca.ulaval.glo4002.billing.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.billing.domain.repositories.ProductRepository;
import ca.ulaval.glo4002.billing.infrastructure.persistence.InMemoryBillRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;


public class BillApplicationServiceTest {

    private Bill validBill;
    private BillAssembler billAssembler;
    private BillDto validBillDTO;
    private BillDto invalidBillDTO;
    private BillApplicationService billApplicationService;
    private ClientRepository clientRepository;
    private PaymentAssembler paymentAssembler;
    private ProductRepository productRepository;
    private BillId billId;
    private BillRepository billRepository;
    private Client client;
    private DueTerm validDueTerm;


    @Before
    public void setup() throws ClientNotFoundException {
        billId = mock(BillId.class);
        billAssembler = mock(BillAssembler.class);
        billRepository = mock(InMemoryBillRepository.class);
        clientRepository = mock(ClientRepository.class);
        productRepository = mock(ProductRepository.class);
        paymentAssembler = mock(PaymentAssembler.class);
        client = mock(Client.class);

        billApplicationService = new BillApplicationService(billAssembler, billRepository, clientRepository, productRepository, paymentAssembler);
        validDueTerm = DueTerm.DAYS30;
        invalidBillDTO = new BillDtoBuilder().withValidValues().build();
        validBillDTO = new BillDtoBuilder().withValidValues().build();
        validBill = new BillBuilder().withValidValues().build();

        willReturn(client).given(clientRepository).getClient(validBillDTO.clientId);
        willReturn(validDueTerm).given(clientRepository).getDefaultDueTerm(validBillDTO.clientId);
        willReturn(1L).given(billId).getId();
    }

    @Test
    public void givenBillNotExistAndValidParam_whenCreateBill_thenShouldInsertBillIntoRepository() throws ClientNotFoundException, ProductNotFoundException {

        billApplicationService.createBill(validBillDTO);

        verify(billRepository).insert(any());
    }

    @Test
    public void givenBillWithoutDueTerm_whenCreatingBill_thenGetClientDedaultDueTerm() throws ProductNotFoundException, ClientNotFoundException {
        invalidBillDTO.dueTerm = null;
        billApplicationService.createBill(invalidBillDTO);

        assertEquals(invalidBillDTO.dueTerm, validDueTerm);
    }

    @Test
    public void givenBillExist_whenClientAcceptBill_thenAcceptTheBill() throws BillNotFoundException {
        when(billRepository.findBillById(validBill.getId())).thenReturn(Optional.ofNullable(validBill));

        billApplicationService.acceptQuote(validBill.getId());

        verify(billRepository).update(validBill);
    }

    //Todo:Ajouter un test pour vérifier que vous accepter le bill

    @Test(expected = BillNotFoundException.class)
    public void givenBillNotExist_whenClientAcceptBill_thenErrorIsThrown() throws BillNotFoundException {
        BillId notExistBillId = new BillId();
        when(billRepository.findBillById(notExistBillId)).thenReturn(Optional.empty());

        billApplicationService.acceptQuote(notExistBillId);
    }

    @Test(expected = ClientNotFoundException.class)
    public void givenNotexistingClientId_whenCreateBill_thenShouldThrowException() throws ClientNotFoundException, ProductNotFoundException {
        invalidBillDTO.clientId = new ClientId(0);
        willReturn(null).given(clientRepository).getClient(invalidBillDTO.clientId);

        billApplicationService.createBill((invalidBillDTO));
    }

    @Test
    public void givenBillExistAndIsAccepted_whenClientPaysBill_thenOldestBillIsPayed() throws ClientNotFoundException, BillNotFoundException, ProductNotFoundException {
        List<Bill> billList = new ArrayList<>();
        billList.add(validBill);

        when(billRepository.findBillsByClientIdOrderedByOldestExpectedPayment(validBill.getClientId())).thenReturn(billList);

        billApplicationService.payOldestBill(validBill.getClientId(), new BigDecimal(50));

        assertTrue(validBill.isPaid());
    }

    /*@Before
    public void setup() {
        paymentRepository = mock(PaymentRepository.class);
        clientRepository = mock(ClientRepository.class);
        paymentAssembler = mock(PaymentAssembler.class);
        client = new Client(new ClientId(), DueTerm.DAYS30);

        validPaymentDto = new PaymentDtoBuilder().withValidValues().build();
        validPayment = new PaymentBuilder().withValidValues().build();
        validPaymentToReturnDto = new PaymentToReturnDto();
        validPaymentToReturnDto.id = validPayment.getPaymentId().getUniqueId();
        validPaymentToReturnDto.url = PAYMENTS_URI + validPayment.getPaymentId().getUniqueId();
        willReturn(validPayment).given(paymentAssembler).createPaymentFromDto(validPaymentDto);
    }

    @Test
    public void givenPaymentNotExistAndValidParam_whenCreatePayment_thenShouldCreateValidPayment() throws ClientNotFoundException {
        Payment payment = billingService.createPayment(validPaymentDto);

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
    }*/

}
