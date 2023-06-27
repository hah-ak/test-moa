package my.application.api.services.tika;

import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.springframework.stereotype.Service;

@Service
public class LanguageDetectService {

    public String identifyLanguage(String text) {
        LanguageDetector detector = new OptimaizeLangDetector().loadModels();
        LanguageResult result = detector.detect(text);
        return result.getLanguage();
    }
}
