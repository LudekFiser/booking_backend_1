package org.example.booking_appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.booking.BookingRequest;
import org.example.booking_appointment.dto.booking.BookingResponse;
import org.example.booking_appointment.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final BookingService bookingService;

    @PostMapping("/{hotelId}/{roomId}/book")
    public ResponseEntity<BookingResponse> createBooking(@PathVariable Long hotelId,
                                                         @PathVariable Long roomId,
                                                         @RequestBody @Valid BookingRequest req) {
        var booking = bookingService.createBooking(req, hotelId, roomId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/{bookingConfirmationCode}")
    public ResponseEntity<BookingResponse> findByBookingConfirmationCode(@PathVariable String bookingConfirmationCode) {
        var booking = bookingService.getBookingByBookingConfirmationCode(bookingConfirmationCode);
        return ResponseEntity.ok(booking);
    }
}
