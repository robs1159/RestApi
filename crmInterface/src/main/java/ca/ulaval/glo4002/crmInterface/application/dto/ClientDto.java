package ca.ulaval.glo4002.crmInterface.application.dto;

import ca.ulaval.glo4002.crmInterface.domain.DueTerm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDto {

    @JsonProperty
    public DueTerm defaultTerm;
}
