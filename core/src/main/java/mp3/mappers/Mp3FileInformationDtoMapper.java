package mp3.mappers;

import mp3.dtos.Mp3FileInformationDto;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class Mp3FileInformationDtoMapper {
    private final static Logger logger = Logger.getLogger(Mp3FileInformationDtoMapper.class);
    public static String toJson(Mp3FileInformationDto dto) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(dto);
        } catch (IOException e) {
            logger.error(String.format("Unable to convert dto %s to json", dto.toString()), e);
        }
        return json;
    }

    public static Mp3FileInformationDto fromJson(String json) {
        Mp3FileInformationDto dto;
        ObjectMapper mapper = new ObjectMapper();
        try {
            dto = mapper.readValue(json, Mp3FileInformationDto.class);
        } catch (IOException e) {
            logger.error(String.format("Unable to convert given %s to Mp3FileInformationDto class. Null will be returned", json), e);
            return null;
        }
        return dto;
    }
}
