package utils;

import entities.Track;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class JsonUtils {
    private final static Logger logger = Logger.getLogger(JsonUtils.class);
    public static String toJson(Track dto) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(dto);
        } catch (IOException e) {
            logger.error(String.format("Unable to convert dto %s to json", dto.toString()), e);
        }
        return json;
    }

    public static Track fromJson(String json) {
        Track dto;
        ObjectMapper mapper = new ObjectMapper();
        try {
            dto = mapper.readValue(json, Track.class);
        } catch (IOException e) {
            logger.error(String.format("Unable to convert given %s to Mp3FileInformationDto class. Null will be returned", json), e);
            return null;
        }
        return dto;
    }
}
