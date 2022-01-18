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

public class Nav implements Attribute {
    @Override
    public ForestInfo get() {
        ElementsCollection navBlocks = Selenide.$$(Constants.nav);
        List<WebVertex> roots = new ArrayList<>(navBlocks.size());
        List<NodeURL> leavesURLs = new ArrayList<>();
        for (int i = 0; i < navBlocks.size(); i++) {
            SelenideElement nav = navBlocks.get(i);
            WebVertex navNode = new WebVertex("Menu Navigation " + (i + 1));
            ElementsCollection links = nav.$$(Constants.link).filterBy(attribute(Constants.href));
            for (SelenideElement linkElem : links) {
                WebVertex linkNode = new WebVertex(linkElem.getText());
                String url = linkElem.attr(Constants.href);
                linkNode.addChild(new WebVertex("Click on " + url));
                navNode.addChild(linkNode);
                leavesURLs.add(new NodeURL(linkNode, url));
            }
            roots.add(navNode);
        }
        return new ForestInfo(roots, leavesURLs);
    }
}
