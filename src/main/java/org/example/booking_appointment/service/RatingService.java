package org.example.booking_appointment.service;

import org.example.booking_appointment.dto.rating.RatingRequest;
import org.example.booking_appointment.dto.rating.RatingResponse;

import java.util.List;

public interface RatingService {

    RatingResponse addRating(RatingRequest request, Long hotelId);

    List<RatingResponse> getRatingsByHotelId(Long hotelId);
}
