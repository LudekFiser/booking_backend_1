package org.example.booking_appointment.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_appointment.dto.room.RoomSummaryDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchAvailableRoomsResponse {

    private List<RoomSummaryDto> rooms;
}
