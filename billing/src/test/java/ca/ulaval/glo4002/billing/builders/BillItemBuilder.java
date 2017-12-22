package ca.ulaval.glo4002.billing.builders;

import ca.ulaval.glo4002.billing.domain.BillItem;
import ca.ulaval.glo4002.billing.domain.ProductId;

import java.math.BigDecimal;

public class BillItemBuilder {

    private static final ProductId VALID_PRODUCT_ID = new ProductId(1);
    private static final int VALID_QUANTITY = 1;
    private static final String VALID_NOTE = "note";
    private static final BigDecimal VALID_PRICE = new BigDecimal(10.89);

    private ProductId productId;
    private int quantity;
    private BigDecimal price;
    private String note;

    public BillItemBuilder withProductId(ProductId productId) {
        this.productId = productId;
        return this;
    }

    public BillItemBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BillItemBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BillItemBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    public BillItemBuilder withValidValues() {
        this.productId = VALID_PRODUCT_ID;
        this.quantity = VALID_QUANTITY;
        this.price = VALID_PRICE;
        this.note = VALID_NOTE;

        return this;
    }

    public BillItem build() {
        return new BillItem(price, note, productId, quantity);
    }
}
