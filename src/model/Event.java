package model;

import java.util.List;
import java.util.Random;

/**
 * Klasa reprezentująca pojedyncze zdarzenie (event) w grze.
 * Przechowuje opis sytuacji, możliwe wybory gracza (pozytywny/negatywny)
 * oraz skutki tych wyborów wpływające na cechy studenta.
 */
public class Event {
    /** Opis sytuacji/zdarzenia. */
    private final String situation;
    /** Tekst wyboru pozytywnego. */
    private final String positiveChoice;
    /** Tekst wyboru negatywnego. */
    private final String negativeChoice;
    /** Tablica zmian cech dla wyboru pozytywnego. */
    private final int[] positiveEffects; // tablica zmian cech dla wyboru pozytywnego
    /** Tablica zmian cech dla wyboru negatywnego. */
    private final int[] negativeEffects; // tablica zmian cech dla wyboru negatywnego
    private final Random random = new Random();

    // Indeksy dla efektów (odpowiadają kolejności cech w Student.java)
    /** Indeks cechy Empatia w tablicy efektów. */
    public static final int EMPATIA = 0;
    /** Indeks cechy Nieczułość w tablicy efektów. */
    public static final int NIECZULOSC = 1;
    /** Indeks cechy Asertywność w tablicy efektów. */
    public static final int ASERTYWNOSC = 2;
    /** Indeks cechy Uległość w tablicy efektów. */
    public static final int ULEGLOSC = 3;
    /** Indeks cechy Samoświadomość w tablicy efektów. */
    public static final int SAMOWIADOMOSC = 4;
    /** Indeks cechy Egocentryzm w tablicy efektów. */
    public static final int EGOCENTRYZM = 5;
    /** Indeks cechy Samoregulacja w tablicy efektów. */
    public static final int SAMOREGULACJA = 6;
    /** Indeks cechy Impulsywność w tablicy efektów. */
    public static final int IMPULSYWNOSC = 7;
    /** Indeks cechy Współpraca w tablicy efektów. */
    public static final int WSPOLPRACA = 8;
    /** Indeks cechy Agresja w tablicy efektów. */
    public static final int AGRESJA = 9;

    /**
     * Tworzy nowe zdarzenie.
     * 
     * @param situation Opis sytuacji
     * @param positiveChoice Tekst wyboru pozytywnego
     * @param negativeChoice Tekst wyboru negatywnego
     * @param positiveEffects Tablica zmian cech dla wyboru pozytywnego
     * @param negativeEffects Tablica zmian cech dla wyboru negatywnego
     */
    public Event(String situation, String positiveChoice, String negativeChoice,
                 int[] positiveEffects, int[] negativeEffects) {
        this.situation = situation;
        this.positiveChoice = positiveChoice;
        this.negativeChoice = negativeChoice;
        this.positiveEffects = positiveEffects;
        this.negativeEffects = negativeEffects;
    }

    /**
     * Zwraca opis sytuacji.
     * @return Opis sytuacji
     */
    public String getSituation() { return situation; }
    
    /**
     * Zwraca tekst wyboru pozytywnego.
     * @return Tekst wyboru
     */
    public String getPositiveChoice() { return positiveChoice; }
    
    /**
     * Zwraca tekst wyboru negatywnego.
     * @return Tekst wyboru
     */
    public String getNegativeChoice() { return negativeChoice; }
    
    /**
     * Zwraca tablicę efektów dla wyboru pozytywnego.
     * @return Tablica zmian cech
     */
    public int[] getPositiveEffects() { return positiveEffects; }
    
    /**
     * Zwraca tablicę efektów dla wyboru negatywnego.
     * @return Tablica zmian cech
     */
    public int[] getNegativeEffects() { return negativeEffects; }

    /**
     * Zastosuj pozytywny wybór z mechaniką "Testu Osobowości".
     * Jeśli gracz ma bardzo negatywne cechy (np. wysoką agresję), może mu się nie udać zachować pozytywnie.
     * @return Komunikat o wyniku akcji
     */
    public String applyPositiveChoice(Student student) {
        // MECHANIKA ODMOWY: Sprawdź czy student ma siłę psychiczną
        
        // 1. Zbyt niska empatia (<30) - blokuje zachowania prospołeczne (wymagające empatii/współpracy w efektach)
        // Sprawdzamy czy ten wybór premiuje empatię (indeks 0) lub współpracę (indeks 8)
        if (student.getEmpatia() < 30 && (positiveEffects[EMPATIA] > 0 || positiveEffects[WSPOLPRACA] > 0)) {
            return "ODMOWA: Jesteś zbyt wyprany z uczuć, by komukolwiek pomóc. (Empatia < 30)";
        }
        
        // 2. Zbyt wysoki egocentryzm (>70) - blokuje poświęcenie dla innych
        if (student.getEgocentryzm() > 70 && (positiveEffects[EMPATIA] > 0 || positiveEffects[WSPOLPRACA] > 0)) {
            return "ODMOWA: Dlaczego miałbyś to robić? Liczysz się tylko Ty. (Egocentryzm > 70)";
        }
        
        // 3. Zbyt niska samoświadomość (<30) - blokuje mądre decyzje (wymagające samoregulacji/samoświadomości)
        if (student.getSamowiadomosc() < 30 && (positiveEffects[SAMOWIADOMOSC] > 0 || positiveEffects[SAMOREGULACJA] > 0)) {
            applyNegativeChoice(student);
            return "PORAŻKA: Nie rozumiesz co się z Tobą dzieje. Robisz głupstwo mimo chęci. (Samoświadomość < 30)";
        }

        // Jeśli Agresja + Impulsywność > 150 (max 200), jest 30% szans na "wybuch"
        if (student.getAgresja() + student.getImpulsywnosc() > 150) {
            if (random.nextDouble() < 0.30) {
                // Porażka samokontroli!
                applyNegativeChoice(student); // Ała! Robimy negatywne skutki mimo dobrych chęci
                return "PRZEGRANA WALKA Z SOBĄ! Chciałeś dobrze, ale Twoja impulsywność wygrała.\nZrobiłeś awanturę zamiast tego.";
            }
        }

        applyEffects(student, positiveEffects);
        return "Udało się zachować zgodnie z planem.";
    }

    /**
     * Zastosuj negatywny wybór.
     * @return Komunikat o wyniku akcji
     */
    public String applyNegativeChoice(Student student) {
        applyEffects(student, negativeEffects);
        return "Postąpiłeś zgodnie ze swoją mroczną stroną.";
    }

    private void applyEffects(Student student, int[] effects) {
        student.changeEmpatia(effects[EMPATIA]);
        student.changeNieczulosc(effects[NIECZULOSC]);
        student.changeAsertywnosc(effects[ASERTYWNOSC]);
        student.changeUleglosc(effects[ULEGLOSC]);
        student.changeSamowiadomosc(effects[SAMOWIADOMOSC]);
        student.changeEgocentryzm(effects[EGOCENTRYZM]);
        student.changeSamoregulacja(effects[SAMOREGULACJA]);
        student.changeImpulsywnosc(effects[IMPULSYWNOSC]);
        student.changeUmiejetnoscWspolpracy(effects[WSPOLPRACA]);
        student.changeAgresja(effects[AGRESJA]);
    }

    // Klasa bazowa dla wszystkich aktywności - przeniesiona z Aktywnosc.java
    public static abstract class ActivityType {
        protected String nazwa;
        protected List<Event> events;
        protected Random random = new Random();

        public ActivityType(String nazwa, List<Event> events) {
            this.nazwa = nazwa;
            this.events = events;
        }

        public String getNazwa() {
            return nazwa;
        }

        public Event getRandomEvent() {
            return events.get(random.nextInt(events.size()));
        }

        public List<Event> getAllEvents() {
            return events;
        }

        public void wykonaj(Student student) {
            System.out.println("Wykonano aktywność: " + nazwa);
        }
    }
}