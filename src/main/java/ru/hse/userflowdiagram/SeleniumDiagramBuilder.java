package ru.hse.userflowdiagram;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static guru.nidi.graphviz.model.Factory.*;

public class SeleniumDiagramBuilder {
    private final String URL_STR;
    private final String fileName;
    private final MutableGraph graph = mutGraph("UsefFlowDiagram");
    private final MutableNode root;

    public SeleniumDiagramBuilder(String URL_STR,  String fileName) {
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
        Selenide.open(curr_page);
        parent.addLink(getInputs());
        parent.addLink(getButtons());
    }

    private MutableNode getTitle() {
        ElementsCollection titles = Selenide.$$("title");
        return mutNode(titles.first().getOwnText());
    }

    private List<MutableNode> getInputs() {
        ElementsCollection inputs = Selenide.$$("input");
        List<MutableNode> result = new ArrayList<>(inputs.size());
        for (var inp : inputs) {
            StringBuilder nodeValueBuilder = new StringBuilder();
            System.out.println(inp);
            String type = inp.getAttribute("type");
            if (!isCorrectStr(type)) {
                nodeValueBuilder.append("input\n");
                result.add(mutNode(nodeValueBuilder.toString()));
                continue;
            }
            nodeValueBuilder.append(type).append(" input\n");
            switch (Objects.requireNonNull(type)) {
                case "text" :
                case "email" :
                case "search" :
                    String placeholder = inp.getAttribute("placeholder");
                    if (isCorrectStr(placeholder)) {
                        nodeValueBuilder.append("With placeholder \"").append(placeholder).append("\"\n");
                    }
                    break;
                case "password" :
                    break;
                case "radio" :
                    String nameRadio = inp.getAttribute("name");
                    String valueRadio = inp.getAttribute("value");
                    if (!isCorrectStr(nameRadio)) {
                        break;
                    }
                    nodeValueBuilder.append("Name: ").append(nameRadio);
                    if (isCorrectStr(valueRadio)) {
                        nodeValueBuilder.append(" and Value: ").append(valueRadio);
                    }
                    break;
                case "checkbox" :
                    String nameCheckbox = inp.getAttribute("name");
                    if (!isCorrectStr(nameCheckbox)) {
                        nameCheckbox = inp.getAttribute("id");
                    }
                    if (!isCorrectStr(nameCheckbox)) {
                        continue;
                    }
                    nodeValueBuilder.append("Name: ").append(nameCheckbox);
                    break;
                case "hidden" :
                    break;
                case "button" :
                    break;
                case "submit" :
                    break;
                case "reset" :
                    break;
                case "image" :
                    break;
                case "file" :
                    break;
                default:
                    break;
            }
//              String listAttr = inp.getAttribute("list");  ElementsCollection datalists = Selenide.$$("datalist");
            result.add(mutNode(nodeValueBuilder.toString()));
        }
        return result;
    }

    private List<MutableNode> getButtons() {
        ElementsCollection buttons = Selenide.$$("button");
        List<MutableNode> result = new ArrayList<>(buttons.size());
        for (var butt : buttons) {
            StringBuilder nodeValueBuilder = new StringBuilder();
            System.out.println(butt);

            String name = butt.getAttribute("aria-label");
            if (!isCorrectStr(name)) {
                name = butt.getAttribute("label");
                if (!isCorrectStr(name)) {
                    name = "";
                }
            }

            String id = butt.getAttribute("id");
            if (!isCorrectStr(id)) {
                id = butt.getAttribute("class");
                if (!isCorrectStr(id)) {
                    id = "";
                }
            }

            String type = butt.getAttribute("type");
            if (!isCorrectStr(type)) {
                type = "";
            }
            nodeValueBuilder.append(String.format("Button %s\n [id: %s, type %s]\n", name, id, type));
            result.add(mutNode(nodeValueBuilder.toString()));
        }
        return result;
    }

    private boolean isCorrectStr(String s) {
        return (s != null && !s.isEmpty());
    }
}
