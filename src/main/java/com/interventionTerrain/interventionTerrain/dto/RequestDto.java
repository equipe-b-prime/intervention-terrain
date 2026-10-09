package com.interventionTerrain.interventionTerrain.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(AgenceRequestDto.class),
        @JsonSubTypes.Type(ClientRequestDto.class),
        @JsonSubTypes.Type(CompetenceRequestDto.class),
        @JsonSubTypes.Type(InterventionRequestDto.class),
        @JsonSubTypes.Type(TechnicienRequestDto.class)

})
public interface RequestDto { }
