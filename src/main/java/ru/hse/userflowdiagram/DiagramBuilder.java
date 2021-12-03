package ru.hse.userflowdiagram;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiagramBuilder {

    private final String URL_STR;
    private final String fileName;
    private final String tab;
    private final Document doc;

    public DiagramBuilder(String URL_STR,  String fileName, String tab) {
        Document doc;
        this.URL_STR = URL_STR;
        this.fileName = fileName;
        this.tab = tab;
        try {
            doc = Jsoup.connect(URL_STR).get();
        } catch (IOException e) {
            doc = null;
            e.printStackTrace(System.err);
        }
        this.doc = doc;
    }

    public void build() {
        StringBuilder strBuildResult = new StringBuilder();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            strBuildResult.append(getTitle());

            strBuildResult.append(getAdress());
            strBuildResult.append(getAudios());
            strBuildResult.append(getVideos());
            strBuildResult.append(getButtons());
            strBuildResult.append(getDetails());
            strBuildResult.append(getDialogs());
            strBuildResult.append(getFieldsets());
            strBuildResult.append(getInputs());
            strBuildResult.append(getMenuitems());
            strBuildResult.append(getProgress());
            strBuildResult.append(getSelect());
            strBuildResult.append(getTables());
            strBuildResult.append(getTextarea());

            writer.write(strBuildResult.toString());
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private String getAdress() {
        return getElements("adress", "Adress");
    }

    private String getAudios() {
        return getElements("audio", "Audios");
    }

    private String getVideos() {
        return getElements("video", "Videos");
    }

    private String getButtons() {
        String tag = "button";
        String title = "Buttons";
        Elements elements = doc.select(tag);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("%s %s (%d):\n", tab, title, elements.size()));
        for (Element element : elements) {
            String name = "EMPTY_NAME";
            if (!element.attr("aria-label").isEmpty()) {
                name = element.attr("aria-label");
            } else if (!element.attr("label").isEmpty()) {
                name = element.attr("label");
            }

            String id = "EMPTY_ID";
            if (!element.attr("id").isEmpty()) {
                id = element.attr("id");
            } else if (!element.attr("class").isEmpty()) {
                id = element.attr("class");
            }

            String type = "EMPTY_TYPE";
            if (!element.attr("type").isEmpty()) {
                type = element.attr("type");
            }

            strBuilder.append(String.format("%s%s * %s [id: %s, type %s]\n", tab, "\t", name, id, type));
        }
        if (elements.isEmpty()) {
            strBuilder.append(String.format("%s%s No %s on the page\n", tab, "\t", title));
        }
        return strBuilder.toString();
    }

    private String getDetails() {
        return getElements("details", "Details");
    }

    private String getDialogs() {
        return getElements("dialog", "Dialogs");
    }

    private String getFieldsets() {
        return getElements("fieldset", "Fieldsets");
    }

    private String getInputs() {
        String tag = "input";
        String title = "Inputs";
        Elements elements = doc.getElementsByTag(tag);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("%s %s (%d):\n", tab, title, elements.size()));
        for (Element element : elements) {
            String type = element.attr("type");
            List<String> actions = new ArrayList<>();
            switch (type) {
                case ("text") :
                    break;
                case ("password") :
                    break;
                case ("radio") :
                    String nameRadio = element.attr("name");
                    String valueRadio = element.attr("value");
                    if (nameRadio.isEmpty()) {
                        nameRadio = "EMPTY_NAME";
                    }
                    if (valueRadio.isEmpty()) {
                        valueRadio = "EMPTY_VALUE";
                    }
                    actions.add(String.format("Turn %s on value %s", nameRadio, valueRadio));
                    actions.add(String.format("Turn %s off value %s", nameRadio, valueRadio));
                    break;
                case ("checkbox") :
                    String nameCheckbox = element.attr("name");
                    if (nameCheckbox.isEmpty()) {
                        nameCheckbox = element.attr("id");
                    }
                    if (nameCheckbox.isEmpty()) {
                        nameCheckbox = "EMPTY_NAME";
                    }
                    actions.add(String.format("Turn on %s", nameCheckbox));
                    actions.add(String.format("Turn off %s", nameCheckbox));
                    break;
                case ("hidden") :
                    String nameHidden = element.attr("name");
                    if (nameHidden.isEmpty()) {
                        nameHidden = element.attr("id");
                    }
                    if (nameHidden.isEmpty()) {
                        nameHidden = "EMPTY_NAME";
                    }
                    actions.add(String.format("Click on %s", nameHidden));
                    break;
                case ("button") :
                    break;
                case ("submit") :
                    break;
                case ("reset") :
                    break;
                case ("image") :
                    break;
                case ("file") :
                    break;
                case ("email") :
                    actions.add("Enter email address");
                    break;
                case ("search") :
                    actions.add("Enter text to search");
                    break;
                default:
                    return getElements(tag, title);
            }
            strBuilder.append(String.format("%s%s * %s -- Acrions: ", tab, "\t", type)).append(actions).append("\n");
        }
        if (elements.isEmpty()) {
            strBuilder.append(String.format("%s%s No %s on the page\n", tab, "\t", title));
        }

        strBuilder.append(doc.html());

        return strBuilder.toString();
    }

    private String getMenuitems() {
        return getElements("menuitem", "Menuitems");
    }

    private String getProgress() {
        return getElements("progress", "Progress");
    }

    private String getSelect() {
        return getElements("select", "Select");
    }

    private String getTables() {
        return getElements("table", "Tables");
    }

    private String getTextarea() {
        return getElements("textarea", "Textarea");
    }

    private String getTitle() {
        Elements titles = doc.select("title");
        if (titles.isEmpty()) {
            return String.format("Site: %s\n", URL_STR);
        } else {
            return String.format("Site: %s\n", titles.get(0).text());
        }
    }

    private String getElements(String tag, String title) {
        Elements elements = doc.select(tag);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(String.format("%s %s (%d):\n", tab, title, elements.size()));
        for (Element element : elements) {
            strBuilder.append(String.format("%s%s * ", tab, "\t")).append(element).append("\n");
        }
        if (elements.isEmpty()) {
            strBuilder.append(String.format("%s%s No %s on the page\n", tab, "\t", title));
        }
        return strBuilder.toString();
    }

}
