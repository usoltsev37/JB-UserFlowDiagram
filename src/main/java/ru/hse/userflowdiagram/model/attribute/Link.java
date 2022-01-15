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

public class Link implements Attribute{
    @Override
    public Forest get() {
        ElementsCollection links = Selenide.$$(Constants.link).filterBy(attribute(Constants.href));
        List<MutableNode> roots = new ArrayList<>(links.size());
        List<NodeLink> leavesURLs = new ArrayList<>();
        for (SelenideElement linkElem : links) {
            StringBuilder nodeValueBuilder = new StringBuilder();
            String url = linkElem.attr(Constants.href);
            nodeValueBuilder.append("URL: ").append(url);
            MutableNode node = mutNode(nodeValueBuilder.toString());
            roots.add(node);
            leavesURLs.add(new NodeLink(node, url));
        }
        return new Forest(roots,leavesURLs);
    }
}
