package org.example.booking_appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsRequest;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsResponse;
import org.example.booking_appointment.dto.room.CreateRoomRequest;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.entity.Room;
import org.example.booking_appointment.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/{hotelId}/add-room")
    public ResponseEntity<RoomSummaryDto> addRoom(@PathVariable Long hotelId,
                                                  @RequestPart("add") CreateRoomRequest req,
                                                  @RequestPart("images") List<MultipartFile> images) {
        var addedRoom = roomService.addRoom(req, images, hotelId);
        return ResponseEntity.ok(addedRoom);
    }

    @PostMapping("/search-available")
    public ResponseEntity<List<RoomSummaryDto>> searchAvailableRooms(@Valid @RequestBody SearchAvailableRoomsRequest req) {
        var search = roomService.searchAvailableRooms(req);
        return ResponseEntity.ok(search);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomSummaryDto>> getRoomsByHotelId(@PathVariable Long hotelId) {
        var rooms = roomService.getRoomsByHotelId(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomSummaryDto> getRoomById(@PathVariable Long roomId) {
        var room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }
}
