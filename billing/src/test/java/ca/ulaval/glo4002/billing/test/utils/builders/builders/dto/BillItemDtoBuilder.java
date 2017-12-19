package ca.ulaval.glo4002.billing.test.utils.builders.builders.dto;

import ca.ulaval.glo4002.billing.application.dto.BillItemDto;
import ca.ulaval.glo4002.crmInterface.domain.ProductId;

import java.math.BigDecimal;

public class BillItemDtoBuilder {

    private BigDecimal validPrice = new BigDecimal(10.99);
    private String validNote = "Une note sur le produit";
    private ProductId validProductId = new ProductId(1);
    private int validQuantity = 3;

    private BigDecimal price;
    private String note;
    private ProductId productId;
    private int quantity;

    public BillItemDtoBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BillItemDtoBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    public BillItemDtoBuilder withProductId(ProductId productId) {
        this.productId = productId;
        return this;
    }

    public BillItemDtoBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BillItemDtoBuilder withValidValues() {
        this.price = validPrice;
        this.note = validNote;
        this.productId = validProductId;
        this.quantity = validQuantity;

        return this;
    }

    public BillItemDto build() {
        BillItemDto buildBillItemDto = new BillItemDto();
        buildBillItemDto.note = this.note;
        buildBillItemDto.price = this.price;
        buildBillItemDto.productId = this.productId;
        buildBillItemDto.quantity = this.quantity;

        return buildBillItemDto;
    }
}
