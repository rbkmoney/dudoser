package com.rbkmoney.dudoser.listener;

import com.rbkmoney.dudoser.handler.poller.EventStockHandler;
import com.rbkmoney.dudoser.service.EventService;
import com.rbkmoney.eventstock.client.*;
import com.rbkmoney.eventstock.client.poll.EventFlowFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OnStart implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    List<EventPublisher> eventPublishers;
    @Autowired
    List<EventStockHandler> eventStockHandlers;
    @Autowired
    EventService eventService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (int i = 0; i < eventPublishers.size(); ++i) {
            EventStockHandler eventStockHandler = eventStockHandlers.get(i);
            EventPublisher eventPublisher = eventPublishers.get(i);
            Long lastEventId = eventService.getLastEventId(eventStockHandler.getMod());
            eventPublisher.subscribe(buildSubscriberConfig(lastEventId));
        }
    }

    private SubscriberConfig buildSubscriberConfig(Long lastEventId) {
        EventConstraint.EventIDRange eventIDRange = new EventConstraint.EventIDRange();
        if (lastEventId != null) {
            eventIDRange.setFromExclusive(lastEventId);
        } else {
            eventIDRange.setFromNow();
        }
        return new DefaultSubscriberConfig(new EventFlowFilter(new EventConstraint(eventIDRange)));
    }

}
