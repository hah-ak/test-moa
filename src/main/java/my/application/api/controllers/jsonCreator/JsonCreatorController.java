package com.example.exam.controllers.jsonCreator;

import com.example.exam.services.jsonCreator.JsonCreatorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/json-creator")
@RequiredArgsConstructor
public class JsonCreatorController {

    private final JsonCreatorService jsonCreatorService;

    @Getter
    @Setter
    static public class JsonCreatorInnerClass {
        private String fileName;
        private String jsonStr;
    }
    @PostMapping("/file")
    public void createJson(@RequestBody JsonCreatorInnerClass jsonStr) throws IOException {
        jsonCreatorService.uploadFile(jsonStr.getJsonStr(), jsonStr.getFileName());
    }
    //    Content-Disposition: attachment; filename="filename.jpg"
    @GetMapping("/file")
    public List<File> findJsonFileByName(@RequestParam("fileName") String[] fileNames) {
        return Arrays.stream(fileNames).map(jsonCreatorService::getFile).toList();
    }
}
