package org.example.booking_appointment.dto.room;

import lombok.Builder;
import lombok.Data;
import org.example.booking_appointment.dto.image.ImageDto;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class RoomSummaryDto {

    private Long id;
    private String name;
    private Long hotelId;
    private String roomType;
    private BigDecimal roomPrice;
    private Integer roomNumber;
    private Long capacity;
    private String hotelName;
    private String description;
    private List<ImageDto> roomImage;

    private boolean isBooked;
}
