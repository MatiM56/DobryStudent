package model;

import java.util.List;
import java.util.Random;

public class Event {
    private String situation;
    private String positiveChoice;
    private String negativeChoice;
    private int[] positiveEffects; // tablica zmian cech dla wyboru pozytywnego
    private int[] negativeEffects; // tablica zmian cech dla wyboru negatywnego

    // Indeksy dla efektów (odpowiadają kolejności cech w Student.java)
    public static final int EMPATIA = 0;
    public static final int NIECZULOSC = 1;
    public static final int ASERTYWNOSC = 2;
    public static final int ULEGLOSC = 3;
    public static final int SAMOWIADOMOSC = 4;
    public static final int EGOCENTRYZM = 5;
    public static final int SAMOREGULACJA = 6;
    public static final int IMPULSYWNOSC = 7;
    public static final int WSPOLPRACA = 8;
    public static final int AGRESJA = 9;

    public Event(String situation, String positiveChoice, String negativeChoice,
                 int[] positiveEffects, int[] negativeEffects) {
        this.situation = situation;
        this.positiveChoice = positiveChoice;
        this.negativeChoice = negativeChoice;
        this.positiveEffects = positiveEffects;
        this.negativeEffects = negativeEffects;
    }

    public String getSituation() { return situation; }
    public String getPositiveChoice() { return positiveChoice; }
    public String getNegativeChoice() { return negativeChoice; }
    public int[] getPositiveEffects() { return positiveEffects; }
    public int[] getNegativeEffects() { return negativeEffects; }

    public void applyPositiveChoice(Student student) {
        student.changeEmpatia(positiveEffects[EMPATIA]);
        student.changeNieczulosc(positiveEffects[NIECZULOSC]);
        student.changeAsertywnosc(positiveEffects[ASERTYWNOSC]);
        student.changeUleglosc(positiveEffects[ULEGLOSC]);
        student.changeSamowiadomosc(positiveEffects[SAMOWIADOMOSC]);
        student.changeEgocentryzm(positiveEffects[EGOCENTRYZM]);
        student.changeSamoregulacja(positiveEffects[SAMOREGULACJA]);
        student.changeImpulsywnosc(positiveEffects[IMPULSYWNOSC]);
        student.changeUmiejetnoscWspolpracy(positiveEffects[WSPOLPRACA]);
        student.changeAgresja(positiveEffects[AGRESJA]);
    }

    public void applyNegativeChoice(Student student) {
        student.changeEmpatia(negativeEffects[EMPATIA]);
        student.changeNieczulosc(negativeEffects[NIECZULOSC]);
        student.changeAsertywnosc(negativeEffects[ASERTYWNOSC]);
        student.changeUleglosc(negativeEffects[ULEGLOSC]);
        student.changeSamowiadomosc(negativeEffects[SAMOWIADOMOSC]);
        student.changeEgocentryzm(negativeEffects[EGOCENTRYZM]);
        student.changeSamoregulacja(negativeEffects[SAMOREGULACJA]);
        student.changeImpulsywnosc(negativeEffects[IMPULSYWNOSC]);
        student.changeUmiejetnoscWspolpracy(negativeEffects[WSPOLPRACA]);
        student.changeAgresja(negativeEffects[AGRESJA]);
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