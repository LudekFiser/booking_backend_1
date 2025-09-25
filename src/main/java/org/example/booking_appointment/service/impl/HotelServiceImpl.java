package org.example.booking_appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.hotel.CreateHotelRequest;
import org.example.booking_appointment.dto.hotel.HotelResponse;
import org.example.booking_appointment.dto.image.UploadedImageDto;
import org.example.booking_appointment.dto.room.CreateRoomRequest;
import org.example.booking_appointment.dto.room.RoomSummaryDto;
import org.example.booking_appointment.enums.ROLE;
import org.example.booking_appointment.exception.UserNotFoundException;
import org.example.booking_appointment.mapper.HotelMapper;
import org.example.booking_appointment.mapper.LocationMapper;
import org.example.booking_appointment.mapper.RoomMapper;
import org.example.booking_appointment.repository.HotelOwnerRepository;
import org.example.booking_appointment.repository.HotelRepository;
import org.example.booking_appointment.repository.LocationRepository;
import org.example.booking_appointment.repository.RoomRepository;
import org.example.booking_appointment.service.AuthService;
import org.example.booking_appointment.service.HotelService;
import org.example.booking_appointment.utils.cloudinary.CloudinaryService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {


    private final AuthService authService;
    private final HotelMapper hotelMapper;
    private final LocationMapper locationMapper;
    private final HotelRepository hotelRepository;
    private final LocationRepository locationRepository;
    private final HotelOwnerRepository hotelOwnerRepository;
    private final CloudinaryService cloudinaryService;
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    // DONE
    @Override
    public HotelResponse addHotel(CreateHotelRequest req, List<MultipartFile> images) {
        var currentProfile = authService.getCurrentProfile();
        if(currentProfile == null) {
            throw new UserNotFoundException();
        }
        if (!currentProfile.getRole().equals(ROLE.HOTEL_OWNER)) {
            throw new AccessDeniedException("You are not allowed to perform this action");
        }

        if (locationRepository.existsByCountryAndCityAndStreetAndStreetNumberAndPostalCode(
                req.getAddLocation().getCountry(), req.getAddLocation().getCity(),
                req.getAddLocation().getStreet(), req.getAddLocation().getStreetNumber(),
                req.getAddLocation().getPostalCode())) {
            throw new RuntimeException("There is already a hotel at this exact location!");
        }

        var hotel = hotelMapper.toEntity(req);
        var location = locationMapper.toEntity(req.getAddLocation());

        if (images.size() > 10) {
            throw new AccessDeniedException("You cant upload more than 10 images");
        }

        //HotelImage hotelImage = null;
        for (MultipartFile image : images) {
            if (image == null || image.isEmpty()) {
                throw new RuntimeException("The image is empty");
            }
            UploadedImageDto uploadedImage = cloudinaryService.uploadImage(image, "hotel_images");
            var hotelImage = cloudinaryService.buildHotelImage(uploadedImage, hotel);
            hotel.addImage(hotelImage);
        }

        hotel.setLocation(location);
        location.setHotel(hotel);

        hotel.setHotelOwner(currentProfile.getHotelOwner());
        hotel.setLikes(0);
        hotel.setNumOfRooms(hotel.getNumberOfRooms());
        hotel.setRatingsCount(0);
        hotel.setRating(hotel.getRating());
        hotelRepository.save(hotel);


        var hotelOwner = currentProfile.getHotelOwner();
        hotelOwner.setNumOfHotels(hotelOwner.getNumOfHotels() + 1);
        hotelOwner.insertHotel(hotel);
        hotelOwnerRepository.save(hotelOwner);

        return hotelMapper.toResponse(hotel);
    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {
        var hotel =  hotelRepository.findById(hotelId).orElseThrow();
        return hotelMapper.toResponse(hotel);
    }

    @Override
    public List<HotelResponse> getAllAvailableHotels() {
        return List.of();
    }
}
