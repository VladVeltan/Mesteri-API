package meseriasiapi.controller;

import lombok.RequiredArgsConstructor;
import meseriasiapi.service.FileDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medias")
@RequiredArgsConstructor
public class FileDataController {
    private final FileDataService fileDataService;
    @PostMapping("/{whichEntity}/{id}")
    public ResponseEntity<?> uploadImagesToFileSystem(@RequestParam("images") MultipartFile[] files, @PathVariable UUID id, @PathVariable String whichEntity) throws IOException {
        List<String> uploadedFiles = fileDataService.uploadImagesToFileSystem(files, id, whichEntity);
        return ResponseEntity.status(HttpStatus.OK).body(uploadedFiles);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=fileDataService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(imageData);

    }
    @GetMapping("/{whichEntity}/{id}")
    public ResponseEntity<?> findAllFileDataByTypeAndId(@PathVariable UUID id,@PathVariable String whichEntity) throws IOException {
        List<byte[]> filteredFileData = fileDataService.findAllFileDataByTypeAndId(id, whichEntity);
        return ResponseEntity.ok(filteredFileData);

    }
    @PutMapping("/{whichEntity}/{id}")
    public ResponseEntity<?> updateImage(@RequestParam("image") MultipartFile file, @PathVariable UUID id, @PathVariable String whichEntity) throws IOException {
        String updatedFile = fileDataService.updateImage(file, id, whichEntity);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFile);
    }




}
