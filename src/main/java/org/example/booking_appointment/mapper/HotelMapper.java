package org.example.booking_appointment.mapper;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.auth.ProfileResponse;
import org.example.booking_appointment.dto.auth.RegisterRequest;
import org.example.booking_appointment.dto.hotel.CreateHotelRequest;
import org.example.booking_appointment.dto.hotel.HotelResponse;
import org.example.booking_appointment.entity.Hotel;
import org.example.booking_appointment.entity.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelMapper {


    private final ImageMapper imageMapper;
    private final LocationMapper locationMapper;

    public Hotel toEntity(CreateHotelRequest req) {
        return Hotel.builder()
                .name(req.getHotelName())
                .description(req.getDescription())
                .build();
    }

    public HotelResponse toResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .numberOfRooms(hotel.getNumberOfRooms())
                .hotelOwnerName(hotel.getHotelOwner().getProfile().getFirstName() + " " + hotel.getHotelOwner().getProfile().getLastName())
                .hotelOwnerId(hotel.getHotelOwner().getProfileId())
                //.hotelOwner(hotelOwnerMapper.toHotelOwnerResponse(hotel.getHotelOwner()))
                .images(imageMapper.toHotelImageDtoList(hotel.getHotelImages()))
                .location(locationMapper.toResponse(hotel.getLocation()))
                .ratingsCount(hotel.getRatingsCount())
                .rating(hotel.getRating())
                .build();
    }
}
