package ru.hse.userflowdiagram.model.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WebVertex {
    private final String id = UUID.randomUUID().toString();
    private final String value;
    private List<WebVertex> children = new ArrayList<>();

    public WebVertex(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void addChildren(List<WebVertex> vertices) {
        children.addAll(vertices);
    }

    public void addChild(WebVertex vertex) {
        children.add(vertex);
    }

    public List<WebVertex> getChildren() {
        return children;
    }
}
