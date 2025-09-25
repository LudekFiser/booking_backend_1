package org.example.booking_appointment.repository;

import org.example.booking_appointment.entity.LikedHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedHotelRepository extends JpaRepository<LikedHotel, Long> {


    @Query("SELECT ls FROM LikedHotel ls WHERE ls.user.profileId = :profileId")
    List<LikedHotel> findByUserProfileId(@Param("profileId") Long profileId);

    boolean existsById(LikedHotel id);

    void deleteById(LikedHotel id);
}
