package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.damsel.payment_processing.PartyManagementSrv;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class PartyManagementConfig {

    @Bean
    public PartyManagementSrv.Iface partyManagementSrv(@Value("${hellgate.url}") Resource resource,
                                                       @Value("${hellgate.networkTimeout}") int networkTimeout) throws IOException {
        return new THSpawnClientBuilder()
                .withNetworkTimeout(networkTimeout)
                .withAddress(resource.getURI()).build(PartyManagementSrv.Iface.class);
    }
}
