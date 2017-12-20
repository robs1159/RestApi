package ca.ulaval.glo4002.billing.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class PaymentId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private long paymentId;

    //TODO:Sortir la génération dans une statégie
    public PaymentId() {
        this.paymentId = Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    public long getUniqueId() {
        return this.paymentId;
    }
}
