package org.example.booking_appointment.mapper;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.location.AddLocationRequest;
import org.example.booking_appointment.dto.location.LocationResponse;
import org.example.booking_appointment.entity.Location;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationMapper {



    public Location toEntity(AddLocationRequest req) {
        return Location.builder()
                .city(req.getCity())
                .country(req.getCountry())
                .street(req.getStreet())
                .streetNumber(req.getStreetNumber())
                .postalCode(req.getPostalCode())
                .build();
    }


    public LocationResponse toResponse(Location loc) {
        return LocationResponse.builder()
                .city(loc.getCity())
                .country(loc.getCountry())
                .street(loc.getStreet())
                .streetNumber(loc.getStreetNumber())
                .postalCode(loc.getPostalCode())
                .build();
    }
}
