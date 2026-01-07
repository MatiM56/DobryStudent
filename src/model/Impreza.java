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
                new int[]{1, -1, 0, 0, 0, 0, 1, -1, 0, 0}, // +empatia, -nieczulosc, +samoregulacja, -impulsywnosc
                new int[]{-1, 0, 0, 0, -1, 0, 0, 1, 0, 1}  // -empatia, -samowiadomosc, +impulsywnosc, +agresja
            ),
            // Konflikt znajomych
            new Event(
                "Twoi znajomi się upili i zaczynają się kłócić",
                "Mediacja i spokój",
                "Podjudzasz konflikt aż zaczną się bić",
                new int[]{0, 0, 1, 0, 0, 0, 1, 0, 0, -1}, // +asertywnosc, +samoregulacja, -agresja
                new int[]{-1, 0, 0, 0, 0, 0, 0, 1, 0, 1}   // -empatia, +impulsywnosc, +agresja
            ),
            // Pożyczka znajomemu
            new Event(
                "Znajomemu brakuje pieniędzy na taksówkę",
                "Pożyczasz z sercem i zaufaniem",
                "Mówisz że ma nogii niech się martwi",
                new int[]{1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // +empatia, +wspolpraca, -egocentryzm
                new int[]{-1, 1, 0, 0, 0, 1, 0, 0, 0, 0}   // -empatia, +nieczulosc, +egocentryzm
            ),
            // Ktoś czuje się źle na imprezie
            new Event(
                "Ktoś czuje się źle na imprezie",
                "Pomagasz, dajesz wodę i siedzisz z nim",
                "Ignorujesz i idziesz do baru pić dalej",
                new int[]{1, 0, 0, 0, 0, 1, 0, -1, 0, 0}, // +empatia, +samoregulacja, -impulsywnosc
                new int[]{0, 1, 0, 0, 0, 0, -1, 1, 0, 0}   // +nieczulosc, -samoregulacja, +impulsywnosc
            )
        ));
    }
}
