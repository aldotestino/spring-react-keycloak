package com.at.srk.rest.controller;

import com.at.srk.rest.controller.dto.MessageInput;
import com.at.srk.rest.controller.dto.SimpleResponse;
import com.at.srk.rest.model.Message;
import com.at.srk.rest.service.MessageService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAuthority('USER_ROLE')") // Here we can define the role that can access this endpoint
public class MessageController {

    private final MessageService messageService;

    @GetMapping

    public List<Message> getAll() {
        return messageService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    // Can also use JwtAuthenticationToken (JWT Decoder) or
    // BearerTokenAuthentication (JWT Introspection)
    public Message create(@RequestBody MessageInput messageInput, Principal principal) {
        return messageService.create(messageInput, principal.getName());
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteAll() {
        messageService.deleteAll();
        return new SimpleResponse("All messages deleted");
    }
}
