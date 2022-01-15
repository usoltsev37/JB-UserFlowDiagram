package ru.hse.userflowdiagram.model;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.nidi.graphviz.model.MutableNode;
import ru.hse.userflowdiagram.Constants;
import ru.hse.userflowdiagram.Forest;
import ru.hse.userflowdiagram.NodeLink;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.attribute;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Nav implements Attribute {
    @Override
    public Forest get() {
        ElementsCollection navBlocks = Selenide.$$(Constants.nav);
        List<MutableNode> roots = new ArrayList<>(navBlocks.size());
        List<NodeLink> leavesURLs = new ArrayList<>();
        for (int i = 0; i < navBlocks.size(); i++) {
            SelenideElement nav = navBlocks.get(i);
            MutableNode navNode = mutNode("Menu Navigation " + (i + 1));
            ElementsCollection links = nav.$$(Constants.link).filterBy(attribute(Constants.href));
            for (SelenideElement linkElem : links) {
                MutableNode linkNode = mutNode(linkElem.getText());
                String url = linkElem.attr(Constants.href);
                linkNode.addLink(mutNode("Click on " + url));
                navNode.addLink(linkNode);
                leavesURLs.add(new NodeLink(linkNode, url));
            }
            roots.add(navNode);
        }
        return new Forest(roots, leavesURLs);
    }
}
