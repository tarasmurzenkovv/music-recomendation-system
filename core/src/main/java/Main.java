import mp3.dtos.Mp3FileInformationDto;
import mp3.processing.Mp3Processing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path[] pathsToFiles = new Path[]{
                Paths.get("/Users/terance1/Desktop/play_me.mp3"),
                Paths.get("/Users/terance1/Desktop/play_me_copy.mp3")};
        List<Mp3FileInformationDto> retrievedInformationFromTracks = Mp3Processing.processInBatch(pathsToFiles);

        for (Mp3FileInformationDto dto : retrievedInformationFromTracks) {
            System.out.println(dto.toString());
        }
    }
}
