package model;

import java.util.Arrays;

public class Sklep extends Event.ActivityType {
    public Sklep() {
        super("Biedronka", Arrays.asList(
            // Długa kolejka
            new Event(
                "Długa kolejka",
                "Cierpliwy i pomocny",
                "Popychasz i krzyczysz",
                new int[]{4, 0, 0, 0, 0, 0, 6, 0, 0, 0}, // +empatia, +samoregulacja
                new int[]{0, 0, 0, 0, 0, 0, 0, 5, 0, 7}   // +impulsywnosc, +agresja
            ),
            // Ktoś upuszcza rzeczy
            new Event(
                "Ktoś upuszcza rzeczy",
                "Pomagasz i pocieszasz",
                "Ignorujesz i komentujesz",
                new int[]{5, 0, 0, 0, 0, 0, 0, 0, 4, 0}, // +empatia, +wspolpraca
                new int[]{0, 6, 0, 0, 0, 5, 0, 0, 0, 0}   // +nieczulosc, +egocentryzm
            )
        ));
    }
}
