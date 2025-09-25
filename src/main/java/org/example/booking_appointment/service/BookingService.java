package org.example.booking_appointment.service;

import org.example.booking_appointment.dto.booking.BookingRequest;
import org.example.booking_appointment.dto.booking.BookingResponse;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsRequest;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest req, Long hotelId, Long roomId);

    BookingResponse getBookingByBookingConfirmationCode(String bookingConfirmationCode);
}
