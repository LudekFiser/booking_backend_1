package org.example.booking_appointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.booking_appointment.dto.image.UploadedImageDto;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hotels")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    /*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;*/
    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Location location;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_owner_id")
    private HotelOwner hotelOwner;

    @Column(name = "num_of_rooms")
    private Integer numOfRooms = 0;

    @Column(name = "likes")
    private Integer likes = 0;

    @Column(name = "ratings_count")
    private Integer ratingsCount = 0;

    @Column(name = "rating")
    private Double rating = 0D;

    @OneToMany(mappedBy = "hotel")
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HotelImage> hotelImages = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikedHotel> likedHotels = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Rating> ratings = new ArrayList<>();


    public void addImage(HotelImage hotelImage) {
        this.hotelImages.add(hotelImage);
        hotelImage.setHotel(this);
        this.setHotelImages(hotelImages);
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        room.setHotel(this);
        this.setRooms(rooms);
    }


    public int getNumberOfRooms() {
        if (rooms == null) return 0;
        return rooms.size();
    }

    public int getRatingsCount() {
        if (ratingsCount == null) return 0;
        return ratingsCount;
    }

    public Double getRating() {
        if (rating == null) return 0D;
        return rating;
    }

    public Double calculateRating() {
        if (ratings == null || ratings.isEmpty()) return 0D;
        return ratings.stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(0D);
    }

    public int getNextRoomNumber() {
        if (rooms == null || rooms.isEmpty()) {
            return 1;
        }
        return rooms.stream()
                .mapToInt(Room::getRoomNumber)
                .max()
                .orElse(0) + 1;
    }
}
