package org.example.booking_appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.rating.RatingRequest;
import org.example.booking_appointment.dto.rating.RatingResponse;
import org.example.booking_appointment.enums.ROLE;
import org.example.booking_appointment.exception.UserNotFoundException;
import org.example.booking_appointment.mapper.RatingMapper;
import org.example.booking_appointment.repository.HotelRepository;
import org.example.booking_appointment.repository.RatingRepository;
import org.example.booking_appointment.service.AuthService;
import org.example.booking_appointment.service.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {


    private final AuthService authService;
    private final RatingMapper ratingMapper;
    private final HotelRepository hotelRepository;
    private final RatingRepository ratingRepository;

    @Override
    public RatingResponse addRating(RatingRequest request, Long hotelId) {
        var currentProfile = authService.getCurrentProfile();
        if (currentProfile == null) {
            throw new UserNotFoundException();
        }

        if (!currentProfile.getRole().equals(ROLE.USER)) {
            throw new RuntimeException("Only users can write comments");
        }

        if (request.getRating() < 0 || request.getRating() > 5) {
            throw new RuntimeException("Invalid rating");
        }


        var rating = ratingMapper.toEntity(request);
        var hotel = hotelRepository.findById(hotelId).orElseThrow(UserNotFoundException::new);

        rating.setHotel(hotel);
        rating.setUser(currentProfile.getUser());
        rating.setCreatedAt(LocalDateTime.now());
        ratingRepository.save(rating);

        //var ratings = ratingRepository.findAllByHotelId(hotelId);
        hotel.getRatings().add(rating);

        hotel.setRatingsCount(hotel.getRatings().size());
        hotel.setRating(hotel.calculateRating());

        hotelRepository.save(hotel);
        return ratingMapper.toResponse(rating);
    }

    @Override
    public List<RatingResponse> getRatingsByHotelId(Long hotelId) {

        var ratings = ratingRepository.findAllByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new RuntimeException("No ratings found");
        }
        return ratings.stream().map(ratingMapper::toResponse).collect(Collectors.toList());
    }
}
