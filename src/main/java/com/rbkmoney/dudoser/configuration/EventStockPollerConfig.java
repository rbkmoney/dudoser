package com.rbkmoney.dudoser.configuration;

import com.rbkmoney.dudoser.dao.LastEventDao;
import com.rbkmoney.dudoser.handler.poller.EventStockHandler;
import com.rbkmoney.dudoser.handler.poller.PollingEventHandler;
import com.rbkmoney.eventstock.client.EventPublisher;
import com.rbkmoney.eventstock.client.poll.PollingEventPublisherBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class EventStockPollerConfig {

    @Value("${bm.pooling.url}")
    Resource bmUri;

    @Value("${bm.pooling.delay}")
    int pollDelay;

    @Value("${bm.pooling.retryDelay}")
    int retryDelay;

    @Value("${bm.pooling.maxPoolSize}")
    int maxPoolSize;

    @Value("${bm.pooling.housekeeperTimeout}")
    int housekeeperTimeout;

    @Autowired
    List<PollingEventHandler> pollingEventHandlers;

    @Bean
    public List<EventPublisher> eventPublishers(List<EventStockHandler> eventStockHandlers,
            @Value("${bm.pooling.workersCount}") int workersCount) throws IOException {
        List<EventPublisher> eventPublishers = new ArrayList<>();
        for (int i = 0; i < workersCount; ++i) {
            eventPublishers.add(new PollingEventPublisherBuilder()
                    .withURI(bmUri.getURI())
                    .withHousekeeperTimeout(housekeeperTimeout)
                    .withEventHandler(eventStockHandlers.get(i))
                    .withMaxPoolSize(maxPoolSize)
                    .withPollDelay(pollDelay)
                    .withEventRetryDelay(retryDelay)
                    .build());
        }
        return eventPublishers;
    }

    @Bean
    public List<EventStockHandler> eventStockHandlers(@Value("${bm.pooling.workersCount}") int workersCount,
                                                      LastEventDao lastEventDao) {
        List<EventStockHandler> eventStockHandlers = new ArrayList<>();
        for (int i = 0; i < workersCount; ++i) {
            eventStockHandlers.add(new EventStockHandler(pollingEventHandlers, lastEventDao, workersCount, i));
        }
        return eventStockHandlers;
    }
}
