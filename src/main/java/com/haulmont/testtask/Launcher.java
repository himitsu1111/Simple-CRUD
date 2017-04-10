package com.haulmont.testtask;

/**
 * Created by secret on 10.04.2017.
 */

import static eu.hurion.vaadin.heroku.VaadinForHeroku.forApplication;
import static eu.hurion.vaadin.heroku.VaadinForHeroku.herokuServer;

public class Launcher {

    public static void main(final String[] args) {
        herokuServer(forApplication(MainUI.class)).start();
    }
}
