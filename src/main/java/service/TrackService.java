package service;

import entities.Track;
import exceptions.classes.TrackException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import service.processing.Mp3Processing;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TrackService {
    private static final Logger logger = Logger.getLogger(TrackService.class);

    public void storeFile(MultipartFile file) {
        try {
            FileUtils.writeByteArrayToFile(new File("uploaded_file"), file.getBytes());
        } catch (IOException e) {
            logger.error("IO exception during file storage: " + e.getMessage(), e);
            throw new TrackException("IO exception during file storage");
        }
    }

    public Track processAndStoreExtractedInformation(File track) {
        Track processResult = Mp3Processing.processTrack(track);
        return processResult;
    }

    public List<Track> findSimilarTracks(Track track) {
        return null;
    }

}
