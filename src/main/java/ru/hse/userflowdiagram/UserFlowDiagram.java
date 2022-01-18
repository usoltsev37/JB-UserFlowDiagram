package ru.hse.userflowdiagram;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class UserFlowDiagram extends JApplet {
    private DiagramJFrame frame;

    private final Integer DEFAULT_WIGHT = 1500;
    private final Integer DEFAULT_HEIGHT = 800;

    public void build(String urlStr, Integer depth, Integer levelDistance, Integer nodeDistance) {
        frame = new DiagramJFrame(urlStr, depth, levelDistance, nodeDistance);
        frame.build();
    }

    public void view(Integer width, Integer height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((width == null) ? DEFAULT_WIGHT : width,
                      (height == null) ? DEFAULT_HEIGHT : height);
        frame.setVisible(true);
    }
}
