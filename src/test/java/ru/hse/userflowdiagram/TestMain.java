package ru.hse.userflowdiagram;

import com.codeborne.selenide.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    static String OZON_URL = "https://www.ozon.ru";
    static String FARFETCH_URL = "https://www.farfetch.com/ru/";
    static String LAMODA_URL = "https://www.lamoda.ru";
    static String YUOTUBE_URL = "https://www.youtube.com";
    static String VARLAMOV_URL = "https://varlamov.ru";
    static String WYLSA_URL = "https://wylsa.com";

    static List<String> URLS_FILES = new ArrayList<>(Arrays.asList(
            OZON_URL,
            FARFETCH_URL,
            LAMODA_URL,
            YUOTUBE_URL,
            VARLAMOV_URL,
            WYLSA_URL
    ));

    public static void main(String[] args) {
        Configuration.headless = true;
        for (var site : Arrays.asList(LAMODA_URL)) {
            var userFlowDiagram = new UserFlowDiagram();
            userFlowDiagram.build(site, 1, null, null);
            userFlowDiagram.view(null, null);
        }
    }
}
