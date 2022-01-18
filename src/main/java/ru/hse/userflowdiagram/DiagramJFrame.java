package ru.hse.userflowdiagram;

import com.codeborne.selenide.Selenide;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import ru.hse.userflowdiagram.model.attribute.Button;
import ru.hse.userflowdiagram.model.attribute.Input;
import ru.hse.userflowdiagram.model.attribute.Link;
import ru.hse.userflowdiagram.model.attribute.Nav;
import ru.hse.userflowdiagram.model.forest.ForestInfo;
import ru.hse.userflowdiagram.model.forest.NodeURL;
import ru.hse.userflowdiagram.model.graph.FoldableTree;
import ru.hse.userflowdiagram.model.graph.WebVertex;
import ru.hse.userflowdiagram.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.JFrame;

public class DiagramJFrame extends JFrame {
    private final String urlStr;
    private final Integer depth;
    private final Integer levelDistance;
    private final Integer nodeDistance;

    private final Integer DEFAULT_DEPTH = 1;
    private final Integer DEFAULT_LEVEL_DIST = 30;
    private final Integer DEFAULT_NODE_DIST = 10;

    private final Set<String> setVisited = new HashSet<>();
    private final Queue<NodeURL> queue = new LinkedList<>();

    private final FoldableTree graph = new FoldableTree();
    private final WebVertex root;

    private final Nav nav = new Nav();
    private final Input input = new Input();
    private final Button button = new Button();
    private final Link link = new Link();

    public DiagramJFrame(String urlStr, Integer depth, Integer levelDistance, Integer nodeDistance) {
        if (depth != null && depth < 0) {
            throw new IllegalArgumentException("Depth must be positive");
        }

        this.urlStr = urlStr;
        this.depth = (depth == null) ? DEFAULT_DEPTH : depth;
        this.levelDistance = (levelDistance == null) ? DEFAULT_LEVEL_DIST : levelDistance;
        this.nodeDistance = (nodeDistance == null) ? DEFAULT_NODE_DIST : nodeDistance;

        Selenide.open(urlStr);
        root =  Utils.getTitle();
        graph.addVertex(root);
    }

    public void build() {
        bfs(root, urlStr);
        mxCompactTreeLayout layout = setUpTreeLayout();
        buildGraphVisualization(layout);
        setUpGraphComponent(layout);
    }

    private void bfs(WebVertex parent, String curr_page) {
        queue.add(new NodeURL(parent, curr_page));
        int elementsToDepthIncrease = 1, nextElementsToDepthIncrease = 0, currentDepth = 0;;
        while(!queue.isEmpty()) {
            NodeURL nodeURL = queue.poll();
            WebVertex node = nodeURL.node;
            String url = nodeURL.url;
            if (!setVisited.contains(url)) {
                setVisited.add(url);
                Selenide.open(url);

                List<NodeURL> children = new ArrayList<>();

                ForestInfo navs = nav.get();
//            ...
                children.addAll(navs.leavesURLs);
//            ...
                node.addChildren(navs.roots);
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

    private mxCompactTreeLayout setUpTreeLayout() {
        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
        layout.setUseBoundingBox(false);
        layout.setEdgeRouting(false);
        layout.setLevelDistance(levelDistance);
        layout.setNodeDistance(nodeDistance);
        return layout;
    }

    private void buildGraphVisualization(mxCompactTreeLayout layout) {
        graph.getModel().beginUpdate();
        try {
            buildGraph(root);
            layout.execute(graph.getDefaultParent());
        } finally {
            graph.getModel().endUpdate();
        }
    }

    private void buildGraph(WebVertex node) {
        for (var child : node.getChildren()) {
            graph.addVertex(child);
            graph.addEdge(node, child);
        }
        for (var child : node.getChildren()) {
            buildGraph(child);
        }
    }

    private void setUpGraphComponent(mxCompactTreeLayout layout) {
        graph.addListener(mxEvent.FOLD_CELLS, (sender, evt) -> layout.execute(graph.getDefaultParent()));
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }
}
