package ru.hse.userflowdiagram.utils;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import guru.nidi.graphviz.model.MutableNode;

import static guru.nidi.graphviz.model.Factory.mutNode;

public class Utils {
    public static boolean isAttributeExists(String s) {
        return (s != null && !s.isEmpty());
    }

    public static MutableNode getTitle() {
        ElementsCollection titles = Selenide.$$("title");
        return mutNode(titles.first().getOwnText());
    }
}
