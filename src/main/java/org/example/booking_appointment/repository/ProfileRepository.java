package org.example.booking_appointment.repository;


import org.example.booking_appointment.entity.Profile;
import org.example.booking_appointment.enums.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByEmail(String email);
    Optional<Profile> findByEmail(String email);

    @Query("SELECT p FROM Profile p WHERE (:role IS NULL OR p.role = :role)")
    List<Profile> findByRoleOnly(@Param("role") ROLE role);
}
