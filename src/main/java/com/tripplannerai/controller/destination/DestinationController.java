package com.tripplannerai.controller.destination;

import com.tripplannerai.common.annotation.Id;
import com.tripplannerai.dto.response.destination.DestinationResponse;
import com.tripplannerai.dto.response.destination.DestinationsCategoryResponse;
import com.tripplannerai.dto.response.destination.DestinationsResponse;
import com.tripplannerai.dto.response.destination.TotalDestinationResponse;
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
    public ResponseEntity<DestinationResponse> fetchDestination(@PathVariable Long destinationId, @Id Long id) {
        DestinationResponse destinationResponse = destinationService.fetchDestination(destinationId,id);
        return ResponseEntity.ok(destinationResponse);
    }

    @GetMapping
    public ResponseEntity<DestinationsResponse> fetchDestinations(@RequestParam Integer page, @RequestParam Integer size,
                                                                  @RequestParam String name,@Id Long id) {
        DestinationsResponse destinationsResponse = destinationService.fetchDestinations(page,size,name,id);
        return new ResponseEntity<>(destinationsResponse, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<DestinationsCategoryResponse> fetchDestinationsByCategory(@RequestParam Integer page, @RequestParam Integer size, Long categoryId) {
        DestinationsCategoryResponse destinationsCategoryResponse = destinationService.fetchDestinationByCategory(page,size,categoryId);
        return new ResponseEntity<>(destinationsCategoryResponse, HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<TotalDestinationResponse> fetchDestinationsByTotal() {
        TotalDestinationResponse totalDestinationResponse = destinationService.fetchDestinationsByTotal();
        return new ResponseEntity<>(totalDestinationResponse, HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<DestinationsResponse> fetchMyDestinations(@RequestParam Integer page, @RequestParam Integer size,
                                                                    @Id Long id) {
        DestinationsResponse destinationsResponse = destinationService.fetchMyDestinations(page,size,id);
        return new ResponseEntity<>(destinationsResponse, HttpStatus.OK);
    }
}
