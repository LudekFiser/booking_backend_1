package org.example.booking_appointment.mapper;


import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.hotel.HotelOwnerResponse;
import org.example.booking_appointment.entity.HotelOwner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelOwnerMapper {

    private final HotelMapper hotelMapper;


    public HotelOwnerResponse toHotelOwnerResponse(HotelOwner hotelOwner) {

        var hotelSummaries = hotelOwner.getHotels()
                .stream()
                .map(hotelMapper::toResponse)
                .toList();

        return HotelOwnerResponse.builder()
                .numOfHotels(hotelOwner.getNumOfHotels())
                .hotels(hotelSummaries)
                .build();
    }

    public HotelOwnerResponse toHotelOwnerPreviewResponse(HotelOwner ho) {
        return HotelOwnerResponse.builder()
                .numOfHotels(ho.calculateNumberOfHotels())
                .hotels(List.of())
                .build();
    }

}
