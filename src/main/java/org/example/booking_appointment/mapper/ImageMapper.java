package org.example.booking_appointment.mapper;


import org.example.booking_appointment.dto.image.ImageDto;
import org.example.booking_appointment.entity.Avatar;
import org.example.booking_appointment.entity.HotelImage;
import org.example.booking_appointment.entity.RoomImage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageMapper {

    public ImageDto toImageDto(Avatar a) {
        return a == null ? null : new ImageDto(a.getImageUrl(), a.getPublicId());
    }

    public ImageDto toRoomImageDto(RoomImage a) {
        return a == null ? null : new ImageDto(a.getImageUrl(), a.getPublicId());
    }

    public ImageDto toHotelImageDto(HotelImage a) {
        return a == null ? null : new ImageDto(a.getImageUrl(), a.getPublicId());
    }

    public List<ImageDto> toRoomImageDtoList(List<RoomImage> images) {
        if (images == null) {
            return List.of(); // prázdný seznam místo null
        }
        return images.stream()
                .map(this::toRoomImageDto)
                .collect(Collectors.toList());
    }

    public List<ImageDto> toHotelImageDtoList(List<HotelImage> images) {
        if (images == null) {
            return List.of(); // prázdný seznam místo null
        }
        return images.stream()
                .map(this::toHotelImageDto)
                .collect(Collectors.toList());
    }


}


