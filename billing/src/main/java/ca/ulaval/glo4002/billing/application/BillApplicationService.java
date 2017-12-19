package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.assembler.BillAssembler;
import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.billing.application.dto.BillToReturnDto;
import ca.ulaval.glo4002.billing.application.exceptions.BillAlreadyAcceptedException;
import ca.ulaval.glo4002.billing.application.exceptions.BillItemAsANegativeValueException;
import ca.ulaval.glo4002.billing.application.exceptions.BillNotFoundException;
import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillId;
import ca.ulaval.glo4002.billing.domain.bill.repositories.BillRepository;
import ca.ulaval.glo4002.billing.domain.bill.repositories.CrmRepository;
import ca.ulaval.glo4002.crmInterface.application.repositories.ClientNotFoundException;
import ca.ulaval.glo4002.crmInterface.application.repositories.ProductNotFoundException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class BillApplicationService implements BillPaymentOperation {

    private BillAssembler billAssembler;
    private BillRepository billRepository;
    private CrmRepository crmRepository;


    @Inject
    public BillApplicationService(BillAssembler billAssembler, BillRepository billRepository, CrmRepository crmRepository) {
        this.billAssembler = billAssembler;
        this.billRepository = billRepository;
        this.crmRepository = crmRepository;
    }

    public BillToReturnDto createBill(BillDto billDto) throws ClientNotFoundException, ProductNotFoundException, BillItemAsANegativeValueException {

        BillToReturnDto billToReturnDto = null;

        if (crmRepository.isClientExist(billDto.clientId)) {
            assignDueTerm(billDto);
            validateProduct(billDto.items);

            Bill bill = BillAssembler.createBillFromDto(billDto);
            billRepository.insert(bill);
            billToReturnDto = billAssembler.toDto(bill);
        } else {
            throw new ClientNotFoundException(billDto.clientId);
        }
        return billToReturnDto;
    }

    public AcceptedBillToReturnDto acceptQuote(BillId billId) throws BillNotFoundException, BillAlreadyAcceptedException {
        Optional<Bill> bill = billRepository.findBillById(billId);

        if (bill.isPresent()) {
            bill.get().acceptQuote(LocalDateTime.now());
            billRepository.update(bill.get());
        } else {
            throw new BillNotFoundException(billId);
        }

        return billAssembler.toAcceptedDtoReturn(bill.get());
    }

    private void validateProduct(List<BillItemDto> billItemsDto) throws ProductNotFoundException, BillItemAsANegativeValueException {
        for (BillItemDto tmpBillItemDto : billItemsDto) {

            crmRepository.findProductById(tmpBillItemDto.productId);

            if (tmpBillItemDto.price.compareTo(BigDecimal.ZERO) < 0 || tmpBillItemDto.quantity < 0) {
                throw new BillItemAsANegativeValueException(tmpBillItemDto.productId);
            }
        }
    }

    private void assignDueTerm(BillDto billDto) throws ClientNotFoundException {
        if (billDto.dueTerm == null) {
            billDto.dueTerm = crmRepository.getDefaultTermForClient(billDto.clientId);
        }
    }

    @Override
    public void payOldestBill(ClientId clientId, BigDecimal amount) {
        List<Bill> clientBills = billRepository.findBillsByClientIdOrderedByOldestExpectedPayment(clientId);
        Bill billToPay = null;

        for (Iterator<Bill> billIterator = clientBills.iterator(); billIterator.hasNext(); ) {
            Bill bill = billIterator.next();

            if (!bill.isPaid()) {
                billToPay = bill;
                break;
            }
        }

        if (billToPay != null) {
            billToPay.pay(amount);
        }
    }
}