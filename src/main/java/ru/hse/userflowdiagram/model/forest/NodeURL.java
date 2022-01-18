package ru.hse.userflowdiagram.model.forest;

import ru.hse.userflowdiagram.model.graph.WebVertex;

public class NodeURL {
    public WebVertex node;
    public String url;

    public NodeURL(WebVertex node, String url) {
        this.node = node;
        this.url = url;
    }
}
