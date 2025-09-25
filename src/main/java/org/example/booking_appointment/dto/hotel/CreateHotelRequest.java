package org.example.booking_appointment.dto.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.booking_appointment.dto.location.AddLocationRequest;
import org.example.booking_appointment.entity.Location;

@Data
public class CreateHotelRequest {

    @NotBlank(message = "Hotel name is required!")
    private String hotelName;

    @NotBlank(message = "Hotel Description is required!")
    private String description;

    @NotBlank(message = "Location of your hotel is required!")
    private AddLocationRequest addLocation;
}
