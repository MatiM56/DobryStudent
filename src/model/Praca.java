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
                new int[]{3, 0, 0, 4, 0, 0, 0, 2, 6, 0}, 
                new int[]{-2, 1, 4, 0, 2, 2, 0, 1, 0, 0}  
            ),
            // Konflikty w zespole
            new Event(
                "Konflikty w zespole",
                "Pomagasz w mediacji",
                "Unikasz konfrontacji i plotkujesz",
                new int[]{0, 0, 5, 0, 0, 0, 4, 0, 0, 0}, // +asertywnosc, +samoregulacja
                new int[]{0, 6, 0, 5, 0, 0, 0, 0, 0, 0}   // +nieczulosc, +uleglosc
            )
        ));
    }
}
