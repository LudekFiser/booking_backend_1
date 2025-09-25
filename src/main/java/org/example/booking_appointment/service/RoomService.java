package org.example.booking_appointment.service;

import org.example.booking_appointment.dto.booking.SearchAvailableRoomsRequest;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsResponse;
import org.example.booking_appointment.dto.room.CreateRoomRequest;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {

    RoomSummaryDto addRoom(CreateRoomRequest req, List<MultipartFile> images, Long hotelId);

    RoomSummaryDto getRoomById(Long roomId);

    List<RoomSummaryDto> getRoomsByHotelId(Long hotelId);

    List<RoomSummaryDto> searchAvailableRooms(SearchAvailableRoomsRequest req);

}
