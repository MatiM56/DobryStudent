package model;

import java.util.Arrays;

public class Spanie extends Event.ActivityType {
    public Spanie() {
        super("Sen", Arrays.asList(
            // Decyzja o odpoczynku
            new Event(
                "Decyzja o odpoczynku",
                "Kładziesz się wcześnie",
                "Ignorujesz sen",
                new int[]{0, 0, 0, 0, 0, 0, 5, 0, 0, 0}, // +samoregulacja
                new int[]{0, 0, 0, 0, 0, 0, 0, 6, 0, 0}   // +impulsywnosc
            ),
            // Poranna frustracja
            new Event(
                "Poranna frustracja",
                "Wstajesz i rozmawiasz o tym z współlokatorami",
                "Wstajesz i czepiasz się wszystkich dookoła",
                new int[]{0, 0, 0, 0, 0, 4, 0, 0, 0, -3}, // +samoregulacja, -agresja
                new int[]{0, 0, 0, 0, 0, 0, 0, 5, 0, 4}   // +impulsywnosc, +agresja
            )
        ));
    }
}
