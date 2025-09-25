package org.example.booking_appointment.service;

import org.example.booking_appointment.dto.hotel.CreateHotelRequest;
import org.example.booking_appointment.dto.hotel.HotelResponse;
import org.example.booking_appointment.dto.room.CreateRoomRequest;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.entity.HotelImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {

    HotelResponse addHotel(CreateHotelRequest req, List<MultipartFile> images);

    HotelResponse getHotelById(Long hotelId);

    List<HotelResponse> getAllAvailableHotels(); // TODO
}
