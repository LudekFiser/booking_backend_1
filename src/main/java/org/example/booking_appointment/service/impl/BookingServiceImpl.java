package org.example.booking_appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.booking.BookingRequest;
import org.example.booking_appointment.dto.booking.BookingResponse;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsRequest;
import org.example.booking_appointment.dto.booking.SearchAvailableRoomsResponse;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.entity.Room;
import org.example.booking_appointment.enums.ROLE;
import org.example.booking_appointment.exception.NotFoundException;
import org.example.booking_appointment.exception.UserNotFoundException;
import org.example.booking_appointment.mapper.BookingMapper;
import org.example.booking_appointment.mapper.RoomMapper;
import org.example.booking_appointment.repository.BookingRepository;
import org.example.booking_appointment.repository.HotelRepository;
import org.example.booking_appointment.repository.RoomRepository;
import org.example.booking_appointment.repository.UserRepository;
import org.example.booking_appointment.service.AuthService;
import org.example.booking_appointment.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final AuthService authService;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomMapper roomMapper;

    /*@Override
    public BookingResponse createBooking(BookingRequest req, Long hotelId, Long roomId) {
        var currentProfile = authService.getCurrentProfile();
        if(currentProfile == null) {
            throw new UserNotFoundException();
        }

        if (req.getCheckInDate().isAfter(req.getCheckOutDate()) || req.getCheckInDate().equals(req.getCheckOutDate())) {
            throw new RuntimeException("Check-in must be BEFORE check-out date");
        }

        var booking = bookingMapper.toEntity(req);
        var hotel = hotelRepository.findById(hotelId).orElseThrow(UserNotFoundException::new);
        var room = roomRepository.findById(roomId).orElseThrow(UserNotFoundException::new);
        var user = currentProfile.getUser();

        booking.setAdults(req.getAdults());
        booking.setChildren(req.getChildren());
        booking.setRoom(room);
        booking.setTotalGuests(req.getAdults() + req.getChildren());
        booking.setGuestEmail(currentProfile.getEmail());
        booking.setGuestFullName(currentProfile.getFirstName() + " " + currentProfile.getLastName());
        bookingRepository.save(booking);

        room.addBooking(booking);
        user.setNumOfBookings(user.getNumOfBookings() + 1);
        roomRepository.save(room);
        userRepository.save(user);

        return bookingMapper.toResponse(booking);
    }*/
    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest req, Long hotelId, Long roomId) {
        var currentProfile = authService.getCurrentProfile();
        if (currentProfile == null) {
            throw new NotFoundException("User not found");
        }
        if (!currentProfile.getRole().equals(ROLE.USER)) {
            throw new RuntimeException("Only users can create booking");
        }

        if (req.getCheckInDate().isAfter(req.getCheckOutDate()) || req.getCheckInDate().equals(req.getCheckOutDate())) {
            throw new RuntimeException("Check-in must be BEFORE check-out date");
        }
        if (req.getCheckInDate().isBefore(LocalDate.now()) || req.getCheckInDate().equals(LocalDate.now())) {
            throw new RuntimeException("Check-in must be AFTER today's date");
        }

        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!room.getHotel().getId().equals(hotel.getId())) {
            throw new RuntimeException("Room does not belong to this hotel");
        }

        var overlaps = bookingRepository.existsByRoomIdAndCheckOutAfterAndCheckInBefore(
                roomId,
                req.getCheckInDate(),
                req.getCheckOutDate());
        if (overlaps) {
            throw new RuntimeException("Room is already booked for this date range");
        }

        var user = currentProfile.getUser();
        var booking = bookingMapper.toEntity(req);

        if (req.getAdults() < 1) {
            throw new RuntimeException("At least one adult must be specified!");
        }
        /*if(req.getChildren() == null) {
            booking.setChildren(0);
        } else if (req.getChildren() < 0) {
            throw new RuntimeException("No negative values allowed");
        } else {
            booking.setChildren(req.getChildren());
        }*/
        int children = Optional.ofNullable(req.getChildren()).orElse(0);
        if (children < 0) {
            throw new RuntimeException("No negative values allowed");
        }
        booking.setChildren(children);

        if (booking.getTotalGuests() > room.getCapacity()) {
            throw new RuntimeException("The room is only for: " + room.getCapacity() + " guests");
        }
        booking.setAdults(req.getAdults());
        booking.setGuestEmail(currentProfile.getEmail());
        booking.setGuestFullName(currentProfile.getFirstName() + " " + currentProfile.getLastName());
        booking.setUser(user);
        booking.setRoom(room);

        bookingRepository.save(booking);

        room.addBooking(booking);
        user.setNumOfBookings(user.getNumOfBookings() + 1);
        userRepository.save(user);

        return bookingMapper.toResponse(booking);
    }

    @Override
    public BookingResponse getBookingByBookingConfirmationCode(String bookingConfirmationCode) {
        var currentProfile =  authService.getCurrentProfile();

        var userBooking = bookingRepository.findByBookingConfirmationCodeAndUser_ProfileId(bookingConfirmationCode, currentProfile.getId());
        if (userBooking == null) {
            throw new RuntimeException("No booking found!");
        }
        return bookingMapper.toResponse(userBooking);

    }
}
