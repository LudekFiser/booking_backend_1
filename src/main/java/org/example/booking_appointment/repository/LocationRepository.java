package org.example.booking_appointment.repository;

import org.example.booking_appointment.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


    boolean existsByCountryAndCityAndStreetAndStreetNumberAndPostalCode(String country, String city,
                                                                        String street, String streetNumber,
                                                                        String postalCode);
}
