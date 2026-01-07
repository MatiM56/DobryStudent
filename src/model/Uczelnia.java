package model;

import java.util.Arrays;

public class Uczelnia extends Event.ActivityType {
    public Uczelnia() {
        super("Uczelnia", Arrays.asList(
            // Dyskusja na wykładzie
            new Event(
                "Dyskusja na wykładzie",
                "Wnosisz konstruktywne uwagi",
                "Przerywasz i lekceważysz innych",
                new int[]{0, 0, 1, 0, 1, 0, 0, 0, 0, -1}, // +asertywnosc, +samowiadomosc, -agresja
                new int[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 1}   // +nieczulosc, +agresja
            ),
            // Pomoc kolegom z materiałem
            new Event(
                "Pomoc kolegom z materiałem",
                "Dajesz wsparcie i cierpliwie tłumaczysz",
                "Odrzucasz i krępujesz",
                new int[]{1, 0, 0, 0, 0, -1, 0, 0, 1, 0}, // +empatia, -egocentryzm, +wspolpraca
                new int[]{-1, 0, 0, 0, 0, 1, 0, 0, 0, 0}   // -empatia, +egocentryzm
            ),
            new Event("Zabrakło ci 2 punkty na egzaminie", "Idziesz na konsultacje i próbujesz poprawić wynik", 
            "Obrażasz się i odreagowujesz na wykładowcy", null, null)
        ));
    }
}
