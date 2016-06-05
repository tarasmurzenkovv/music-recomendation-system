package core.mp3.processing;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import core.mp3.dtos.Mp3FileInformationDto;
import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static core.mp3.utils.Mp3Utils.computeFrequencies;
import static core.mp3.utils.Mp3Utils.performFftOnTheGivenMp3File;

public class ProcessingTask implements Callable<Mp3FileInformationDto> {
    private final static Logger logger = Logger.getLogger(ProcessingTask.class);
    private Path pathToFile;

    ProcessingTask(String pathToFile) {
        this.pathToFile = Paths.get(pathToFile);
    }

    @Override
    public Mp3FileInformationDto call() throws Exception {
        try {
            logger.info(String.format("Started computing FFT on the path: %s", pathToFile));
            Mp3FileInformationDto informationDto = new Mp3FileInformationDto();
            Complex[] fft = performFftOnTheGivenMp3File(this.pathToFile.toString());
            informationDto.setFrequencies(computeFrequencies(fft, 100));
            informationDto.setTrackName(this.pathToFile.getFileName().toString());
            logger.info(String.format("Finished with computing frequencies on the path: %s", pathToFile));
            return informationDto;

        } catch (FileNotFoundException | BitstreamException | DecoderException e) {
            String errorMessage = String.format("Unable to process the given file %s . Null will be returned", pathToFile);
            logger.info(errorMessage);
            logger.error(e.toString(), e);
            return null;
        }
    }
}
