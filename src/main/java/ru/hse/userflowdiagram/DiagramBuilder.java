package ru.hse.userflowdiagram;

import com.codeborne.selenide.Selenide;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import ru.hse.userflowdiagram.model.attribute.Button;
import ru.hse.userflowdiagram.model.attribute.Input;
import ru.hse.userflowdiagram.model.attribute.Link;
import ru.hse.userflowdiagram.model.attribute.Nav;
import ru.hse.userflowdiagram.model.forest.ForestInfo;
import ru.hse.userflowdiagram.model.forest.NodeURL;
import ru.hse.userflowdiagram.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static guru.nidi.graphviz.model.Factory.mutGraph;

public class DiagramBuilder {
    private final String URL_STR;
    private final String fileName;
    private final MutableGraph graph = mutGraph("UserFlowDiagram");
    private final MutableNode root;
    private int depth = 1;

    private final Set<String> setVisited = new HashSet<>();

    private final Nav nav = new Nav();
    private final Input input = new Input();
    private final Button button = new Button();
    private final Link link = new Link();

    public DiagramBuilder(String URL_STR, String fileName) {
        this.URL_STR = URL_STR;
        this.fileName = fileName;
        Selenide.open(URL_STR);
        root =  Utils.getTitle();
        graph.add(root);
    }

    public void build(int depth, Format format) throws IOException {
        if (depth < 0) {
            throw new IllegalArgumentException("Depth must be positive");
        }
        this.depth = depth;
        bfs(root, URL_STR);
        Graphviz.fromGraph(graph).render(format).toFile(new File(fileName));
    }

    private final Queue<NodeURL> queue = new LinkedList<>();

    private void bfs(MutableNode parent, String curr_page) {
        queue.add(new NodeURL(parent, curr_page));
        int elementsToDepthIncrease = 1, nextElementsToDepthIncrease = 0, currentDepth = 0;;
        while(!queue.isEmpty()) {
            NodeURL nodeURL = queue.poll();
            MutableNode node = nodeURL.node;
            String url = nodeURL.url;
            if (!setVisited.contains(url)) {
                setVisited.add(url);
                Selenide.open(url);

                List<NodeURL> children = new ArrayList<>();

                ForestInfo navs = nav.get();
//            ...
                children.addAll(navs.leavesURLs);
//            ...
                node.addLink(navs.roots);
//            ...

                nextElementsToDepthIncrease += children.size();
                elementsToDepthIncrease -= 1;
                if (elementsToDepthIncrease == 0) {
                    currentDepth += 1;
                    if (currentDepth >= depth) {
                        return;
                    }
                    elementsToDepthIncrease = nextElementsToDepthIncrease;
                    nextElementsToDepthIncrease = 0;
                }
                queue.addAll(children);
            }
        }
    }
}
