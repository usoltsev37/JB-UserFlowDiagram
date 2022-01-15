package ru.hse.userflowdiagram.model.attribute;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.nidi.graphviz.model.MutableNode;
import ru.hse.userflowdiagram.utils.Constants;
import ru.hse.userflowdiagram.model.forest.ForestInfo;
import ru.hse.userflowdiagram.model.forest.NodeURL;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.attribute;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class Link implements Attribute{
    @Override
    public ForestInfo get() {
        ElementsCollection links = Selenide.$$(Constants.link).filterBy(attribute(Constants.href));
        List<MutableNode> roots = new ArrayList<>(links.size());
        List<NodeURL> leavesURLs = new ArrayList<>();
        for (SelenideElement linkElem : links) {
            StringBuilder nodeValueBuilder = new StringBuilder();
            String url = linkElem.attr(Constants.href);
            nodeValueBuilder.append("URL: ").append(url);
            MutableNode node = mutNode(nodeValueBuilder.toString());
            roots.add(node);
            leavesURLs.add(new NodeURL(node, url));
        }
        return new ForestInfo(roots,leavesURLs);
    }
}
