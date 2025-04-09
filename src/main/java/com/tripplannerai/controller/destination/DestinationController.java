package com.tripplannerai.controller.destination;

import com.tripplannerai.annotation.Username;
import com.tripplannerai.dto.response.destination.DestinationResponse;
import com.tripplannerai.dto.response.destination.DestinationsResponse;
import com.tripplannerai.service.destination.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/destination")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationService destinationService;
    @GetMapping("/{destinationId}")
    public ResponseEntity<DestinationResponse> fetchDestination(@PathVariable Long destinationId, @Username String email) {
        DestinationResponse destinationResponse = destinationService.fetchDestination(destinationId,email);
        return ResponseEntity.ok(destinationResponse);
    }

    @GetMapping
    public ResponseEntity<DestinationsResponse> fetchDestinations(@RequestParam Integer page, @RequestParam Integer size) {
        DestinationsResponse destinationsResponse = destinationService.fetchDestinations(page,size);
        return new ResponseEntity<>(destinationsResponse, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<?> fetchDestinationsByCategory(@RequestParam Integer page, @RequestParam Integer size,@RequestParam String category) {
        DestinationsResponse destinationsResponse = destinationService.fetchDestinationByCategory(page,size,category);
        return new ResponseEntity<>(destinationsResponse, HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<?> fetchDestinationsByTotal() {
        DestinationsResponse destinationsResponse = destinationService.fetchDestinationsByTotal();
        return new ResponseEntity<>(destinationsResponse, HttpStatus.OK);
    }
}
