package com.aggxb.subtrackr.controller;

import com.aggxb.subtrackr.dto.request.SubscriptionPostRequest;
import com.aggxb.subtrackr.dto.request.SubscriptionPutRequest;
import com.aggxb.subtrackr.dto.response.SubscriptionResponse;
import com.aggxb.subtrackr.dto.response.SummaryResponse;
import com.aggxb.subtrackr.enums.OwnershipType;
import com.aggxb.subtrackr.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin(origins = {"https://subtrackr-web.vercel.app", "http://localhost:5173/", "http://localhost:4173/"})
@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<SubscriptionResponse>> findAll(
            @RequestParam(required = false) String term,
            @RequestParam(required = false) OwnershipType ownershipType,
            @PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Request to get all subscriptions with params: term = {}, ownership type = {}", term, ownershipType);

        var subscriptionPage = service.findWithFilters(term, ownershipType, pageable);

        return ResponseEntity.ok(subscriptionPage);
    }

    @GetMapping("summary")
    public ResponseEntity<SummaryResponse> getSummary(@RequestParam(required = false) OwnershipType ownershipType) {
        log.info("Request to get summary");

        var summary = service.getSummary(ownershipType);

        return ResponseEntity.ok(summary);
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> save(@RequestBody SubscriptionPostRequest subscriptionPostRequest) {
        log.info("Request to save a new subscription");

        var subscription = service.save(subscriptionPostRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Request to delete a subscription with id {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody SubscriptionPutRequest subscriptionPutRequest) {
        log.info("Request to update a subscription with id {}", id);

        service.update(id, subscriptionPutRequest);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("toggle/{id}")
    public ResponseEntity<Void> toggleStatus(@PathVariable UUID id) {
        log.info("Request to toggle status of a subscription with id {}", id);

        service.toggleStatus(id);

        return ResponseEntity.noContent().build();
    }
}
