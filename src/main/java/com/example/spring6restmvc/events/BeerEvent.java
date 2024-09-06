package com.example.spring6restmvc.events;

import com.example.spring6restmvc.entity.Beer;

import org.springframework.security.core.Authentication;

public interface BeerEvent {

    Beer getBeer();
    Authentication getAuthentication();
}
