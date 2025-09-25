package org.example.booking_appointment.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsRequest;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsResponse;
import org.example.booking_appointment.dto.image.UploadedImageDto;
import org.example.booking_appointment.dto.room.CreateRoomRequest;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.entity.Booking;
import org.example.booking_appointment.entity.Hotel;
import org.example.booking_appointment.entity.Location;
import org.example.booking_appointment.entity.Room;
import org.example.booking_appointment.enums.ROLE;
import org.example.booking_appointment.exception.UserNotFoundException;
import org.example.booking_appointment.mapper.RoomMapper;
import org.example.booking_appointment.repository.HotelRepository;
import org.example.booking_appointment.repository.RoomRepository;
import org.example.booking_appointment.service.AuthService;
import org.example.booking_appointment.service.RoomService;
import org.example.booking_appointment.utils.cloudinary.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    private final AuthService authService;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;
    private final CloudinaryService cloudinaryService;
    private final RoomRepository roomRepository;

    // DONE
    @Override
    public RoomSummaryDto addRoom(CreateRoomRequest req, List<MultipartFile> images, Long hotelId) {
        var currentProfile = authService.getCurrentProfile();
        if(currentProfile == null) {
            throw new UserNotFoundException();
        }
        if (!currentProfile.getRole().equals(ROLE.HOTEL_OWNER)) {
            throw new AccessDeniedException("You are not allowed to perform this action");
        }

        //var hotel = hotelRepository.findById(req.getHotel().getId()).orElseThrow();
        var hotel = hotelRepository.findById(hotelId).orElseThrow();
        if (!hotel.getHotelOwner().equals(currentProfile.getHotelOwner())) {
            throw new AccessDeniedException("You are not allowed to perform this action");
        }
        var room = roomMapper.toRoomEntity(req);

        if (images.size() > 10) {
            throw new RuntimeException("You cant upload more than 10 images");
        }

        for (MultipartFile image : images) {
            if (image == null || image.isEmpty()) {
                throw new RuntimeException("The image is empty");
            }
            UploadedImageDto uploadedImage = cloudinaryService.uploadImage(image, "room_images");
            var roomImage = cloudinaryService.buildRoomImage(uploadedImage, room);
            room.addImage(roomImage);
        }

        room.setHotel(hotel);

        List<Integer> numbers = roomRepository.findAllRoomNumbersByHotelId(hotelId);
        int next = 1;
        for (Integer n : numbers) {
            if (n == next) {
                next++;
            } else {
                break;
            }
        }
        room.setRoomNumber(next);
        roomRepository.save(room);

        hotel.addRoom(room);
        hotel.setNumOfRooms(hotel.getNumberOfRooms());
        hotelRepository.save(hotel);

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomSummaryDto getRoomById(Long roomId) {
        var room = roomRepository.findById(roomId).orElseThrow();
        return roomMapper.toRoomResponse(room);
    }

    @Override
    public List<RoomSummaryDto> getRoomsByHotelId(Long hotelId) {
        var rooms = roomRepository.getRoomsByHotelId(hotelId);
        return rooms.stream()
                .map(roomMapper::toRoomResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<RoomSummaryDto> searchAvailableRooms(SearchAvailableRoomsRequest req) {

        if (req.getCheckInDate().isAfter(req.getCheckOutDate()) || req.getCheckInDate().equals(req.getCheckOutDate())) {
            throw new RuntimeException("Check-in must be BEFORE check-out date");
        }

        if (req.getCity() == null || req.getCity().isBlank()) {
            throw new RuntimeException("City is required");
        }
        List<Room> rooms = roomRepository.findAvailableRoomsByCityAndDates(
                req.getCity(),
                req.getCheckInDate(),
                req.getCheckOutDate()
        );
        return rooms.stream().map(roomMapper::toRoomResponse).toList();
    }
}
