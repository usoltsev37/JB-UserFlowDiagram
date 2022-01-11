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

public class Nav implements Attribute {
    @Override
    public List<MutableNode> get() {
        ElementsCollection navBlocks = Selenide.$$(Constants.nav);
        List<MutableNode> result = new ArrayList<>(navBlocks.size());
        for (int i = 0; i < navBlocks.size(); i++) {
            SelenideElement nav = navBlocks.get(i);
            MutableNode navNode = mutNode("Menu Navigation " + (i + 1));
            ElementsCollection links = nav.$$(Constants.link).filterBy(attribute(Constants.href));
            for (SelenideElement linkElem : links) {
                MutableNode linkNode = mutNode(linkElem.getText());
                linkNode.addLink(mutNode("Click on " + linkElem.attr(Constants.href)));
                navNode.addLink(linkNode);
            }
            result.add(navNode);
        }
        return result;
    }
}
