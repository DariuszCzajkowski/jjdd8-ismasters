package com.infoshareacademy.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

import static com.infoshareacademy.menu.Menu.scanner;

public class ChoiceGetter {

    private static final Logger stdout = LoggerFactory.getLogger("CONSOLE_OUT");
    private static final String BORDER = "(\"│                                          │\\n\");";

    public static int getChoice() {
        int result = 0;

        try {
            String in = scanner.next();

            if ((Pattern.matches("[0-9]", in))) {
                try {
                    result = Integer.parseInt(in);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                stdout.info("\n");
                stdout.info("┌──────────────────────────────────────────┐\n");
                stdout.info(BORDER);
                stdout.info("│       " + ColorHandler.RED + "PODANO NIEPRAWIDŁOWĄ WARTOŚĆ! " + ColorHandler.DEFAULT + "     │\n");
                stdout.info(BORDER);
                stdout.info("│               JESZCZE RAZ!               │\n");
                stdout.info(BORDER);
                stdout.info("└──────────────────────────────────────────┘\n");
                stdout.info("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}