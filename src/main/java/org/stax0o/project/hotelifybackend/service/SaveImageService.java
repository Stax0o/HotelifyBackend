package org.stax0o.project.hotelifybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.stax0o.project.hotelifybackend.entity.Hotel;
import org.stax0o.project.hotelifybackend.entity.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveImageService {
    @Value("${upload.directory}")
    private Path uploadDirectory;

    public List<Image> saveImage(List<MultipartFile> images, Hotel hotel) throws IOException {
        // Создаем папку для загрузки изображений, если ее нет
        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }

        // Сохраняем изображения и добавляем их в список
        List<Image> imageList = new ArrayList<>();
        for (MultipartFile image : images) {
            String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path imagePath = uploadDirectory.resolve(imageName);
            Files.copy(image.getInputStream(), imagePath); // Копируем файл в указанную директорию
            Image savedImage = new Image();
            savedImage.setHotel(hotel);
            savedImage.setFilePath(imagePath.toString());
            imageList.add(savedImage); // Добавляем путь к файлу в список
        }

        return imageList;
    }
}
