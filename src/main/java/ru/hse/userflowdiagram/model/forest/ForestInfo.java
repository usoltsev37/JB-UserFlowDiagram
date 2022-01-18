package ru.hse.userflowdiagram.model.forest;

import ru.hse.userflowdiagram.model.graph.WebVertex;

import java.util.List;

public class ForestInfo {
    public List<WebVertex> roots;
    public List<NodeURL> leavesURLs;

    public ForestInfo(List<WebVertex> roots, List<NodeURL> leavesURLs) {
        this.roots = roots;
        this.leavesURLs = leavesURLs;
    }
}
