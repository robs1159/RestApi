package ca.ulaval.glo4002.crmInterface.domain;

import javax.persistence.*;

@Entity
public class ProductId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int dbId;

    @Column
    private int productId;

    public ProductId() {
    }

    public ProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return Integer.toString(this.productId);
    }
}
