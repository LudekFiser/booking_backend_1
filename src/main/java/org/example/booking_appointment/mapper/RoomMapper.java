package org.example.booking_appointment.mapper;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.dto.room.CreateRoomRequest;
import org.example.booking_appointment.entity.Room;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final ImageMapper imageMapper;

    public Room toRoomEntity(CreateRoomRequest req) {
        return Room.builder()
                .name(req.getName())
                .description(req.getDescription())
                .roomType(req.getRoomType())
                .roomPrice(req.getPrice())
                .capacity(req.getCapacity())
                .build();
    }

    public RoomSummaryDto toRoomResponse(Room s) {
        return RoomSummaryDto.builder()
                .id(s.getId())
                .name(s.getName())
                .roomPrice(s.getRoomPrice())
                .roomType(s.getRoomType())
                .roomNumber(s.getRoomNumber())
                .capacity(s.getCapacity())
                .hotelId(s.getHotel().getId())
                .hotelName(s.getHotel().getName())
                .roomImage(imageMapper.toRoomImageDtoList(s.getRoomImages()))
                .description(s.getDescription())
                .isBooked(s.isBooked())
                .build();
    }

}
