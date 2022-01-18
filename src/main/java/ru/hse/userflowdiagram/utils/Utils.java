package ru.hse.userflowdiagram.utils;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import ru.hse.userflowdiagram.model.graph.WebVertex;

public class Utils {
    public static boolean isAttributeExists(String s) {
        return (s != null && !s.isEmpty());
    }

    public static WebVertex getTitle() {
        ElementsCollection titles = Selenide.$$("title");
        return new WebVertex(titles.first().getOwnText());
    }
}
