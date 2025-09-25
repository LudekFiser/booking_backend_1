package org.example.booking_appointment.mapper;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.rating.RatingRequest;
import org.example.booking_appointment.dto.rating.RatingResponse;
import org.example.booking_appointment.entity.Rating;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingMapper {


    public Rating toEntity(RatingRequest req) {
        return Rating.builder()
                .rating(req.getRating())
                .comment(req.getComment())
                .build();
    }

    public RatingResponse toResponse(Rating r) {
        return RatingResponse.builder()
                .id(r.getId())
                .rating(r.getRating())
                .comment(r.getComment())
                .createdAt(r.getCreatedAt())
                .userId(r.getUser().getProfileId())
                .build();
    }
}
