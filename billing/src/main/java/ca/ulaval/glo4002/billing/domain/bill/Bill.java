package ca.ulaval.glo4002.billing.domain.bill;

import ca.ulaval.glo4002.billing.application.exceptions.BillAlreadyAcceptedException;
import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import ca.ulaval.glo4002.crmInterface.domain.DueTerm;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @OneToOne(cascade = CascadeType.ALL)
    private BillId billId;

    @OneToOne(cascade = CascadeType.ALL)
    private ClientId clientId;

    @Column
    private String creationDate;

    @Column
    private DueTerm dueTerm;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "billItem")
    private List<BillItem> items = new ArrayList<>();

    @Column
    private LocalDateTime effectiveDate;

    @Column
    private LocalDateTime expectedPayment;

    @Column
    private BigDecimal amountPaid;

    public Bill() {
    }

    public Bill(ClientId clientId, String creationDate, DueTerm dueTerm, List<BillItem> billItems) {
        this.clientId = clientId;
        this.creationDate = creationDate;
        this.dueTerm = dueTerm;
        this.billId = this.createBillId();
        this.amountPaid = BigDecimal.ZERO;

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
            total = total.add(item.calculatePriceItem());
        }

        return total;
    }

    public void acceptQuote(LocalDateTime acceptedQuoteDate) throws BillAlreadyAcceptedException {
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

    public void pay(BigDecimal amountPaidToAdd) {
        BigDecimal amountToPay = calculateTotal();
        amountToPay = amountToPay.subtract(this.amountPaid);

        if (amountToPay.compareTo(amountPaidToAdd) >= 0) {
            this.amountPaid = this.amountPaid.add(amountPaidToAdd);
        } else {
            this.amountPaid = this.amountPaid.add(amountToPay);
        }
    }

    public List<BillItem> getItems() {
        return this.items;
    }

    public boolean isEffectiveDate(LocalDateTime effectiveDateToValidate) {
        return this.effectiveDate.isEqual(effectiveDateToValidate);
    }

    public boolean isPaymentDue(LocalDateTime expectedPaymentToValidate) {
        return this.expectedPayment.isBefore(expectedPaymentToValidate);
    }

    public boolean isPaid() {
        return this.amountPaid.compareTo(calculateTotal()) >= 0;
    }

    public LocalDateTime getEffectiveDate() {
        return this.effectiveDate;
    }

    public LocalDateTime getExpectedPayment() {
        return this.expectedPayment;
    }
}
