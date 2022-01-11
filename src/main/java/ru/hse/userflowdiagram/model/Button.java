package ru.hse.userflowdiagram.model;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.nidi.graphviz.model.MutableNode;
import ru.hse.userflowdiagram.Constants;
import ru.hse.userflowdiagram.Utils;

import java.util.ArrayList;
import java.util.List;

import static guru.nidi.graphviz.model.Factory.mutNode;

public class Button implements Attribute {
    @Override
    public List<MutableNode> get() {
        ElementsCollection buttons = Selenide.$$(Constants.button);
        List<MutableNode> result = new ArrayList<>(buttons.size());
        for (SelenideElement butt : buttons) {
            StringBuilder nodeValueBuilder = new StringBuilder();

            String name = getButtonName(butt);
            String id = butt.getAttribute(Constants.id);
            if (!Utils.isAttributeExists(id)) {
                id = butt.getAttribute(Constants.classAttr);
                if (!Utils.isAttributeExists(id)) {
                    id = "";
                }
            }

            String type = butt.getAttribute(Constants.type);
            if (!Utils.isAttributeExists(type)) {
                type = "";
            }
            nodeValueBuilder.append(String.format("Button %s\n [%s: %s, %s %s]\n", name, Constants.id, id, Constants.type, type));
            MutableNode node = mutNode(nodeValueBuilder.toString());
            result.add(node);
        }
        return result;
    }

    private String getButtonName(SelenideElement butt) {
        String name = butt.getText();
        if (!Utils.isAttributeExists(name)) {
            name = butt.getAttribute(Constants.ariaLabel);
            if (!Utils.isAttributeExists(name)) {
                name = butt.getAttribute(Constants.label);
                if (!Utils.isAttributeExists(name)) {
                    name = "";
                }
            }
        }
        return name;
    }
}
