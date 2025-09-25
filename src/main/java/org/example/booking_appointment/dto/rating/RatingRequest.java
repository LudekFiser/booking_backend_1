package org.example.booking_appointment.dto.rating;

import lombok.Builder;
import lombok.Data;

@Data
public class RatingRequest {

    private Integer rating;
    private String comment;
}
