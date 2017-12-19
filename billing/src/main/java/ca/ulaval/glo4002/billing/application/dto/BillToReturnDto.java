package ca.ulaval.glo4002.billing.application.dto;

import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BillToReturnDto {

    @JsonProperty
    public long id;

    @JsonProperty
    public BigDecimal total;

    @JsonProperty
    public DueTerm dueTerm;

    @JsonProperty
    public String url;
}
