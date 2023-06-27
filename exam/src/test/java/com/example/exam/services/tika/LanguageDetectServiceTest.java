package com.example.exam.services.tika;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageDetectServiceTest {

    LanguageDetectService service = new LanguageDetectService();
    @Test
    void identifyLanguage() {
        String s = service.identifyLanguage("호고노고");
        Assertions.assertThat(s).isEqualTo("ko");
        Assertions.assertThat(service.identifyLanguage("ਕੋਈ ਟਿੱਪਣੀ ਨਹੀਂ")).isEqualTo("pa");
//        Assertions.assertThat(service.identifyLanguage("kein Kommentar")).isEqualTo("de");
        Assertions.assertThat(service.identifyLanguage("不予置评")).isEqualTo("zh");
    }
}