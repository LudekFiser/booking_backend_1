package org.example.booking_appointment.dto.room;

import lombok.Builder;
import lombok.Data;
import org.example.booking_appointment.entity.Hotel;
import org.example.booking_appointment.entity.RoomImage;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateRoomRequest {
    private String name;
    private String description;
    private String roomType;
    private BigDecimal price;
    private Long capacity;
}
