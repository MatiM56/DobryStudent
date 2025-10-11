package model;

import java.util.Arrays;

public class Nauka extends Event.ActivityType {
    public Nauka() {
        super("Nauka", Arrays.asList(
            // Grupa ma problemy z projektem
            new Event(
                "Grupa ma problemy z projektem",
                "Organizujesz spotkanie i wspierasz",
                "Krytykujesz i oczekujesz że problem sam się rozwiąże",
                new int[]{0, 0, 1, -1, 0, 0, 0, 0, 1, 0}, // +asertywnosc, -uleglosc, +wspolpraca
                new int[]{-1, 0, 0, 0, 0, 1, 0, 0, 0, 1}   // -empatia, +egocentryzm, +agresja
            ),
            // Ktoś prosi o pomoc z materiałem
            new Event(
                "Ktoś prosi o pomoc z materiałem",
                "Tłumaczysz cierpliwie",
                "Ignorujesz i mówisz że sam musiałeś sobie radzić",
                new int[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0}, // +empatia, +samoregulacja
                new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0}   // +impulsywnosc, +nieczulosc
            )
        ));
    }
}
