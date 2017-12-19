package ca.ulaval.glo4002.billing.test.utils.builders.builders;

import ca.ulaval.glo4002.billing.domain.bill.Bill;
import ca.ulaval.glo4002.billing.domain.bill.BillId;
import ca.ulaval.glo4002.billing.domain.bill.BillItem;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BillBuilder {

    private final static ClientId VALID_CLIENT_ID = new ClientId(1);
    private final static String VALID_CREATION_DATE = "2017-01-01";
    private final static DueTerm VALID_DUE_TERM = DueTerm.DAYS30;
    private final static BillId VALID_BILLID = new BillId();

    private ClientId clientId;
    private String creationDate;
    private DueTerm dueTerm;
    private BigDecimal amountPaid;
    private List<BillItem> items;
    private BillId billId;

    public BillBuilder withClientId(ClientId clientId) {
        this.clientId = clientId;
        return this;
    }

    public BillBuilder withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public BillBuilder withDueTerm(DueTerm dueTerm) {
        this.dueTerm = dueTerm;
        return this;
    }

    public BillBuilder withAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public BillBuilder withBillId(BillId billId) {
        this.billId = billId;
        return this;
    }

    public BillBuilder withItems(List<BillItem> items) {
        this.items = items;
        return this;
    }

    public BillBuilder withValidValues() {
        this.clientId = VALID_CLIENT_ID;
        this.creationDate = VALID_CREATION_DATE;
        this.dueTerm = VALID_DUE_TERM;
        this.billId = VALID_BILLID;
        this.amountPaid = BigDecimal.ZERO;

        List<BillItem> items = new ArrayList<BillItem>();
        BillItem validBillItem = new BillItemBuilder().withValidValues().build();

        items.add(validBillItem);

        this.items = items;

        return this;
    }

    public Bill build() {
        return new Bill(billId, clientId, creationDate, dueTerm, amountPaid, items);
    }
}