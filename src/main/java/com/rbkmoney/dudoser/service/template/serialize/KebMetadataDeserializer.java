package com.rbkmoney.dudoser.service.template.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rbkmoney.dudoser.service.template.model.KebMetadata;
import com.rbkmoney.dudoser.utils.Converter;

import java.io.IOException;
import java.math.BigDecimal;

public class KebMetadataDeserializer extends JsonDeserializer<KebMetadata> {

    @Override
    public KebMetadata deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        KebMetadata kebMetaData = new KebMetadata();
        JsonNode txnIdNode = node.get("txnId");
        if (txnIdNode != null) {
            kebMetaData.setTxnId(txnIdNode.asText());
        }
        JsonNode checkResultCodeNode = node.get("checkResultCode");
        if (checkResultCodeNode != null) {
            kebMetaData.setCheckResultCode(checkResultCodeNode.asInt());
        }
        JsonNode customerInitialsNode = node.get("customerInitials");
        if (customerInitialsNode != null) {
            kebMetaData.setCustomerInitials(customerInitialsNode.asText());
        }
        JsonNode commissionNode = node.get("commission");
        if (commissionNode != null) {
            if (commissionNode.asInt(0) == 1) {
                kebMetaData.setCommission(true);
            } else {
                kebMetaData.setCommission(false);
            }
        }
        JsonNode accountNode = node.get("account");
        if (accountNode != null) {
            kebMetaData.setAccount(accountNode.asText());
        }
        JsonNode sumNode = node.get("sum");
        if (sumNode != null) {
            kebMetaData.setSum(Converter.longToBigDecimal(sumNode.asLong()));
        }
        JsonNode commissionAmountNode = node.get("commissionAmount");
        if (commissionAmountNode != null) {
            String commissionAmount = commissionAmountNode.asText();
            kebMetaData.setCommissionAmount(new BigDecimal(commissionAmount));
        }

        return kebMetaData;
    }

}
