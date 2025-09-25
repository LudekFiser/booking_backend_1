package org.example.booking_appointment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "hotel_owners")
@RequiredArgsConstructor
public class HotelOwner {
    @Id
    private Long profileId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "num_of_hotels")
    private Integer numOfHotels = 0;

    @OneToMany(mappedBy = "hotelOwner")
    private List<Hotel> hotels = new ArrayList<>();



    public List<Hotel> getHotels() {
        if (hotels == null) {
            hotels = new ArrayList<>();
        }
        return hotels;
    }
    public int calculateNumberOfHotels() {
        return getHotels().size();
    }

    public void insertHotel(Hotel hotel) {

        this.hotels.add(hotel);
        hotel.setHotelOwner(this);
        this.numOfHotels = this.hotels.size();
    }

    /*public int calculateTotalPlays() {
        // pokud máš v Song 'plays' jako int (doporučeno), není potřeba null guard
        return getSongs()
                .stream()
                .mapToInt(Song::getPlays)
                .sum();
    }*/
}