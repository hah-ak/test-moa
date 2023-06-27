package com.example.exam.services.jsonCreator;

import com.example.exam.services.file.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface JsonCreatorService extends FileService {

    String serializationObject(Object jsonObject);
    Object deserializationJson(String jsonStr);
    void uploadFile(String content, String fileName) throws IOException;
}
