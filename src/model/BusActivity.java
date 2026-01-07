package model;

import java.util.Arrays;

public class BusActivity extends Event.ActivityType {
    public BusActivity() {
        super("BusActivity", Arrays.asList(
            // Kobieta w ciąży prosi o miejsce
            new Event(
                "Kobieta w ciąży prosi o miejsce",
                "Pomagas i zaczynasz small talk",
                "Mówisz, że 'trzeba się zabezpieczyć'",
                new int[]{1, -1, 1, -1, 0, 0, 0, 0, 0, 0}, // +empatia, -nieczulosc, +asertywnosc, -uleglosc
                new int[]{-1, 0, 0, 0, 0, 1, 0, 0, 0, 1}  // -empatia, +egocentryzm, +agresja
            ),
            // Osoba z ciężkim bagażem
            new Event(
                "Osoba z ciężkim bagażem",
                "Pomagasz z uśmiechem",
                "Mówisz 'jak się przeprowadzasz to weź DHL'",
                new int[]{1, 0, 0, 0, 0, -1, 0, 0, 1, 0}, // +empatia, -egocentryzm, +wspolpraca
                new int[]{0, 0, 0, 1, 0, 1, 0, 0, -1, 0}  // +uleglosc, +egocentryzm, -wspolpraca
            ),
            // Ktoś upuszcza torbę
            new Event(
                "Ktoś upuszcza torbę",
                "Pomagasz i pocieszasz",
                "Ignorujesz i komentujesz głośno",
                new int[]{1, -1, 0, 0, 0, -1, 0, 0, 0, 0}, // +empatia, -nieczulosc, -egocentryzm
                new int[]{-1, 1, 0, 0, 0, 1, 0, 0, 0, 0}   // -empatia, +nieczulosc, +egocentryzm
            )
        ));
    }
}
