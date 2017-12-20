package ca.ulaval.glo4002.billing.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BillId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;
    //TODO : 2 id
    @Column
    private final long billId;

    //TODO:Sortir la génération dans une statégie
    public BillId() {
        this.billId = Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    public BillId(Long billId) {
        this.billId = billId;
    }

    public long getId() {
        return this.billId;
    }

}
