package com.example.spring6restmvc.listeners;

import com.example.spring6restmvc.events.BeerCreatedEvent;
import com.example.spring6restmvc.events.BeerDeletedEvent;
import com.example.spring6restmvc.events.BeerEvent;
import com.example.spring6restmvc.events.BeerPatchedEvent;
import com.example.spring6restmvc.events.BeerUpdatedEvent;
import com.example.spring6restmvc.mapper.BeerMapper;
import com.example.spring6restmvc.repository.BeerAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerCreatedListener {

    private final BeerMapper beerMapper;
    private final BeerAuditRepository beerAuditRepository;

    @Async
    @EventListener
    public void listen(BeerEvent event) {
        val beerAudit = beerMapper.toBeerAudit(event.getBeer());
        String eventType = extractedEventTypeAsString(event);

        beerAudit.setAuditEventType("BEER_CREATED");

        if (event.getAuthentication() != null && event.getAuthentication().getName() != null) {
            beerAudit.setPrincipalName(event.getAuthentication().getName());
        }

        val savedBeerAudit = beerAuditRepository.save(beerAudit);
    }

    private static String extractedEventTypeAsString(BeerEvent event) {
        if (event instanceof BeerCreatedEvent) return "BEER_CREATED";
        else if (event instanceof BeerPatchedEvent) return "BEER_PATCHED";
        else if (event instanceof BeerUpdatedEvent) return "BEER_UPDATED";
        else if (event instanceof BeerDeletedEvent) return "BEER_DELETED";
        return "UNKNOWN";
    }
}
