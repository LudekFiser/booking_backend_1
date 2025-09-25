package org.example.booking_appointment.dto.booking;

import lombok.Builder;
import lombok.Data;
import org.example.booking_appointment.dto.room.RoomSummaryDto;

import java.time.LocalDate;

@Data
@Builder
public class BookingResponse {

    private Long id;

    private Long userId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String guestFullName;
    private String guestEmail;

    private Integer adults;
    private Integer children;
    private Integer totalGuests;

    private String bookingConfirmationCode;
    private RoomSummaryDto room;
}
