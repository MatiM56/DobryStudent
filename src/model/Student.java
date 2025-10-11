package model;

public class Student {
    // Nowe parametry cech
    private int empatia = 50;
    private int nieczulosc = 50;
    private int asertywnosc = 50;
    private int uleglosc = 50;
    private int samowiadomosc = 50;
    private int egocentryzm = 50;
    private int samoregulacja = 50;
    private int impulsywnosc = 50;
    private int umiejetnoscWspolpracy = 50;
    private int agresja = 50;

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
}
