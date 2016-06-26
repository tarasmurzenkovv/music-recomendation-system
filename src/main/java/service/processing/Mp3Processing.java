package service.processing;

import entities.Track;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Mp3Processing {
    private final static Logger logger = Logger.getLogger(Mp3Processing.class);
    private final static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static Track processTrack(Path pathToFile) {
        Track mp3FileInformationDto = null;

        Future<Track> expectedResult = threadPool.submit(new ProcessingTask(pathToFile.toString()));
        try {
            mp3FileInformationDto = expectedResult.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception occurred during service.processing of file. Exception: ", e);
            threadPool.shutdown();
        }
        return mp3FileInformationDto;
    }

    public static Track processTrack(File file) {
        Track mp3FileInformationDto = null;

        Future<Track> expectedResult = threadPool.submit(new ProcessingTask(file.getAbsolutePath()));
        try {
            mp3FileInformationDto = expectedResult.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception occurred during service.processing of file. Exception: ", e);
            threadPool.shutdown();
        }
        return mp3FileInformationDto;
    }

    public static Track processTrack(byte[] bytes, String pathToStoreTrack) {
        Track mp3FileInformationDto = null;
        try {
            File file = new File(pathToStoreTrack);
            FileUtils.writeByteArrayToFile(file, bytes);
            Future<Track> expectedResult = threadPool.submit(new ProcessingTask(file.getAbsolutePath()));
            mp3FileInformationDto = expectedResult.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception occurred during service.processing of file. Exception: ", e);
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("Nul will be returned. Unable to save or write bytes to given file: " + pathToStoreTrack, e);
        }
        return mp3FileInformationDto;
    }

    public static List<Track> processInBatch(Path[] pathsToFiles) {

        List<Track> dtos = new ArrayList<>();
        List<Future<Track>> expectedResults = new ArrayList<>();
        try {
            for (Path pathToFile : pathsToFiles) {
                Future<Track> expectedResult = threadPool.submit(new ProcessingTask(pathToFile.toString()));
                expectedResults.add(expectedResult);
            }
            for (Future<Track> future : expectedResults) {
                dtos.add(future.get());
            }
            return dtos;
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception occurred during batch service.processing of files. Exception: ", e);
            threadPool.shutdown();
            return null;
        }
    }

    public static List<Track> processInBatch(File[] pathsToFiles) {

        List<Track> dtos = new ArrayList<>();
        List<Future<Track>> expectedResults = new ArrayList<>();
        try {
            for (File file : pathsToFiles) {
                Future<Track> expectedResult = threadPool.submit(new ProcessingTask(file.getAbsolutePath()));
                expectedResults.add(expectedResult);
            }
            for (Future<Track> future : expectedResults) {
                dtos.add(future.get());
            }
            return dtos;
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception occurred during batch service.processing of files. Exception: ", e);
            threadPool.shutdown();
            return null;
        }
    }
}
