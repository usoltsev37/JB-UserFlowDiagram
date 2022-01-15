package ru.hse.userflowdiagram.model.forest;

import guru.nidi.graphviz.model.MutableNode;

public class NodeURL {
    public MutableNode node;
    public String url;

    public NodeURL(MutableNode node, String url) {
        this.node = node;
        this.url = url;
    }
}
