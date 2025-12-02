package model;

import java.util.Arrays;

public class BusActivity extends Event.ActivityType {
    public BusActivity() {
        super("BusActivity", Arrays.asList(
            // Kobieta w ciąży prosi o miejsce
            new Event(
                "Kobieta w ciąży prosi o miejsce",
                "Pomagasz i zaczynasz small talk",
                "Mówisz, że 'trzeba się zabezpieczyć'",
                new int[]{6, -5, 4, -3, 0, 0, 0, 0, 0, 0}, // +empatia, -nieczulosc, +asertywnosc, -uleglosc
                new int[]{-7, 0, 0, 0, 0, 8, 0, 6, 0, 4}  // -empatia, +egocentryzm, +impulsywnosc, +agresja
            ),
            // Osoba z ciężkim bagażem
            new Event(
                "Osoba z ciężkim bagażem",
                "Pomagasz z uśmiechem",
                "Mówisz 'jak się przeprowadzasz to weź kuriera'",
                new int[]{5, 0, 0, 0, 0, -6, 0, 0, 7, 0}, // +empatia, -egocentryzm, +wspolpraca
                new int[]{0, 0, 0, 4, 0, 8, 0, 0, -5, 0}  // +uleglosc, +egocentryzm, -wspolpraca
            ),
            // Ktoś upuszcza torbę
            new Event(
                "Ktoś upuszcza torbę",
                "Pomagasz i pocieszasz",
                "Śmiesz się i komentujesz",
                new int[]{4, -4, 0, 0, 0, -3, 0, 0, 0, 0}, // +empatia, -nieczulosc, -egocentryzm
                new int[]{-3, 5, 0, 0, 0, 6, 0, 0, 0, 0}   // -empatia, +nieczulosc, +egocentryzm
            )
        ));
    }
}
