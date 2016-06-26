package controllers;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class TrackController {
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "file") MultipartFile file) {
        try {
            FileUtils.writeByteArrayToFile(new File("uploaded_file"), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
