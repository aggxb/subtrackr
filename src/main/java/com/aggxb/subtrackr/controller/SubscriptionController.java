package com.aggxb.subtrackr.controller;

import com.aggxb.subtrackr.dto.request.SubscriptionPostRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionPutRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionToggleStatusRequest;
import com.aggxb.subtrackr.dto.response.SubscriptionResponse;
import com.aggxb.subtrackr.dto.response.SummaryResponse;
import com.aggxb.subtrackr.enums.Order;
import com.aggxb.subtrackr.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Order sort) {
        log.debug("Request to get all subcriptions with params: {}, {}", name, sort);

        var subscriptionList = service.findWithFilters(name, sort);

        return ResponseEntity.ok(subscriptionList);
    }

    @GetMapping("summary")
    public ResponseEntity<SummaryResponse> getSummary() {
        log.debug("Request to get summary");

        var summary = service.getSummary();

        return ResponseEntity.ok(summary);
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> save(@RequestBody SubscriptionPostRequest subscriptionPostRequest) {
        log.debug("Request to save a new subscription");

        var subscription = service.save(subscriptionPostRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.debug("Request to delete a subscription with id {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@RequestBody SubscriptionPutRequest subscriptionPutRequest) {
        log.debug("Request to update a subscription with id {}", subscriptionPutRequest.id());

        service.update(subscriptionPutRequest);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("toggle/{id}")
    public ResponseEntity<Void> toggleStatus(@RequestBody SubscriptionToggleStatusRequest subscriptionToggleStatusRequest) {
        log.debug("Request to toggle status of a subscription with id {}", subscriptionToggleStatusRequest.id());

        service.toggleStatus(subscriptionToggleStatusRequest);

        return ResponseEntity.noContent().build();
    }
}
