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

public class Nav implements Attribute {
    @Override
    public ForestInfo get() {
        ElementsCollection navBlocks = Selenide.$$(Constants.nav);
        List<MutableNode> roots = new ArrayList<>(navBlocks.size());
        List<NodeURL> leavesURLs = new ArrayList<>();
        for (int i = 0; i < navBlocks.size(); i++) {
            SelenideElement nav = navBlocks.get(i);
            MutableNode navNode = mutNode("Menu Navigation " + (i + 1));
            ElementsCollection links = nav.$$(Constants.link).filterBy(attribute(Constants.href));
            for (SelenideElement linkElem : links) {
                MutableNode linkNode = mutNode(linkElem.getText());
                String url = linkElem.attr(Constants.href);
                linkNode.addLink(mutNode("Click on " + url));
                navNode.addLink(linkNode);
                leavesURLs.add(new NodeURL(linkNode, url));
            }
            roots.add(navNode);
        }
        return new ForestInfo(roots, leavesURLs);
    }
}
