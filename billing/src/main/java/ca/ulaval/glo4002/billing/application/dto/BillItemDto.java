package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.billing.domain.ProductId;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class BillItemDto {

    @JsonProperty
    @Valid
    @Min(0)
    public BigDecimal price;

    @JsonProperty
    public String note;

    @JsonProperty
    public ProductId productId;

    @JsonProperty
    @Valid
    @Min(0)
    public int quantity;
}
