package service;

import entities.Track;
import service.processing.Mp3Processing;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class TrackService {

    public Track processAndStoreExtractedInformation(File track) {
        Track processResult = Mp3Processing.processTrack(track);
        return processResult;
    }

    public List<Track> findSimilarTracks(Track track){
        return null;
    }

}
