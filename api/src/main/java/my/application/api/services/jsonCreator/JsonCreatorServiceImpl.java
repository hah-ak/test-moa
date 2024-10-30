package my.application.api.services.jsonCreator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.application.api.services.file.FileUtils;
import my.application.api.services.file.JsonFileServiceImpl;
import org.springframework.stereotype.Service;
import web.core.MyAppCommonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonCreatorServiceImpl extends JsonFileServiceImpl implements JsonCreatorService {

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
        MyAppCommonUtil.defaultObjectMapper.writeValue(json.toFile(),content);

        Files.copy(json, FileUtils.USER_HOME.resolve(FileUtils.JSON_DIRECTORY).resolve(fileName));
    }
}
