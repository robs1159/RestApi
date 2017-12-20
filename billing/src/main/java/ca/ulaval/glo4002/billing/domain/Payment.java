package ca.ulaval.glo4002.billing.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    //TODO:2 id
    @OneToOne(cascade = CascadeType.ALL)
    @Embedded
    private PaymentId paymentId;

    @OneToOne(cascade = CascadeType.ALL)
    private ClientId clientId;

    @Column
    private BigDecimal amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentMethod")
    private PaymentMethod paymentMethod;

    public Payment(ClientId clientId, float amount, PaymentMethod paymentMethod) {
        this.paymentId = createPaymentId();
        this.clientId = clientId;
        this.amount = new BigDecimal(amount);
        this.paymentMethod = paymentMethod;
    }

    private PaymentId createPaymentId() {
        return new PaymentId();
    }

    public PaymentId getPaymentId() {
        return this.paymentId;
    }

    public ClientId getClientId() {
        return this.clientId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
