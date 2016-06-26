package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.TrackService;

@RestController
public class TrackController {
    @Autowired
    private TrackService trackService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "file") MultipartFile file) {
        trackService.storeFile(file);
    }
}
