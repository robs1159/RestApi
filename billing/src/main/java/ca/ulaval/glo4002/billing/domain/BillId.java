package ca.ulaval.glo4002.billing.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BillId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private final long billId;

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
