package com.rbkmoney.dudoser.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "invoicing-service")
public class InvoicingServiceProperties {

    private Resource url;
    private int networkTimeout;

}
