package ca.ulaval.glo4002.billing.application;

import ca.ulaval.glo4002.billing.application.assembler.BillAssembler;
import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.billing.application.repositories.BillItemAsANegativeValueException;
import ca.ulaval.glo4002.billing.application.repositories.BillNotFoundException;
import ca.ulaval.glo4002.billing.domain.Bill;
import ca.ulaval.glo4002.billing.domain.BillId;
import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.exceptions.BillAlreadyAcceptedException;
import ca.ulaval.glo4002.billing.domain.exceptions.ClientNotFoundException;
import ca.ulaval.glo4002.billing.domain.exceptions.ProductNotFoundException;
import ca.ulaval.glo4002.billing.domain.repositories.BillRepository;
import ca.ulaval.glo4002.billing.domain.repositories.ClientRepository;
import ca.ulaval.glo4002.billing.domain.repositories.ProductRepository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class BillApplicationService {

    private BillAssembler billAssembler;
    private BillRepository billRepository;
    private ClientRepository clientRepository;
    private ProductRepository productRepository;

    @Inject
    public BillApplicationService(BillAssembler billAssembler, BillRepository billRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.billAssembler = billAssembler;
        this.billRepository = billRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public BillDto createBill(BillDto billDto) throws ClientNotFoundException, ProductNotFoundException, BillItemAsANegativeValueException {
        BillDto billToReturnDto = null;

        if (clientRepository.getClient(billDto.clientId) != null) {
            assignDueTerm(billDto);
            validateProduct(billDto.items);

            Bill bill = billAssembler.createBillFromDto(billDto);
            billRepository.insert(bill);
            billToReturnDto = billAssembler.toDto(bill);
        } else {
            throw new ClientNotFoundException(billDto.clientId);
        }
        return billToReturnDto;
    }

    private void validateProduct(List<BillItemDto> billItemsDto) throws ProductNotFoundException, BillItemAsANegativeValueException {
        for (BillItemDto billItemDto : billItemsDto) {
            productRepository.exist(billItemDto.productId);
        }
    }

    public AcceptedBillToReturnDto acceptQuote(BillId billId) throws BillNotFoundException, BillAlreadyAcceptedException {
        Optional<Bill> bill = billRepository.findBillById(billId);

        if (bill.isPresent()) {
            bill.get().acceptQuote(LocalDateTime.now());
            billRepository.update(bill.get());
        } else {
            throw new BillNotFoundException(billId);
        }

        return billAssembler.toAcceptedDto(bill.get());
    }

    public void deleteQuote(BillId billId) throws BillNotFoundException, BillAlreadyAcceptedException {
        Optional<Bill> bill = billRepository.findBillById(billId);

        if (bill.isPresent()) {
            bill.get().deleteQuote();

            payOldestBill(bill.get().getClientId(), bill.get().getAmountPaid());

            billRepository.update(bill.get());
        } else {
            throw new BillNotFoundException(billId);
        }
    }

    private void assignDueTerm(BillDto billDto) throws ClientNotFoundException {
        if (billDto.dueTerm == null) {
            billDto.dueTerm = clientRepository.getDefaultDueTerm(billDto.clientId);
        }
    }

    public void payOldestBill(ClientId clientId, BigDecimal amount) {
        List<Bill> clientBills = billRepository.findBillsByClientIdOrderedByOldestExpectedPayment(clientId);
        Bill billToPay = null;

        for (Bill bill : clientBills) {
            if (!bill.isPaid() && bill.isActive()) {
                billToPay = bill;
                break;
            }
        }

        if (billToPay != null) {
            billToPay.pay(amount);
        } else {
            clientBills.get(clientBills.size()).pay(amount);
        }
    }
}