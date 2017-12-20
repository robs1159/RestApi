package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.billing.domain.ClientId;
import ca.ulaval.glo4002.billing.domain.DueTerm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class BillDto {

    @JsonProperty
    public ClientId clientId;

    @JsonProperty
    public String creationDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public DueTerm dueTerm;

    @JsonProperty
    public List<BillItemDto> items;

    @JsonProperty
    public long id;

    @JsonProperty
    public BigDecimal total;

    @JsonProperty
    public String url;
}