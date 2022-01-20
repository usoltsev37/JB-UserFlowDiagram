package ru.hse.userflowdiagram.model.attribute;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.hse.userflowdiagram.model.graph.WebVertex;
import ru.hse.userflowdiagram.utils.Constants;
import ru.hse.userflowdiagram.model.forest.ForestInfo;
import ru.hse.userflowdiagram.model.forest.NodeURL;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.attribute;

public class Link implements Attribute{
    @Override
    public ForestInfo get() {
        ElementsCollection links = Selenide.$$(Constants.link).filterBy(attribute(Constants.href));
        List<WebVertex> roots = new ArrayList<>(links.size());
        List<NodeURL> leavesURLs = new ArrayList<>();
        for (SelenideElement linkElem : links) {
            StringBuilder nodeValueBuilder = new StringBuilder();
            String url = linkElem.attr(Constants.href);
            nodeValueBuilder.append("URL: ").append(url);
            WebVertex node = new WebVertex(nodeValueBuilder.toString());
            roots.add(node);
            leavesURLs.add(new NodeURL(node, url));
        }
        return new ForestInfo(roots,leavesURLs);
    }
}
