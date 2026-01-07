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
                new int[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0}, // +empatia, +samoregulacja
                new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 1}   // +impulsywnosc, +agresja
            ),
            // Ktoś upuszcza rzeczy
            new Event(
                "Ktoś upuszcza rzeczy",
                "Pomagasz i pocieszasz",
                "Ignorujesz i komentujesz",
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // +empatia, +wspolpraca
                new int[]{0, 1, 0, 0, 0, 1, 0, 0, 0, 0}   // +nieczulosc, +egocentryzm
            )
        ));
    }
}
