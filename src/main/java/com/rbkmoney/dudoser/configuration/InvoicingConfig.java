package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.damsel.payment_processing.InvoicingSrv;
import com.rbkmoney.dudoser.configuration.properties.InvoicingServiceProperties;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class InvoicingConfig {

    @Bean
    public InvoicingSrv.Iface invoicingClient(InvoicingServiceProperties invoicingServiceProperties) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(invoicingServiceProperties.getUrl().getURI())
                .withNetworkTimeout(invoicingServiceProperties.getNetworkTimeout())
                .build(InvoicingSrv.Iface.class);
    }
}
