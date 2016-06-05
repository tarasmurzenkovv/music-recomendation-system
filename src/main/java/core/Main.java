package core;

import core.mp3.dtos.Mp3FileInformationDto;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static core.mp3.mappers.Mp3FileInformationDtoMapper.toJson;
import static core.mp3.processing.Mp3Processing.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Path> filesInDirectory = new ArrayList<>();
        Path directory = Paths.get("/Users/terance1/Desktop/mp3s_to_collect_information");
        File collectedInformation = new File("/Users/terance1/Desktop/collected_info.txt");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                System.out.println(entry.toString());
                filesInDirectory.add(entry);
            }
        }

        Path[] filesToProcess = new Path[filesInDirectory.size()];
        filesInDirectory.toArray(filesToProcess);

        PrintWriter printWriter = new PrintWriter(collectedInformation);

        for (Mp3FileInformationDto dto : processInBatch(filesToProcess)) {
            printWriter.println(toJson(dto));
        }
    }
}
