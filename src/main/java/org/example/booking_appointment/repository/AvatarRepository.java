package org.example.booking_appointment.repository;

import org.example.booking_appointment.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
