package org.example.booking_appointment.utils.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booking_appointment.dto.image.UploadedImageDto;
import org.example.booking_appointment.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public UploadedImageDto uploadImage(MultipartFile file, String folderName) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folderName,       // složka dynamicky
                            "resource_type", "image"
                    )
            );

            String url = uploadResult.get("secure_url").toString();
            String publicId = uploadResult.get("public_id").toString();

            return new UploadedImageDto(url, publicId);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }



    /*public List<UploadedImageDto> uploadImageList(List<MultipartFile> file, String folderName) {
        try {

            for(MultipartFile fileItem : file) {

                @SuppressWarnings("unchecked")
                Map<String, Object> uploadResult = cloudinary.uploader().upload(
                        fileItem.getBytes(),
                        ObjectUtils.asMap(
                                "folder", folderName,       // složka dynamicky
                                "resource_type", "image"
                        )
                );
                String url = uploadResult.get("secure_url").toString();
                String publicId = uploadResult.get("public_id").toString();
                return Collections.singletonList(new UploadedImageDto(url, publicId));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }*/


    public Avatar buildProfileImage(UploadedImageDto dto, Profile profile) {

        return Avatar.builder()
                .imageUrl(dto.getUrl())
                .publicId(dto.getPublicId())
                .createdAt(LocalDateTime.now())
                .profile(profile)
                .build();
    }

    public RoomImage buildRoomImage(UploadedImageDto dto, Room room) {

        return RoomImage.builder()
                .imageUrl(dto.getUrl())
                .publicId(dto.getPublicId())
                .createdAt(LocalDateTime.now())
                .room(room)
                .build();
    }

    public HotelImage buildHotelImage(UploadedImageDto dto, Hotel hotel) {
        return HotelImage.builder()
                .imageUrl(dto.getUrl())
                .publicId(dto.getPublicId())
                .createdAt(LocalDateTime.now())
                .hotel(hotel)
                .build();
    }

    public void deleteImageByPublicId(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image with public_id: " + publicId, e);
        }
    }
}
