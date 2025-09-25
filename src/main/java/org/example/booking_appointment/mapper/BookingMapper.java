package org.example.booking_appointment.mapper;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.booking.BookingRequest;
import org.example.booking_appointment.dto.booking.BookingResponse;
import org.example.booking_appointment.dto.hotel.CreateHotelRequest;
import org.example.booking_appointment.dto.hotel.HotelResponse;
import org.example.booking_appointment.entity.Booking;
import org.example.booking_appointment.entity.Hotel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final RoomMapper roomMapper;

    public Booking toEntity(BookingRequest req) {
        return Booking.builder()
                .checkIn(req.getCheckInDate())
                .checkOut(req.getCheckOutDate())
                .adults(req.getAdults())
                .children(req.getChildren())
                .build();
    }

    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUser().getProfileId())
                .checkInDate(booking.getCheckIn())
                .checkOutDate(booking.getCheckOut())
                .guestFullName(booking.getGuestFullName())
                .guestEmail(booking.getGuestEmail())
                .adults(booking.getAdults())
                .children(booking.getChildren())
                .totalGuests(booking.getTotalGuests())
                .bookingConfirmationCode(booking.getBookingConfirmationCode())
                .room(roomMapper.toRoomResponse(booking.getRoom()))
                .build();
    }
}
