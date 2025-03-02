package com.bank.tech.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @GetMapping("/contactsupport")
    public Mono<String> contactsupport()
    {
        return Mono.just("An ErrorAccord please try after som time or call the team");
    }
}
