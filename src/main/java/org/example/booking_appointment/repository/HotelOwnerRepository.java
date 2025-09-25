package org.example.booking_appointment.repository;

import org.example.booking_appointment.entity.HotelOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long> {
}
