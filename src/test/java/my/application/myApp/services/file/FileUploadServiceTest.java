package my.application.myApp.services.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@AutoConfigureMockMvc
@SpringBootTest
class FileUploadServiceTest {

    @Autowired
    PhotoFileServiceImpl photoFileUploadService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void 루트디렉토리() {
        Paths.get(System.getProperty("user.home")).resolve("\\Desktop\\photos\\");

        System.out.println(System.getProperty("user.dir"));
        System.out.println(Paths.get(System.getProperty("user.home")).toAbsolutePath().resolve("Desktop\\photos\\"));
    }

    @Test
    void 전체찾기() throws IOException {

//        final Path FILE_PATH = FileUploadConstants.USER_HOME.resolve("Desktop\\photosTest").resolve(multipartFile.getResource().lastModified() + multipartFile.getName());
//        log.info("path : {}",FILE_PATH.toString());

        Files.list(FileConstants.USER_HOME.resolve("Desktop\\photosTest")).forEach(path -> System.out.println(path.toFile().lastModified()));
//        photoFileUploadService.getAllFiles().forEach(file -> {
//            System.out.println(file.getName());
//        });
    }

    @Test
    void callFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file/abs/all")).andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void uploadTest() throws IOException, InterruptedException {
        //given

        try (Stream<Path> list = Files.list(FileConstants.USER_HOME.resolve("Desktop\\photosTest"));) {
            List<MultipartFile> list1 = list.map(path -> {
                try {
                    return (MultipartFile) new MockMultipartFile(String.valueOf(path.getFileName()), Files.readAllBytes(path));
//                return (MultipartFile) path.toFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).toList();

//            photoFileUploadService.uploadFile(list1.get(0));
            photoFileUploadService.uploadFiles(list1);

            List<Path> list2 = Files.list(FileConstants.USER_HOME.resolve("Desktop\\photos")).toList();
            Assertions.assertThat(list1.size() == list2.size()).isTrue();
        }

    }
}