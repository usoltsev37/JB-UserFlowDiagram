package ru.hse.userflowdiagram.model.attribute;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.hse.userflowdiagram.model.graph.WebVertex;
import ru.hse.userflowdiagram.utils.Constants;
import ru.hse.userflowdiagram.model.forest.ForestInfo;
import ru.hse.userflowdiagram.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Button implements Attribute {
    @Override
    public ForestInfo get() {
        ElementsCollection buttons = Selenide.$$(Constants.button);
        List<WebVertex> result = new ArrayList<>(buttons.size());
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
            WebVertex node = new WebVertex(nodeValueBuilder.toString());
            result.add(node);
        }
        return new ForestInfo(result, new ArrayList<>());
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
