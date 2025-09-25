package org.example.booking_appointment.dto.auth;

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
public class SearchResultResponse {
    private List<SearchProfileResponse> profiles;
    private List<RoomSummaryDto> rooms;
}

