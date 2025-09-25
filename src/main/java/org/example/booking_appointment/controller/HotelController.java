package org.example.booking_appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.booking.BookingRequest;
import org.example.booking_appointment.dto.booking.BookingResponse;
import org.example.booking_appointment.dto.hotel.CreateHotelRequest;
import org.example.booking_appointment.dto.hotel.HotelResponse;
import org.example.booking_appointment.dto.room.CreateRoomRequest;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.service.BookingService;
import org.example.booking_appointment.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {


    private final HotelService hotelService;

    @PostMapping("/add")
    public ResponseEntity<HotelResponse> addHotel(
            @RequestPart("add") CreateHotelRequest req,
            @RequestPart("images") List<MultipartFile> images) {
        var hotel = hotelService.addHotel(req, images);
        return ResponseEntity.ok(hotel);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long hotelId) {
        var hotel =  hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotel);
    }
}
