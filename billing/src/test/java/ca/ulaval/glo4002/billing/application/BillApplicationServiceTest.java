package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.assembler.BillAssembler;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.billing.application.exceptions.BillAlreadyAcceptedException;
import ca.ulaval.glo4002.billing.application.exceptions.BillItemAsANegativeValueException;
import ca.ulaval.glo4002.billing.application.exceptions.BillNotFoundException;
import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillId;
import ca.ulaval.glo4002.billing.domain.bill.repositories.BillRepository;
import ca.ulaval.glo4002.billing.domain.bill.repositories.CrmRepository;
import ca.ulaval.glo4002.billing.infrastructure.persistence.InMemoryBillRepository;
import ca.ulaval.glo4002.billing.test.utils.builders.builders.BillBuilder;
import ca.ulaval.glo4002.billing.test.utils.builders.builders.dto.BillDtoBuilder;
import ca.ulaval.glo4002.billing.test.utils.builders.builders.dto.BillItemDtoBuilder;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;


public class BillApplicationServiceTest {

    private Bill validBill;
    private BillAssembler billAssembler;
    private BillDto validBillDTO;
    private BillDto invalidBillDTO;
    private BillApplicationService billApplicationService;
    private CrmRepository crmRepository;
    private BillId billId;
    private BillRepository repository;
    BillItemDto billItemDto;
    DueTerm validDueTerm;


    @Before
    public void setup() throws ClientNotFoundException {
        billId = mock(BillId.class);
        billAssembler = mock(BillAssembler.class);
        repository = mock(InMemoryBillRepository.class);
        crmRepository = mock(CrmRepository.class);

        billApplicationService = new BillApplicationService(billAssembler, repository, crmRepository);
        billItemDto = new BillItemDtoBuilder().withValidValues().withPrice(new BigDecimal(-10)).build();
        validDueTerm = DueTerm.DAYS30;
        invalidBillDTO = new BillDtoBuilder().withValidValues().build();
        validBillDTO = new BillDtoBuilder().withValidValues().build();
        validBill = new BillBuilder().withValidValues().build();

        willReturn(validDueTerm).given(crmRepository).getDefaultTermForClient(invalidBillDTO.clientId);
        willReturn(1L).given(billId).getBillId();
    }

    @Test
    public void givenBillNotExistAndValidParam_whenCreateBill_thenShouldCreateValidBill() throws ClientNotFoundException, ProductNotFoundException {
        willReturn(true).given(crmRepository).isClientExist(validBillDTO.clientId);
        billApplicationService.createBill(validBillDTO);
        verify(repository).insert(any());
    }

    @Test(expected = BillItemAsANegativeValueException.class)
    public void givenBillItemValueIsNegative_whenPriceIsNegative_thenBillItemAsANegativeValueException() throws BillItemAsANegativeValueException, ProductNotFoundException, ClientNotFoundException {
        willReturn(true).given(crmRepository).isClientExist(validBillDTO.clientId);
        invalidBillDTO.items.add(billItemDto);
        billApplicationService.createBill(invalidBillDTO);
    }

    @Test
    public void givenBill_whenNoDueTerm_thenGetClientDedaultDueTerm() throws ProductNotFoundException, ClientNotFoundException {
        willReturn(true).given(crmRepository).isClientExist(validBillDTO.clientId);
        invalidBillDTO.dueTerm = null;
        billApplicationService.createBill(invalidBillDTO);
        Assert.assertTrue(invalidBillDTO.dueTerm.equals(validDueTerm));
    }

    @Test
    public void givenBillExist_whenClientAcceptBill_thenAcceptTheBill() throws BillNotFoundException {
        when(repository.findBillById(validBill.getId())).thenReturn(Optional.ofNullable(validBill));

        billApplicationService.acceptQuote(validBill.getId());

        verify(repository).update(validBill);
    }

    @Test(expected = BillNotFoundException.class)
    public void givenBillNotExist_whenClientAcceptBill_thenErrorIsThrown() throws BillNotFoundException {
        BillId notExistBillId = new BillId();
        when(repository.findBillById(notExistBillId)).thenReturn(Optional.empty());

        billApplicationService.acceptQuote(notExistBillId);
    }

    @Test(expected = BillAlreadyAcceptedException.class)
    public void givenBillAlreadyAccepted_whenClientAcceptBill_thenErrorIsThrown() throws BillNotFoundException {
        when(repository.findBillById(validBill.getId())).thenReturn(Optional.ofNullable(validBill));
        billApplicationService.acceptQuote(validBill.getId());

        billApplicationService.acceptQuote(validBill.getId());
    }

    @Test(expected = ClientNotFoundException.class)
    public void whenClientNotFound_thenShouldThrowException() throws ClientNotFoundException, ProductNotFoundException {
        invalidBillDTO.clientId = new ClientId(0);
        willReturn(false).given(crmRepository).isClientExist(invalidBillDTO.clientId);

        billApplicationService.createBill((invalidBillDTO));
    }

    @Test
    public void givenBillExistAndIsAccepted_whenClientPaysBill_thenOldestBillIsPayd() throws ClientNotFoundException, BillNotFoundException, ProductNotFoundException {
        List<Bill> billList = new ArrayList<>();
        billList.add(validBill);

        when(repository.findBillsByClientIdOrderedByOldestExpectedPayment(validBill.getClientId())).thenReturn(billList);

        billApplicationService.payOldestBill(validBill.getClientId(), new BigDecimal(50));

        Assert.assertTrue(validBill.isPaid());
    }
}
