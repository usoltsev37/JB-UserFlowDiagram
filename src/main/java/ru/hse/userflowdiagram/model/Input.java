package ru.hse.userflowdiagram.model;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import guru.nidi.graphviz.model.MutableNode;
import ru.hse.userflowdiagram.Constants;
import ru.hse.userflowdiagram.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static guru.nidi.graphviz.model.Factory.mutNode;

public class Input implements Attribute {
    @Override
    public List<MutableNode> get() {
        ElementsCollection inputs = Selenide.$$(Constants.input);
        List<MutableNode> result = new ArrayList<>(inputs.size());
        for (var inp : inputs) {
            StringBuilder nodeValueBuilder = new StringBuilder();
            String type = inp.getAttribute(Constants.type);
            if (!Utils.isAttributeExists(type)) {
                nodeValueBuilder.append(Constants.input).append("\n");
                result.add(mutNode(nodeValueBuilder.toString()));
                continue;
            }
            nodeValueBuilder.append(type).append(" ").append(Constants.input).append("\n");
            switch (Objects.requireNonNull(type)) {
                case Constants.text :
                case Constants.email :
                case Constants.search :
                    String placeholder = inp.getAttribute(Constants.placeholder);
                    if (Utils.isAttributeExists(placeholder)) {
                        nodeValueBuilder.append("With ").append(Constants.placeholder).append(" \"").append(placeholder).append("\"\n");
                    }
                    break;
                case Constants.password :
                    break;
                case Constants.radio :
                    String nameRadio = inp.getAttribute(Constants.name);
                    String valueRadio = inp.getAttribute(Constants.value);
                    if (!Utils.isAttributeExists(nameRadio)) {
                        break;
                    }
                    nodeValueBuilder.append("Name: ").append(nameRadio);
                    if (Utils.isAttributeExists(valueRadio)) {
                        nodeValueBuilder.append(" and Value: ").append(valueRadio);
                    }
                    break;
                case Constants.checkbox :
                    String nameCheckbox = inp.getAttribute(Constants.name);
                    if (!Utils.isAttributeExists(nameCheckbox)) {
                        nameCheckbox = inp.getAttribute(Constants.id);
                    }
                    if (!Utils.isAttributeExists(nameCheckbox)) {
                        continue;
                    }
                    nodeValueBuilder.append("Name: ").append(nameCheckbox);
                    break;
                case Constants.hidden :
                    break;
                case Constants.button :
                    break;
                case Constants.submit :
                    break;
                case Constants.reset :
                    break;
                case Constants.image :
                    break;
                case Constants.file :
                    break;
                default:
                    break;
            }
            result.add(mutNode(nodeValueBuilder.toString()));
        }
        return result;
    }
}
