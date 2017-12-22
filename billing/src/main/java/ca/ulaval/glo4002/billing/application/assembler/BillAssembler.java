package ca.ulaval.glo4002.billing.application.assembler;

import ca.ulaval.glo4002.billing.application.dto.AcceptedBillToReturnDto;
import ca.ulaval.glo4002.billing.application.dto.BillDto;
import ca.ulaval.glo4002.billing.application.dto.EntrieDto;
import ca.ulaval.glo4002.billing.application.dto.LedgerDto;
import ca.ulaval.glo4002.billing.domain.*;

import java.util.ArrayList;
import java.util.List;

public class BillAssembler {

    private static final String BILLS_URI = "/bills/";

    public BillDto toDto(Bill bill) {
        BillDto billDto = new BillDto();

        billDto.id = bill.getId().getId();
        billDto.total = bill.calculateTotal();
        billDto.dueTerm = bill.getDueTerm();
        billDto.url = this.buildBillURI(bill.getId());

        return billDto;
    }

    public Bill createBillFromDto(BillDto billDto) {
        return new Bill(billDto.clientId, billDto.creationDate, billDto.dueTerm, (new BillItemAssembler()).createBillItemFromDto(billDto.items));
    }

    public AcceptedBillToReturnDto toAcceptedDto(Bill bill) {
        AcceptedBillToReturnDto acceptedBillToReturnDto = new AcceptedBillToReturnDto();

        acceptedBillToReturnDto.id = bill.getId().getId();
        acceptedBillToReturnDto.dueTerm = bill.getDueTerm();
        acceptedBillToReturnDto.url = this.buildBillURI(bill.getId());
        acceptedBillToReturnDto.expectedPayment = bill.getExpectedPayment().toInstant();
        acceptedBillToReturnDto.effectiveDate = bill.getEffectiveDate().toInstant();

        return acceptedBillToReturnDto;
    }

    private String buildBillURI(BillId billId) {
        return BILLS_URI + billId.getId();
    }

    public List<Entrie> createEntriesFromBills(List<Bill> bills) {
        List<Entrie> entries = new ArrayList<>();
        for (Bill bill : bills) {
            TransactionType transactionType = TransactionType.INVOICE;
            if (!bill.isActive()) {
                transactionType = TransactionType.INVOICE_CANCELLED;
            }

            Entrie entrie = new Entrie(
                    bill.getEffectiveDate().toInstant(),
                    transactionType,
                    bill.getClientId(),
                    OperationType.CREDIT,
                    bill.calculateTotal().floatValue());
            entries.add(entrie);
        }
        return entries;
    }

    public List<LedgerDto> toLedgerDto(List<Ledger> ledgers) {
        List<LedgerDto> ledgerDtos = new ArrayList<>();
        for (Ledger ledger : ledgers) {
            LedgerDto ledgerDto = new LedgerDto();
            ledgerDto.accountid = ledger.getAccountid();
            ledgerDto.entries = new ArrayList<>();
            for (Entrie entrie : ledger.getEntries()) {
                ledgerDto.entries.add(toEntrieDto(entrie));
            }
            ledgerDtos.add(ledgerDto);
        }
        return ledgerDtos;
    }

    public EntrieDto toEntrieDto(Entrie entrie) {
        EntrieDto entrieDto = new EntrieDto();
        entrieDto.date = entrie.getDate();
        entrieDto.typeTransaction = entrie.getTypeTransaction();
        entrieDto.clientId = entrie.getClientId().getClientId();
        entrieDto.typeOperation = entrie.getTypeOperation();
        entrieDto.amount = entrie.getAmount();
        entrieDto.balance = entrie.getBalance();
        return entrieDto;
    }
}
