package ru.hse.userflowdiagram;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import ru.hse.userflowdiagram.model.Button;
import ru.hse.userflowdiagram.model.Input;
import ru.hse.userflowdiagram.model.Link;
import ru.hse.userflowdiagram.model.Nav;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.*;

public class DiagramBuilder {
    private final String URL_STR;
    private final String fileName;
    private final MutableGraph graph = mutGraph("UserFlowDiagram");
    private final MutableNode root;

    private final Map<MutableNode, String> map = new HashMap<>();
    private final Set<String> setVisited = new HashSet<>();

    private final Nav nav = new Nav();
    private final Input input = new Input();
    private final Button button = new Button();
    private final Link link = new Link();

    public DiagramBuilder(String URL_STR, String fileName) {
        this.URL_STR = URL_STR;
        this.fileName = fileName;
        Selenide.open(URL_STR);
        root = getTitle();
        graph.add(root);
    }

    public void build() throws IOException {
        dfs(root, URL_STR);
        Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File(fileName));
    }

    private void dfs(MutableNode parent, String curr_page) {
        setVisited.add(curr_page);
        Selenide.open(curr_page);

        List<MutableNode> children = new ArrayList<>();

        List<MutableNode> navs = nav.get();
        List<MutableNode> inputs = input.get();
        List<MutableNode> buttons = button.get();
        List<MutableNode> links = link.get();

        children.addAll(navs);
        parent.addLink(navs);

//        for (var child : children) {
//            String link = map.get(child);
//            if (!setVisited.contains(link)) {
//                dfs(child, link);
//            }
//        }
    }

    private MutableNode getTitle() {
        ElementsCollection titles = Selenide.$$("title");
        return mutNode(titles.first().getOwnText());
    }
}
