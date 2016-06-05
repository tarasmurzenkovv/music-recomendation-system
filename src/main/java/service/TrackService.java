package service;

import core.mp3.dtos.Mp3FileInformationDto;
import core.mp3.processing.Mp3Processing;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class TrackService {

    public Mp3FileInformationDto processAndStoreExtractedInformation(File track) {
        Mp3FileInformationDto processResult = Mp3Processing.processTrack(track);
        return processResult;
    }

    public List<Mp3FileInformationDto> findSimilarTracks(Mp3FileInformationDto track){
        return null;
    }

}
