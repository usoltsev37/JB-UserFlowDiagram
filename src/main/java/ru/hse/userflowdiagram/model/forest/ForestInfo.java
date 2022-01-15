package ru.hse.userflowdiagram.model.forest;

import guru.nidi.graphviz.model.MutableNode;

import java.util.List;

public class ForestInfo {
    public List<MutableNode> roots;
    public List<NodeURL> leavesURLs;

    public ForestInfo(List<MutableNode> roots, List<NodeURL> leavesURLs) {
        this.roots = roots;
        this.leavesURLs = leavesURLs;
    }
}
