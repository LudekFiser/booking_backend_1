package org.example.booking_appointment.repository;

import jakarta.annotation.Nullable;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    @EntityGraph(attributePaths = {"artist.profile", "songImage"})
    @Query("SELECT s FROM Room s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Room> findByTitleWithArtistAndImage(@Param("title") String title);

    List<Room> findByHotelId(@Param("id") Long hotelId);


    //List<Room> findAllAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String country, String city);

    //List<Room> findAllAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate);
    @Query("SELECT r.roomNumber FROM Room r WHERE r.hotel.id = :hotelId ORDER BY r.roomNumber ASC")
    List<Integer> findAllRoomNumbersByHotelId(@Param("hotelId") Long hotelId);


    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId")
    List<Room> getRoomsByHotelId(@Param("hotelId") Long hotelId);



    /*@Query("""
    SELECT r
    FROM Room r
    WHERE r.hotel.location.city = :city
      AND r.id NOT IN (
          SELECT b.room.id
          FROM Booking b
          WHERE b.checkIn < :checkOutDate
            AND b.checkOut > :checkInDate
      )
    """)
    List<Room> findAvailableRoomsByCityAndDates(@Param("city") String city,
                                                @Param("checkInDate") LocalDate checkInDate,
                                                @Param("checkOutDate") LocalDate checkOutDate);*/

    @Query("""
    SELECT r
    FROM Room r
    WHERE r.hotel.location.city = :city
      AND r.id NOT IN (
          SELECT b.room.id
          FROM Booking b
          WHERE b.checkIn < :checkOutDate
            AND b.checkOut > :checkInDate
      )
    """)
    List<Room> findAvailableRoomsByCityAndDates(@Param("city") String city,
                                                @Param("checkInDate") LocalDate checkInDate,
                                                @Param("checkOutDate") LocalDate checkOutDate);
}
