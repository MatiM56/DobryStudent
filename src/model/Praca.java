package model;

import java.util.Arrays;

public class Praca extends Event.ActivityType {
    public Praca() {
        super("Praca", Arrays.asList(
            // Dodatkowe zadanie od szefa
            new Event(
                "Dodatkowe zadanie od szefa",
                "Podejmujesz, wspierasz zespół",
                "Grzecznie odmawiasz i tłumaczysz powody",
                new int[]{0, 0, 0, -1, 0, 0, 1, 0, 1, 0}, // -uleglosc, +samoregulacja, +wspolpraca
                new int[]{0, 0, 0, 0, 0, 1, 0, 1, 0, 0}   // +egocentryzm, +impulsywnosc
            ),
            // Konflikty w zespole
            new Event(
                "Konflikty w zespole",
                "Pomagasz w mediacji",
                "Unikasz konfrontacji i plotkujesz",
                new int[]{0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, // +asertywnosc, +samoregulacja
                new int[]{0, 1, 0, 1, 0, 0, 0, 0, 0, 0}   // +nieczulosc, +uleglosc
            )
        ));
    }
}
