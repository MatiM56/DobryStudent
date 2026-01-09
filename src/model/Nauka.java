package model;

import java.util.Arrays;

/**
 * Klasa reprezentująca aktywności i zdarzenia związane z nauką.
 * Zawiera scenariusze pracy grupowej, pomocy innym studentom i wyzwań akademickich.
 */
public class Nauka extends Event.ActivityType {
    /**
     * Konstruktor inicjalizujący listę zdarzeń dla aktywności Nauka.
     */
    public Nauka() {
        super("Nauka", Arrays.asList(
            // Grupa ma problemy z projektem
            new Event(
                "Grupa ma problemy z projektem",
                "Organizujesz spotkanie i wspierasz",
                "Krytykujesz i oczekujesz że problem sam się rozwiąże",
                new int[]{0, 0, 4, -3, 0, 0, 0, 0, 5, 0}, // +asertywnosc, -uleglosc, +wspolpraca
                new int[]{-4, 0, 0, 0, 0, 6, 0, 0, 0, 3}   // -empatia, +egocentryzm, +agresja
            ),
            // Ktoś prosi o pomoc z materiałem
            new Event(
                "Ktoś prosi o pomoc z materiałem",
                "Tłumaczysz cierpliwie",
                "Ignorujesz i mówisz że sam musiałeś sobie radzić",
                new int[]{5, 0, 0, 0, 0, 0, 4, 0, 0, 0}, // +empatia, +samoregulacja
                new int[]{0, 0, 0, 0, 0, 0, 0, 3, 0, 0}   // +impulsywnosc, +nieczulosc
            )
        ));
    }
}
