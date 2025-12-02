package model;

import java.util.Arrays;

public class Impreza extends Event.ActivityType {
    public Impreza() {
        super("Impreza", Arrays.asList(
            // Znajomy otwiera się przed tobą
            new Event(
                "Znajomy otwiera się przed tobą",
                "Słuchasz i wspierasz",
                "Przerywasz i wyśmiewasz",
                new int[]{5, -4, 0, 0, 0, 0, 6, -3, 0, 0}, // +empatia, -nieczulosc, +samoregulacja, -impulsywnosc
                new int[]{-4, 0, 0, 0, -5, 0, 0, 7, 0, 6}  // -empatia, -samowiadomosc, +impulsywnosc, +agresja
            ),
            // Konflikt znajomych
            new Event(
                "Twoi znajomi się upili i zaczynają się kłócić",
                "Mediacja i spokój",
                "Podjudzasz konflikt aż zaczną się bić",
                new int[]{0, 0, 5, 0, 0, 0, 4, 0, 0, -3}, // +asertywnosc, +samoregulacja, -agresja
                new int[]{-6, 0, 0, 0, 0, 0, 0, 5, 0, 7}   // -empatia, +impulsywnosc, +agresja
            ),
            // Pożyczka znajomemu
            new Event(
                "Znajomemu brakuje pieniędzy na taksówkę",
                "Pożyczasz z sercem i zaufaniem",
                "Mówisz że ma nogi i niech się martwi",
                new int[]{6, 0, 0, 0, 0, 0, 0, 0, 5, 0}, // +empatia, +wspolpraca, -egocentryzm
                new int[]{-5, 4, 0, 0, 0, 7, 0, 0, 0, 0}   // -empatia, +nieczulosc, +egocentryzm
            ),
            // Ktoś czuje się źle na imprezie
            new Event(
                "Ktoś czuje się źle na imprezie",
                "Pomagasz, dajesz wodę i siedzisz z nim",
                "Ignorujesz i idziesz do baru pić dalej",
                new int[]{7, 0, 0, 0, 0, 3, 0, -4, 0, 0}, // +empatia, +samoregulacja, -impulsywnosc
                new int[]{0, 5, 0, 0, 0, 0, -6, 8, 0, 0}   // +nieczulosc, -samoregulacja, +impulsywnosc
            )
        ));
    }
}
