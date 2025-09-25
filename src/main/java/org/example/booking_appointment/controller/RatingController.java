package org.example.booking_appointment.controller;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.rating.RatingRequest;
import org.example.booking_appointment.dto.rating.RatingResponse;
import org.example.booking_appointment.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {


    private final RatingService ratingService;

    @PostMapping("/{hotelId}/add")
    public ResponseEntity<RatingResponse> addRating(@PathVariable("hotelId") Long hotelId, @RequestBody RatingRequest ratingRequest) {

        var rating = ratingService.addRating(ratingRequest, hotelId);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/{hotelId}/all")
    public ResponseEntity<List<RatingResponse>> getAllRatings(@PathVariable("hotelId") Long hotelId) {

        var ratings = ratingService.getRatingsByHotelId(hotelId);
        return ResponseEntity.ok(ratings);
    }
}
