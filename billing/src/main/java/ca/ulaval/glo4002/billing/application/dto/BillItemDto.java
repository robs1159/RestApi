package ca.ulaval.glo4002.billing.application.dto;


import ca.ulaval.glo4002.crmInterface.domain.ProductId;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BillItemDto {

    @JsonProperty
    public BigDecimal price;

    @JsonProperty
    public String note;

    @JsonProperty
    public ProductId productId;

    @JsonProperty
    public int quantity;
}
