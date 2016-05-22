package mp3.processing;

import com.sun.xml.internal.ws.client.AsyncResponseImpl;
import mp3.dtos.Mp3FileInformationDto;
import org.apache.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Mp3Processing {
    private final static Logger logger = Logger.getLogger(Mp3Processing.class);
    private final static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static List<Mp3FileInformationDto> processInBatch(Path[] pathsToFiles) {

        List<Mp3FileInformationDto> dtos = new ArrayList<>();
        List<Future<Mp3FileInformationDto>> expectedResults = new ArrayList<>();
        try {
            for (Path pathToFile : pathsToFiles) {
                Future<Mp3FileInformationDto> expectedResult = threadPool.submit(new ProcessingTask(pathToFile.toString()));
                expectedResults.add(expectedResult);
            }
            for (Future<Mp3FileInformationDto> future : expectedResults) {
                dtos.add(future.get());
            }
            return dtos;
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception occurred during batch processing of files. Exception: ", e);
            threadPool.shutdown();
            return null;
        }
    }
}
