package org.example.booking_appointment.dto.rating;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RatingResponse {

    private Long id;
    private Long userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
