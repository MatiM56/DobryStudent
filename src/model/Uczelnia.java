package model;

import java.util.Arrays;

/**
 * Klasa reprezentująca aktywności i zdarzenia związane z życiem uczelnianym.
 * Zawiera scenariusze wykładów, egzaminów i interakcji akademickich.
 */
public class Uczelnia extends Event.ActivityType {
    /**
     * Konstruktor inicjalizujący listę zdarzeń dla aktywności Uczelnia.
     */
    public Uczelnia() {
        super("Uczelnia", Arrays.asList(
            // Dyskusja na wykładzie
            new Event(
                "Dyskusja na wykładzie",
                "Wnosisz konstruktywne uwagi",
                "Przerywasz i lekceważysz innych",
                new int[]{0, 0, 4, 0, 5, 0, 0, 0, 0, -3}, // +asertywnosc, +samowiadomosc, -agresja
                new int[]{0, 6, 0, 0, 0, 0, 0, 0, 0, 4}   // +nieczulosc, +agresja
            ),
            // Pomoc kolegom z materiałem
            new Event(
                "Pomoc kolegom z materiałem",
                "Dajesz wsparcie i cierpliwie tłumaczysz",
                "Odrzucasz i krępujesz",
                new int[]{5, 0, 0, 0, 0, -4, 0, 0, 6, 0}, // +empatia, -egocentryzm, +wspolpraca
                new int[]{-3, 0, 0, 0, 0, 7, 0, 0, 0, 0}   // -empatia, +egocentryzm
            ),
            new Event("Zabrakło ci 2 punkty na egzaminie", "Idziesz na konsultacje i próbujesz poprawić wynik", 
            "Obrażasz się i odreagowujesz na wykładowcy", 
            new int[]{0, 0, 4, 0, 5, 0, 3, 0, 0, 0}, // +asertywność, +samowiaromość, +współpraca
            new int[]{0, 0, 0, 0, -3, 6, -2, 0, 0, 0}) // -samowiaromość, +agresja, -współpraca
        ));
    }
}
