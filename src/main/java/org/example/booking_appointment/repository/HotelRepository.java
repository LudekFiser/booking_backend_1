package org.example.booking_appointment.repository;

import org.example.booking_appointment.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    /*@Query("SELECT h FROM Hotel h WHERE h.name = :hotelName")
    Hotel findByHotelNameIgnoreCase(@Param("hotelName") String name);*/


}
