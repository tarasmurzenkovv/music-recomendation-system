package service.processing;

import entities.Track;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.apache.commons.math3.complex.Complex;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static utils.FftUtils.performFftOnTheGivenMp3File;
import static utils.FrequencyUtils.computeFrequencies;

class ProcessingTask implements Callable<Track> {
    private final static Logger logger = Logger.getLogger(ProcessingTask.class);
    private Path pathToFile;

    ProcessingTask(String pathToFile) {
        this.pathToFile = Paths.get(pathToFile);
    }

    @Override
    public Track call() throws Exception {
        try {
            logger.info(String.format("Started computing FFT on the path: %s", pathToFile));
            Track informationDto = new Track();
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
