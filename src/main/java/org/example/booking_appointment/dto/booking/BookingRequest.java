package org.example.booking_appointment.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class BookingRequest {

    /*private String country;
    private String city;*/

    @NotNull(message = "check-in date is required!")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate checkInDate;

    @NotNull(message = "check-out date is required!")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate checkOutDate;

    @NotNull(message = "Enter a valid amount of adults")
    private Integer adults;

    //@NotNull(message = "Enter a valid amount of children")
    private Integer children;
}
