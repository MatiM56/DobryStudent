package model;

/**
 * Klasa reprezentująca studenta w symulacji.
 * Przechowuje stan cech psychicznych studenta oraz metody do ich modyfikacji.
 */
public class Student {
    /** Poziom empatii studenta (0-100). */
    private int empatia = 50;
    /** Poziom nieczułości studenta (0-100). */
    private int nieczulosc = 50;
    /** Poziom asertywności studenta (0-100). */
    private int asertywnosc = 50;
    /** Poziom uległości studenta (0-100). */
    private int uleglosc = 50;
    /** Poziom samoświadomości studenta (0-100). */
    private int samowiadomosc = 50;
    /** Poziom egocentryzmu studenta (0-100). */
    private int egocentryzm = 50;
    /** Poziom samoregulacji studenta (0-100). */
    private int samoregulacja = 50;
    /** Poziom impulsywności studenta (0-100). */
    private int impulsywnosc = 50;
    /** Poziom umiejętności współpracy studenta (0-100). */
    private int umiejetnoscWspolpracy = 50;
    /** Poziom agresji studenta (0-100). */
    private int agresja = 50;

    /**
     * Resetuje wszystkie cechy studenta do wartości początkowej (50).
     * Używane przy rozpoczynaniu nowej gry.
     */
    public void reset() {
        empatia = 50; nieczulosc = 50; asertywnosc = 50; uleglosc = 50;
        samowiadomosc = 50; egocentryzm = 50; samoregulacja = 50;
        impulsywnosc = 50; umiejetnoscWspolpracy = 50; agresja = 50;
    }

    public void setEmpatia(int v) { empatia = Math.max(0, Math.min(100, v)); }
    public void setNieczulosc(int v) { nieczulosc = Math.max(0, Math.min(100, v)); }
    public void setAsertywnosc(int v) { asertywnosc = Math.max(0, Math.min(100, v)); }
    public void setUleglosc(int v) { uleglosc = Math.max(0, Math.min(100, v)); }
    public void setSamowiadomosc(int v) { samowiadomosc = Math.max(0, Math.min(100, v)); }
    public void setEgocentryzm(int v) { egocentryzm = Math.max(0, Math.min(100, v)); }
    public void setSamoregulacja(int v) { samoregulacja = Math.max(0, Math.min(100, v)); }
    public void setImpulsywnosc(int v) { impulsywnosc = Math.max(0, Math.min(100, v)); }
    public void setUmiejetnoscWspolpracy(int v) { umiejetnoscWspolpracy = Math.max(0, Math.min(100, v)); }
    public void setAgresja(int v) { agresja = Math.max(0, Math.min(100, v)); }

    // Metody dla nowych cech
    public void changeEmpatia(int value) {
        empatia = Math.max(0, Math.min(100, empatia + value));
    }

    public void changeNieczulosc(int value) {
        nieczulosc = Math.max(0, Math.min(100, nieczulosc + value));
    }

    public void changeAsertywnosc(int value) {
        asertywnosc = Math.max(0, Math.min(100, asertywnosc + value));
    }

    public void changeUleglosc(int value) {
        uleglosc = Math.max(0, Math.min(100, uleglosc + value));
    }

    public void changeSamowiadomosc(int value) {
        samowiadomosc = Math.max(0, Math.min(100, samowiadomosc + value));
    }

    public void changeEgocentryzm(int value) {
        egocentryzm = Math.max(0, Math.min(100, egocentryzm + value));
    }

    public void changeSamoregulacja(int value) {
        samoregulacja = Math.max(0, Math.min(100, samoregulacja + value));
    }

    public void changeImpulsywnosc(int value) {
        impulsywnosc = Math.max(0, Math.min(100, impulsywnosc + value));
    }

    public void changeUmiejetnoscWspolpracy(int value) {
        umiejetnoscWspolpracy = Math.max(0, Math.min(100, umiejetnoscWspolpracy + value));
    }

    public void changeAgresja(int value) {
        agresja = Math.max(0, Math.min(100, agresja + value));
    }

    public String getCechyStatus() {
        return String.format(
            "Empatia: %d | Asertywność: %d | Samoświadomość: %d | Samoregulacja: %d | Współpraca: %d\nNieczułość: %d | Uległość: %d | Egocentryzm: %d | Impulsywność: %d | Agresja: %d",
            empatia, asertywnosc, samowiadomosc, samoregulacja, umiejetnoscWspolpracy,
            nieczulosc, uleglosc, egocentryzm, impulsywnosc, agresja
        );
    }

    public String getCechyPozytywneStatus() {
        return String.format(
            "Empatia: %d | Asertywność: %d | Samoświadomość: %d | Samoregulacja: %d | Współpraca: %d",
            empatia, asertywnosc, samowiadomosc, samoregulacja, umiejetnoscWspolpracy
        );
    }

    public String getCechyNegatywneStatus() {
        return String.format(
            "Nieczułość: %d | Uległość: %d | Egocentryzm: %d | Impulsywność: %d | Agresja: %d",
            nieczulosc, uleglosc, egocentryzm, impulsywnosc, agresja
        );
    }

    // Gettery dla cech
    public int getEmpatia() {
        return empatia;
    }

    public int getNieczulosc() {
        return nieczulosc;
    }

    public int getAsertywnosc() {
        return asertywnosc;
    }

    public int getUleglosc() {
        return uleglosc;
    }

    public int getSamowiadomosc() {
        return samowiadomosc;
    }

    public int getEgocentryzm() {
        return egocentryzm;
    }

    public int getSamoregulacja() {
        return samoregulacja;
    }

    public int getImpulsywnosc() {
        return impulsywnosc;
    }

    public int getUmiejetnoscWspolpracy() {
        return umiejetnoscWspolpracy;
    }

    public int getAgresja() {
        return agresja;
    }
}
