package com.example.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractErrorAwareResponse {
    @JsonProperty("error")
    private ErrorDto errorDto;
    @JsonIgnore
    private Integer status;

    public AbstractErrorAwareResponse(final ErrorDto errorDto) {
        this.errorDto = errorDto;
        this.status = errorDto.getHttpStatus().value();
    }

    public AbstractErrorAwareResponse() {
        this.status = 200;
    }
}
