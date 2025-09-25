package org.example.booking_appointment.dto.location;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddLocationRequest {

    private String country;
    private String city;
    private String street;
    private String streetNumber;
    private String postalCode;
}
