package ca.ulaval.glo4002.payment.domain.payment;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class PaymentId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private long paymentId;

    public PaymentId() {
        this.paymentId = Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    public long getUniqueId() {
        return this.paymentId;
    }
}
