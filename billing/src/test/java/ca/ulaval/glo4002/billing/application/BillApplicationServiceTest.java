package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.assembler.BillAssembler;
import ca.ulaval.glo4002.billing.application.assembler.PaymentAssembler;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.LedgerDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentDto;
import ca.ulaval.glo4002.billing.application.dto.PaymentToReturnDto;
import ca.ulaval.glo4002.billing.application.repositories.BillNotFoundException;
import ca.ulaval.glo4002.billing.builders.BillBuilder;
import ca.ulaval.glo4002.billing.builders.PaymentBuilder;
import ca.ulaval.glo4002.billing.builders.dto.BillDtoBuilder;
import ca.ulaval.glo4002.billing.builders.dto.PaymentDtoBuilder;
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
import java.time.ZonedDateTime;
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
    private PaymentDto validPaymentDto;
    private Payment validPayment;
    private PaymentToReturnDto paymentToReturnDto;
    private List<Bill> validBills;
    private List<Payment> validPayments;

    @Before
    public void setup() throws ClientNotFoundException {
        billId = mock(BillId.class);
        billAssembler = mock(BillAssembler.class);
        billRepository = mock(InMemoryBillRepository.class);
        clientRepository = mock(ClientRepository.class);
        productRepository = mock(ProductRepository.class);
        paymentAssembler = mock(PaymentAssembler.class);
        client = mock(Client.class);

        validDueTerm = DueTerm.DAYS30;
        validPayment = new PaymentBuilder().withValidValues().build();
        validPaymentDto = new PaymentDtoBuilder().withValidValues().build();
        invalidBillDTO = new BillDtoBuilder().withValidValues().build();
        validBillDTO = new BillDtoBuilder().withValidValues().build();
        validBill = new BillBuilder().withValidValues().build();
        paymentToReturnDto = new PaymentToReturnDto();
        paymentToReturnDto.id = validPayment.getPaymentId().getUniqueId();
        paymentToReturnDto.url = paymentAssembler.buildPaymentURI(validPayment.getPaymentId());
        validBills = new ArrayList<>();
        validBills.add(validBill);
        validPayments = new ArrayList<>();
        validPayments.add(validPayment);

        billApplicationService = new BillApplicationService(billAssembler, billRepository, clientRepository, productRepository, paymentAssembler);

        willReturn(client).given(clientRepository).getClient(validBillDTO.clientId);
        willReturn(validDueTerm).given(clientRepository).getDefaultDueTerm(validBillDTO.clientId);
        willReturn(1L).given(billId).getId();
        willReturn(validPayment).given(paymentAssembler).createPaymentFromDto(validPaymentDto);
        willReturn(paymentToReturnDto).given(paymentAssembler).toDto(validPayment);
        willReturn(validBills).given(billRepository).findBillsByExpectedPayment(any(), any());
        willReturn(validPayments).given(billRepository).findPaymentsByDate(any(), any());
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

    @Test
    public void givenPaymentWithValidParam_whenPaymentResponseRest_thenPaymentReturnToDto() throws ClientNotFoundException {
        PaymentToReturnDto paymentToReturnDto = billApplicationService.createPayment(validPaymentDto);

        assertEquals(this.paymentToReturnDto.url, paymentToReturnDto.url);
        assertEquals(this.paymentToReturnDto.id, paymentToReturnDto.id);
    }

    @Test
    public void givenFilterMonthsAndYear_whenfilterLedger_thenReturnLedgersDto() {
        long month = 1;
        long year = 1;

        List<LedgerDto> ledgerDtos = billApplicationService.filterLedger(month, month, year);

        verify(billAssembler).toLedgerDto(any());
    }

    @Test
    public void givenMonthAndYear_whenbuildStartingDate_thenReturnFirstDayOfMonth() {
        long month = 1;
        long year = 1;
        ZonedDateTime zonedDateTime = billApplicationService.buildStartingDate(month, year);

        assertTrue(zonedDateTime.getMonth().getValue() == month);
        assertTrue(zonedDateTime.getYear() == year);
        assertTrue(zonedDateTime.getDayOfMonth() == 1);
    }

    @Test
    public void givenYear_whenbuildStartingDate_thenReturnFirstDayOfMonthandFirstMonth() {
        long year = 1;
        ZonedDateTime zonedDateTime = billApplicationService.buildStartingDate(null, year);

        assertTrue(zonedDateTime.getMonth().getValue() == 1);
        assertTrue(zonedDateTime.getYear() == year);
        assertTrue(zonedDateTime.getDayOfMonth() == 1);
    }

    @Test
    public void givenMonthAndYear_whenbuildendingDate_thenReturnLastDayOfMonth() {
        long month = 1;
        long year = 1;
        ZonedDateTime zonedDateTime = billApplicationService.buildEndingDate(month, year);

        assertTrue(zonedDateTime.getMonth().getValue() == month);
        assertTrue(zonedDateTime.getYear() == year);
        assertTrue(zonedDateTime.getDayOfMonth() == 31);
    }

    @Test
    public void givenYear_whenbuildendingDate_thenReturnFirstDayOfMonthandLastMonth() {
        long year = 1;
        ZonedDateTime zonedDateTime = billApplicationService.buildEndingDate(null, year);

        assertTrue(zonedDateTime.getMonth().getValue() == 12);
        assertTrue(zonedDateTime.getYear() == year);
        assertTrue(zonedDateTime.getDayOfMonth() == 31);
    }

    @Test
    public void givenABillId_whendeleteQuote_thenTheQuoteIsDead() throws BillNotFoundException {
        when(billRepository.findBillById(validBill.getId())).thenReturn(Optional.ofNullable(validBill));

        billApplicationService.deleteQuote(validBill.getId());

        verify(billRepository).update(validBill);
    }
}

