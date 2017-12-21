package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {

    @JsonProperty
    public long id;

    @JsonProperty
    public ClientId clientId;

    @JsonProperty
    public String creationDate;

    @JsonProperty
    public BigDecimal total;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public DueTerm dueTerm;

    @JsonProperty
    public List<BillItemDto> items;

    @JsonProperty
    public String url;
}