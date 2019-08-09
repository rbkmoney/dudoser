package com.rbkmoney.dudoser.service.template.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.dudoser.service.template.serialize.KebMetadataDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@JsonDeserialize(using = KebMetadataDeserializer.class)
@Getter
@Setter
public class KebMetadata {

    private String txnId;

    private Integer checkResultCode;

    private String customerInitials;

    private Boolean commission;

    private String account;

    private BigDecimal sum;

    private BigDecimal commissionAmount;

}
