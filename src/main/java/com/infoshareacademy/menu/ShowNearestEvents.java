package com.infoshareacademy.menu;

import com.infoshareacademy.domain.parser.Event;
import com.infoshareacademy.repository.FilterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static com.infoshareacademy.menu.PrintEvents.printEvents;

public class ShowNearestEvents {

    private static final Logger stdout = LoggerFactory.getLogger("CONSOLE_OUT");

    void showNearestEvents() {
        FilterRepository filterRepository = new FilterRepository();

        int submenu2ExitCode = 0;

        while (submenu2ExitCode != 9) {
            stdout.info("\n");
            new PrintHeader().printHeader();
            stdout.info("│                                                    │\n");
            stdout.info("│   1. Pokaż dzisiejsze wydarzenia                   │\n");
            stdout.info("│   2. Pokaż jutrzejsze wydarzenia                   │\n");
            stdout.info("│   3. Pokaż wydarzenia za 3 dni                     │\n");
            stdout.info("│                                                    │\n");
            stdout.info("│  \u001b[36m 9. Wróć                       \u001b[0m                   │\n");
            stdout.info("│                                                    │\n");
            stdout.info("└────────────────────────────────────────────────────┘\n");
            stdout.info("\n");
            stdout.info("Wpisz liczbę: \n");

            switch (GetChoice.getChoice()) {
                case 1:

                    printEvents(filterRepository.filterAllDatesEvents(LocalDate.now().toString()));
                    stdout.info("\n");
                    break;
                case 2:

                    printEvents(filterRepository.filterAllDatesEvents(LocalDate.now().plusDays(1).toString()));
                    stdout.info("\n");
                    break;
                case 3:

                    printEvents(filterRepository.filterAllDatesEvents(LocalDate.now().plusDays(2).toString()));
                    stdout.info("\n");
                    break;
                case 9:
                    stdout.info("        POWRÓT DO POPRZEDNIEGO MENU       \n");
                    submenu2ExitCode = 9;
                    break;
                case 0:
                    break;
                default:
                    new PrintNothingHere().printNothingHere();
            }
        }
    }
}
