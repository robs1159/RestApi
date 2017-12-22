package ca.ulaval.glo4002.billing.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @OneToOne(cascade = CascadeType.ALL)
    @Embedded
    private ProductId productId;

    @Column
    private BigDecimal price;

    @Column
    private String note;

    @Column
    private int quantity;

    public BillItem() {
    }

    public BillItem(BigDecimal price, String note, ProductId productId, int quantity) {
        this.price = price;
        this.note = note;
        this.productId = productId;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal calculatePrice() {
        return getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }
}
