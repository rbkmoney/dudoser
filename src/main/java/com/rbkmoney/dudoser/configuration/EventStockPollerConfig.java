package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.dudoser.handler.Handler;
import com.rbkmoney.dudoser.handler.poller.EventStockErrorHandler;
import com.rbkmoney.dudoser.handler.poller.EventStockHandler;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.eventstock.client.*;
import com.rbkmoney.eventstock.client.poll.EventFlowFilter;
import com.rbkmoney.eventstock.client.poll.PollingEventPublisherBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Configuration
public class EventStockPollerConfig {

    @Value("${bm.pooling.url}")
    Resource bmUri;

    @Value("${bm.pooling.delay}")
    int pollDelay;

    @Value("${bm.pooling.maxPoolSize}")
    int maxPoolSize;

    @Autowired
    List<Handler> handlers;

    @Autowired
    EventService eventService;

    @Bean(destroyMethod = "destroy")
    public EventPublisher eventPublisher() throws IOException {
        return new PollingEventPublisherBuilder()
                .withURI(bmUri.getURI())
                .withEventHandler(new EventStockHandler(handlers))
                .withErrorHandler(new EventStockErrorHandler())
                .withMaxPoolSize(maxPoolSize)
                .withPollDelay(pollDelay)
                .build();
    }

    @Bean
    public SubscriberConfig subscriberConfig() {
        return new DefaultSubscriberConfig(eventFilter());
    }

    public EventFilter eventFilter() {
        EventConstraint.EventIDRange eventIDRange = new EventConstraint.EventIDRange();
        Long lastEventId = eventService.getLastEventId();
        if (lastEventId != null) {
            eventIDRange.setFromInclusive(lastEventId);
        } else {
            eventIDRange.setFromNow();
        }
        return new EventFlowFilter(new EventConstraint(eventIDRange));
    }

}
