package org.example.booking_appointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "bookings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @Column(name = "guest_full_name")
    private String guestFullName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "adults")
    private Integer adults;

    @Column(name = "children")
    private Integer children;

    @Column(name = "total_guests")
    private Integer totalGuests;

    @Column(name = "booking_confirmation_code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public void calculateTotalNumberOfGuests() {
        this.totalGuests = this.adults + children;
    }

    public void setAdults(int numOfAdults) {
        adults = numOfAdults;
        calculateTotalNumberOfGuests();
    }

    public void setChildren(int numOfChildren) {
        children = numOfChildren;
        calculateTotalNumberOfGuests();
    }
    /*public void setChildren(Integer numOfChildren) {
        children = Objects.requireNonNullElse(numOfChildren, 0);
        calculateTotalNumberOfGuests();
    }*/


}