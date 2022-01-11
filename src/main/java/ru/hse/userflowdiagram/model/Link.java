package ru.hse.userflowdiagram.model;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.nidi.graphviz.model.MutableNode;
import ru.hse.userflowdiagram.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.attribute;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Link implements Attribute{
    @Override
    public List<MutableNode> get() {
        ElementsCollection links = Selenide.$$(Constants.link).filterBy(attribute(Constants.href));
        List<MutableNode> result = new ArrayList<>(links.size());
        for (SelenideElement linkElem : links) {
            StringBuilder nodeValueBuilder = new StringBuilder();
            nodeValueBuilder.append("URL: ").append(linkElem.attr(Constants.href));
            MutableNode node = mutNode(nodeValueBuilder.toString());
            result.add(node);
        }
        return result;
    }
}
