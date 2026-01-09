package model;

import java.util.Arrays;

/**
 * Klasa reprezentująca aktywności i zdarzenia związane ze sportem i rywalizacją.
 * Zawiera scenariusze meczów, wsparcia drużyny i zachowania fair play.
 */
public class Sport extends Event.ActivityType {
    /**
     * Konstruktor inicjalizujący listę zdarzeń dla aktywności Sport.
     */
    public Sport() {
        super("Sport", Arrays.asList(
            // Wsparcie kolegów
            new Event(
                "Przed wami ciężki mecz",
                "Wspierasz i motywujesz w szatni",
                "Panikujesz, narzekasz i paraliżujesz zespół",
                new int[]{4, 0, 0, 0, 0, -3, 0, 0, 5, 0}, // +empatia, -egocentryzm, +wspolpraca
                new int[]{0, 0, 0, 0, 0, 6, 0, 0, -4, 0}   // +egocentryzm, -wspolpraca
            ),
            // Napięta rywalizacja
            new Event(
                "Gracie derby z lokalnym rywalem",
                "Mimo wszystko pokazujesz przewagę tylko sportową",
                "Prowokujesz, pajacujesz i przekraczasz granice",
                new int[]{0, 0, 5, 0, 0, 0, 4, -3, 0, 0}, // +asertywnosc, +samoregulacja, -impulsywnosc
                new int[]{0, 0, 0, 0, 0, 0, 0, 6, 0, 7}   // +agresja, +impulsywnosc
            )
        ));
    }
}
