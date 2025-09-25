package org.example.booking_appointment.dto.hotel;


import lombok.Builder;
import lombok.Data;
import org.example.booking_appointment.dto.image.ImageDto;
import org.example.booking_appointment.dto.location.LocationResponse;
import org.example.booking_appointment.entity.Location;

import java.awt.*;
import java.util.List;

@Builder
@Data
public class HotelResponse {

    private Long id;
    private String name;
    private String description;
    private LocationResponse location;

    private List<ImageDto> images;
    private Long hotelOwnerId;
    private String hotelOwnerName;
    private Integer numberOfRooms;

    private Integer ratingsCount;
    private Double rating;


}
