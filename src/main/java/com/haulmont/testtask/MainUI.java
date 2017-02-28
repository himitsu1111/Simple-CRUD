package com.haulmont.testtask;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;


@Title("My UI")
@Theme("valo")
public class MainUI extends UI {
    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout content = new VerticalLayout();
        setContent(content);

        content.addComponent(new MyGridLayout());
        content.setImmediate(true);
    }
}