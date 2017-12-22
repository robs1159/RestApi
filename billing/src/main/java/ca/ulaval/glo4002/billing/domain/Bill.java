package ca.ulaval.glo4002.billing.domain;

import ca.ulaval.glo4002.billing.domain.exceptions.BillAlreadyAcceptedException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bill {


    //TODO: Pourquoi 2 id
    //TODO: id serait ok comme nom
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @OneToOne(cascade = CascadeType.ALL)
    @Embedded
    private BillId billId;

    @OneToOne(cascade = CascadeType.ALL)
    @Embedded
    private ClientId clientId;

    @Column
    private String creationDate;

    @Column
    private DueTerm dueTerm;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "billItem")
    private List<BillItem> items = new ArrayList<>();

    @Column
    private ZonedDateTime effectiveDate;

    @Column
    private ZonedDateTime expectedPayment;

    @Column
    private BigDecimal amountPaid;

    @Column
    private Boolean activeQuote;

    public Bill() {
    }

    public Bill(ClientId clientId, String creationDate, DueTerm dueTerm, List<BillItem> billItems) {
        this.clientId = clientId;
        this.creationDate = creationDate;
        this.dueTerm = dueTerm;
        this.billId = this.createBillId();
        this.amountPaid = BigDecimal.ZERO;
        activeQuote = true;

        for (BillItem item : billItems) {
            this.addBillItem(item);
        }
    }

    public Bill(BillId billId, ClientId clientId, String creationDate, DueTerm dueTerm, BigDecimal amountPaid, List<BillItem> billItems) {
        this.clientId = clientId;
        this.creationDate = creationDate;
        this.dueTerm = dueTerm;
        this.billId = billId;
        this.amountPaid = amountPaid;
        activeQuote = true;

        for (BillItem item : billItems) {
            this.addBillItem(item);
        }
    }

    private BillId createBillId() {
        return new BillId();
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (BillItem item : items) {
            total = total.add(item.calculatePrice());
        }

        return total;
    }

    public BigDecimal calculateBalance() {
        return calculateBalance().subtract(getAmountPaid());
    }

    public void acceptQuote(ZonedDateTime acceptedQuoteDate) throws BillAlreadyAcceptedException {
        if (effectiveDate == null) {
            effectiveDate = acceptedQuoteDate;
            expectedPayment = effectiveDate.plusDays(dueTerm.inDays());
        } else {
            throw new BillAlreadyAcceptedException(this.billId);
        }
    }

    private void addBillItem(BillItem item) {
        items.add(item);
    }

    public BillId getId() {
        return this.billId;
    }

    public ClientId getClientId() {
        return this.clientId;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public DueTerm getDueTerm() {
        return this.dueTerm;
    }

    public BigDecimal getAmountPaid() {
        return this.amountPaid;
    }

    public void pay(BigDecimal payAmount) {
        BigDecimal amountLeftToPay = calculateTotal().subtract(this.amountPaid);

        if (amountLeftToPay.compareTo(payAmount) >= 0) {
            this.amountPaid = this.amountPaid.add(payAmount);
        } else {
            this.amountPaid = this.amountPaid.add(amountLeftToPay);
        }
    }

    public List<BillItem> getItems() {
        return this.items;
    }

    public boolean isPaid() {
        return this.amountPaid.compareTo(calculateTotal()) >= 0;
    }

    public ZonedDateTime getEffectiveDate() {
        return this.effectiveDate;
    }

    public ZonedDateTime getExpectedPayment() {
        return this.expectedPayment;
    }

    public void deleteQuote() {
        this.activeQuote = false;
    }

    public boolean isActive() {
        return activeQuote;
    }
}
