package ru.hse.userflowdiagram.model.graph;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.view.mxGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoldableTree extends mxGraph {
    private Map<String, mxCell> mapCells = new HashMap<>();

    private final Integer DEFAULT_X = 60;
    private final Integer DEFAULT_Y = 20;
    private final Integer DEFAULT_BUTTON_SIZE = 30;

    public void addVertex(WebVertex vertex) {
        var cell = (mxCell) insertVertex(getDefaultParent(), vertex.getId(), vertex.getValue(), DEFAULT_X, DEFAULT_Y, 0, 0);
        this.updateCellSize(cell, true);
        var geom = cell.getGeometry();
        geom.setWidth(geom.getWidth() + DEFAULT_BUTTON_SIZE);
        mapCells.put(vertex.getId(), cell);
    }

    public void addEdge(WebVertex from, WebVertex to) {
        if (!mapCells.containsKey(from.getId())) {
            addVertex(from);
        }
        if (!mapCells.containsKey(to.getId())) {
            addVertex(to);
        }
        insertEdge(this.getDefaultParent(), null, "", mapCells.get(from.getId()), mapCells.get(to.getId()));
    }

    @Override
    public boolean isCellFoldable(Object cell, boolean collapse) {
        boolean result = super.isCellFoldable(cell, collapse);
        if(!result) {
            return this.getOutgoingEdges(cell).length > 0;
        }
        return result;
    }

    @Override
    public Object[] foldCells(boolean collapse, boolean recurse, Object[] cells, boolean checkFoldable) {
        if(cells == null) {
            cells = getFoldableCells(getSelectionCells(), collapse);
        }

        this.getModel().beginUpdate();
        try {
            toggleSubtree(this, cells[0], !collapse);
            this.model.setCollapsed(cells[0], collapse);
            fireEvent(new mxEventObject(mxEvent.FOLD_CELLS, "cells", cells, "collapse", collapse, "recurse", recurse));
        }
        finally {
            this.getModel().endUpdate();
        }

        return cells;
    }

    private void toggleSubtree(mxGraph graph, Object cellSelected, boolean show) {
        List<Object> cellsAffected = new ArrayList<>();
        graph.traverse(cellSelected, true, (vertex, edge) -> {
            if(vertex != cellSelected) {
                cellsAffected.add(vertex);
            }
            return vertex == cellSelected || !graph.isCellCollapsed(vertex);
        });

        graph.toggleCells(show, cellsAffected.toArray(), true);
    }
}
