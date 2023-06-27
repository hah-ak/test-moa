package com.example.exam.services.jsonCreator;

import com.example.exam.services.file.FileConstants;
import com.example.exam.services.file.JsonFileServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.apache.tika.metadata.FileSystem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonCreatorServiceImpl extends JsonFileServiceImpl implements JsonCreatorService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String serializationObject(Object jsonObject) {
        return null;
    }

    @Override
    public Object deserializationJson(String jsonStr) {
        return null;
    }

    @Override
    public void uploadFile(String content, String fileName) throws IOException {
        Path json = Files.createTempFile("json" + ZonedDateTime.now(ZoneId.of("UTC")), ".json");
        objectMapper.writeValue(json.toFile(),content);

        Files.copy(json, FileConstants.USER_HOME.resolve(this.JSON_DIRECTORY).resolve(fileName));
    }
}
