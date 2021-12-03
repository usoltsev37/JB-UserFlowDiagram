package ru.hse.userflowdiagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    static String OZON_URL = "https://www.ozon.ru";
    static String OZON_FILE = "src/test/java/ru/hse/userflowdiagram/output/ozon";

    static String FARFETCH_URL = "https://www.farfetch.com/ru/";
    static String FARFETCH_FILE = "src/test/java/ru/hse/userflowdiagram/output/farfetch";

    static String LAMODA_URL = "https://www.lamoda.ru";
    static String LAMODA_FILE = "src/test/java/ru/hse/userflowdiagram/output/lamoda";

    static String YUOTUBE_URL = "https://www.youtube.com";
    static String YUOTUBE_FILE = "src/test/java/ru/hse/userflowdiagram/output/youtube";

    static String VARLAMOV_URL = "https://varlamov.ru";
    static String VARLAMOV_FILE = "src/test/java/ru/hse/userflowdiagram/output/varlamov";

    static String WYLSA_URL = "https://wylsa.com";
    static String WYLSA_FILE = "src/test/java/ru/hse/userflowdiagram/output/wylsa";

    static List<SiteData> URLS_FILES = new ArrayList<>(Arrays.asList(
            new SiteData(OZON_URL, OZON_FILE),
            new SiteData(FARFETCH_URL, FARFETCH_FILE),
            new SiteData(LAMODA_URL, LAMODA_FILE),
            new SiteData(YUOTUBE_URL, YUOTUBE_FILE),
            new SiteData(VARLAMOV_URL, VARLAMOV_FILE),
            new SiteData(WYLSA_URL, WYLSA_FILE)
    ));

    public static void main(String[] args) throws IOException {
        for (var site : Arrays.asList(new SiteData(OZON_URL, OZON_FILE))) {
            var builder = new SeleniumDiagramBuilder(site.url, site.file);
            builder.build();
        }
    }
}
