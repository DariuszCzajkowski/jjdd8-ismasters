package com.infoshareacademy.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingPrinter {
    private static final Logger stdout = LoggerFactory.getLogger("CONSOLE_OUT");
    private static final String BORDER = "│                                                    │\n";

    void printGreeting() {
        stdout.info("┌────────────────────────────────────────────────────┐\n");
        stdout.info(BORDER);
        stdout.info("│      " + ColorHandler.YELLOW + "Witaj w kalendarzu wydarzeń kulturalnych      " + ColorHandler.DEFAULT + "│\n");
        stdout.info(BORDER);
        stdout.info("│                (c) ISMasters 2019                  │\n");
        stdout.info(BORDER);
        stdout.info("└────────────────────────────────────────────────────┘\n");
        stdout.info("\n");
        stdout.info("\n");
    }
}