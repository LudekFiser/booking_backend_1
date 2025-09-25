package org.example.booking_appointment.dto.hotel;

import lombok.Builder;
import lombok.Data;
import org.example.booking_appointment.entity.Hotel;

import java.util.List;

@Data
@Builder
public class HotelOwnerResponse {

    private Integer numOfHotels;
    private List<HotelResponse> hotels;
}
