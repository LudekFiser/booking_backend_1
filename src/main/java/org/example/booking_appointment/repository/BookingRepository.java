package org.example.booking_appointment.repository;

import org.example.booking_appointment.entity.Booking;
import org.example.booking_appointment.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    boolean existsByRoomIdAndCheckOutAfterAndCheckInBefore(Long roomId, LocalDate checkIn, LocalDate checkOut);

    Booking findByBookingConfirmationCodeAndUser_ProfileId(String bookingConfirmationCode, Long userId);
    //Booking findByBookingConfirmationCodeAndUserId(String bookingConfirmationCode, Long userId);

}
