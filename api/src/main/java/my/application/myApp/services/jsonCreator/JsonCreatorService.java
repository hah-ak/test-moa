package my.application.myApp.services.jsonCreator;

import my.application.myApp.services.file.FileService;

import java.io.IOException;

public interface JsonCreatorService extends FileService {

    String serializationObject(Object jsonObject);
    Object deserializationJson(String jsonStr);
    void uploadFile(String content, String fileName) throws IOException;
}
