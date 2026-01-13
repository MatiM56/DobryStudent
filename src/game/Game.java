package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.BusActivity;
import model.Event;
import model.Event.ActivityType;
import model.Impreza;
import model.Nauka;
import model.Praca;
import model.Sklep;
import model.Spanie;
import model.Sport;
import model.Student;
import model.Uczelnia;

/**
 * Główna klasa aplikacji gry "Symulator Studenta".
 * Odpowiada za inicjalizację interfejsu graficznego (JavaFX),
 * zarządzanie stanem gry oraz obsługę zdarzeń użytkownika.
 */
public class Game extends Application {

    /** Obiekt reprezentujący studenta i jego cechy. */
    private final Student student = new Student();
    
    /** Etykieta wyświetlająca pasek cech pozytywnych. */
    private final Label pasekCechPozytywnych = new Label();
    
    /** Etykieta wyświetlająca pasek cech negatywnych. */
    private final Label pasekCechNegatywnych = new Label();
    
    /** Etykieta wyświetlająca instrukcje sterowania. */
    private final Label sterowanie = new Label();
    
    /** Komponent wyświetlający obraz graficzny studenta. */
    private ImageView obrazGracza = new ImageView(new Image(Game.class.getResource("/resources/student.png").toExternalForm()));
    
    /** Aktualnie trwające zdarzenie w grze. */
    private Event currentEvent = null;
    /** Okno dialogowe wyświetlające opcje zdarzenia. */
    private Alert eventDialog = null;
    /** Główny panel mapy, na którym rysowane są elementy gry. */
    private Pane panelMapy;
    /** Zbiór aktualnie wciśniętych klawiszy, służący do sterowania ruchem. */
    private final Set<KeyCode> wcisnieteKlawisze = new HashSet<>();
    /** Flaga określająca, czy gracz jest w trakcie ruchu. */
    private boolean czyRuszaSie = false;
    
    /** Mapa przechowująca czasy odnowienia dla poszczególnych aktywności. */
    private final java.util.Map<String, Integer> cooldowns = new java.util.HashMap<>();
    /** Domyślny czas odnowienia aktywności (w krokach/cyklach). */
    private static final int DEFAULT_COOLDOWN = 15; 

    /** Licznik wszystkich wyborów dokonanych przez gracza. */
    private int totalWyborow = 0; 
    /** Generator liczb losowych używany w grze. */
    private final Random random = new Random();
    
    /** Flaga określająca, czy muzyka w tle jest włączona. */
    private boolean muzykaWlaczona = true;
    /** Aktualny poziom głośności muzyki (0.0 - 1.0). */
    private double glosnosc = 0; 
    /** Odtwarzacz multimedialny do obsługi muzyki w tle. */
    private MediaPlayer mediaPlayer; 
    /** Panel menu zawierający przyciski sterujące grą. */
    private HBox panelMenu;
    /** Przycisk rozpoczynający nową grę. */
    private Button przyciskNowaGra;
    /** Przycisk zapisujący stan gry. */
    private Button przyciskZapisz;
    /** Lista rozwijana służąca do wyboru zapisu gry do wczytania. */
    private ComboBox<String> comboBoxWczytaj;
    /** Przycisk włączający/wyłączający muzykę. */
    private Button przyciskMuzyka; 
    /** Suwak do regulacji głośności. */
    private Slider suwakGlosnosci;
    /** Etykieta wyświetlająca liczbę podjętych decyzji. */
    private Label licznikDecyzji; 

    /** Rozmiar pojedynczego kafelka mapy w pikselach. */
    private final int rozmiarKafelka = 50;
    
    /** Pozycja X gracza na siatce mapy. */
    private int graczX = 5;
    /** Pozycja Y gracza na siatce mapy. */
    private int graczY = 5;
     
    
    /** Kolumna, w której znajduje się ściana (element mapy). */
    private final int scianaKolumna = 25;
    /** Rząd, w którym znajduje się ściana (element mapy). */
    private final int scianaRzad = 9;
    
    /** Tablica definiująca obszar łóżka na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresLozka = {8, 6, 10, 8};
    /** Tablica definiująca obszar biblioteki na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresBiblioteki = {20, 2, 23, 4};
    /** Tablica definiująca obszar Politechniki Wrocławskiej na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresKwadratowej = {19, 0, 24, 1};
    /** Tablica definiująca obszar miejsca pracy na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresPracy = {13, 6, 17, 8};
    /** Tablica definiująca obszar uczelni na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresUczelni = {0, 0, 6, 2};
    /** Tablica definiująca obszar sklepu (Biedronki) na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresBiedronki = {11, 0, 14, 0};
    /** Tablica definiująca obszar centrum sportowego (CSA) na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresCSA = {19, 5, 24, 8};
    /** Tablica definiująca obszar przystanku autobusowego na mapie [minX, minY, maxX, maxY]. */
    private final int[] zakresAutobusu = {0,6 , 4, 7};
    /**
     * Główna metoda startowa aplikacji JavaFX.
     * Inicjalizuje okno, scenę, komponenty graficzne, mapę oraz obsługę zdarzeń.
     *
     * @param stage Główny kontener (okno) aplikacji.
     */
    @Override
    public void start(Stage stage) {
        System.out.println("Starting game...");
        stage.setTitle("Dobry Student");
        
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(false);
        System.out.println("Stage configured");
        
        javafx.stage.Screen ekran = javafx.stage.Screen.getPrimary();
        javafx.geometry.Rectangle2D granice = ekran.getVisualBounds();
        double szerokoscEkranu = granice.getWidth();
        double wysokoscEkranu = granice.getHeight();

        panelMapy = new Pane();
        panelMapy.setPrefSize(szerokoscEkranu, wysokoscEkranu);
        
        /* ... istniejący kod ... */

        ImageView tloMapy;
        try {
            Image obrazMapy = new Image(getClass().getResourceAsStream("/resources/mapa.png"));
            tloMapy = new ImageView(obrazMapy);
            
            tloMapy.fitWidthProperty().bind(panelMapy.widthProperty());
            tloMapy.fitHeightProperty().bind(panelMapy.heightProperty());
            tloMapy.setX(0);
            tloMapy.setY(0);
            
            panelMapy.getChildren().add(tloMapy);
        } catch (Exception e) {
            System.out.println("Błąd ładowania mapy: " + e.getMessage());
        }
        try {
            Image zdjecieStudenta = new Image(getClass().getResourceAsStream("/resources/student.png"));
            
            obrazGracza = new ImageView(zdjecieStudenta);
            obrazGracza.setFitWidth(1.5 * rozmiarKafelka);
            obrazGracza.setFitHeight(2 * rozmiarKafelka);
            
            ustawPozycjeGracza();
        } catch (Exception e) {
            System.out.println("Nie można załadować obrazu gracza: " + e.getMessage());
            obrazGracza = new ImageView();
        }

        panelMapy.getChildren().add(obrazGracza);
        obrazGracza.toFront();

        panelMenu = new HBox(15); 
        panelMenu.setStyle("-fx-alignment: center-left; -fx-background-color: rgba(0,0,0,0.8); -fx-background-radius: 5;");


        przyciskNowaGra = new Button("Nowa gra");
        przyciskNowaGra.setStyle("-fx-font-size: 11px; -fx-min-width: 100px;");
        przyciskNowaGra.setOnAction(e -> nowaGra());

        przyciskZapisz = new Button("Zapisz grę");
        przyciskZapisz.setStyle("-fx-font-size: 11px; -fx-min-width: 100px;");
        przyciskZapisz.setOnAction(e -> zapiszGre());

        comboBoxWczytaj = new ComboBox<>();
        comboBoxWczytaj.setPromptText("Wybierz zapis");
        comboBoxWczytaj.setStyle("-fx-font-size: 11px; -fx-min-width: 140px;");
        comboBoxWczytaj.setOnAction(e -> wczytajGre());
        odswiezListeZapisow();

        przyciskMuzyka = new Button("🔊");
        przyciskMuzyka.setStyle("-fx-font-size: 12px; -fx-min-width: 30px;");
        przyciskMuzyka.setOnAction(e -> przelaczMuzyke());

        suwakGlosnosci = new Slider(0, 100, glosnosc);
        suwakGlosnosci.setShowTickLabels(true);
        suwakGlosnosci.setShowTickMarks(true);
        suwakGlosnosci.setMajorTickUnit(25);
        suwakGlosnosci.setBlockIncrement(10);
        suwakGlosnosci.setStyle("-fx-font-size: 11px; -fx-min-width: 40px; -fx-text-fill: white;");

        suwakGlosnosci.valueProperty().addListener((obs, oldVal, newVal) -> {
            glosnosc = newVal.doubleValue() / 100.0; 
            aktualizujGlosnosc();
        });

        licznikDecyzji = new Label("Decyzje: 0");
        licznikDecyzji.setStyle("-fx-font-size: 11px; -fx-text-fill: white;");

        panelMenu.getChildren().addAll(przyciskNowaGra, przyciskZapisz, comboBoxWczytaj, przyciskMuzyka, suwakGlosnosci);

        panelMapy.getChildren().remove(panelMenu);

        panelMenu.setLayoutX(20); 
        panelMenu.setLayoutY(0); 

        inicjalizujMuzyke();

        inicjalizujKomponentyEventow();

        pasekCechPozytywnych.setText(student.getCechyPozytywneStatus());
        pasekCechPozytywnych.setStyle("-fx-padding: 10; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: white;");
        pasekCechPozytywnych.setWrapText(true);

        pasekCechNegatywnych.setText(student.getCechyNegatywneStatus());
        pasekCechNegatywnych.setStyle("-fx-padding: 10; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: white;");
        pasekCechNegatywnych.setWrapText(true);

        sterowanie.setText("WASD - Ruch (4 kierunki) | E - Interakcja");
        sterowanie.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
        
        VBox prawaStrona = new VBox(5, pasekCechPozytywnych, pasekCechNegatywnych, sterowanie);
        prawaStrona.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(prawaStrona, Priority.ALWAYS);

        VBox menuPanel = new VBox(10, panelMenu, prawaStrona);
        menuPanel.setStyle("-fx-alignment: center-left; -fx-background-color: rgba(0,0,0,0.5);");

        VBox uklad = new VBox(10, panelMapy, menuPanel, licznikDecyzji);
        uklad.setStyle("-fx-alignment: center-left; -fx-background-color: #1a1a1a;");

        Scene widok = new Scene(uklad);

        widok.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F1) {
                pokazEventPomocy();
                return;
            }
            wcisnieteKlawisze.add(event.getCode());
            
            if (!czyRuszaSie) {
                obsluzRuch();
            }

            if (event.getCode() == KeyCode.E) {
                obsluzInterakcje();
            }
        });

        widok.setOnKeyReleased(event -> {
            wcisnieteKlawisze.remove(event.getCode());
        });

        stage.setScene(widok);
        stage.show();
        panelMapy.requestFocus(); 
    }

    /**
     * Obsługuje logikę ruchu gracza w oparciu o wciśnięte klawisze.
     * Aktualizuje pozycję gracza, jeśli ruch jest dozwolony, oraz zmniejsza cooldowny aktywności.
     */
    private void obsluzRuch() {
        int nowyX = graczX;
        int nowyY = graczY;
        
        boolean ruchGora = wcisnieteKlawisze.contains(KeyCode.W);
        boolean ruchDol = wcisnieteKlawisze.contains(KeyCode.S);
        boolean ruchLewo = wcisnieteKlawisze.contains(KeyCode.A);
        boolean ruchPrawo = wcisnieteKlawisze.contains(KeyCode.D);

        if (ruchGora) {
            nowyY -= 1;  
        } else if (ruchDol) {
            nowyY += 1;  
        } else if (ruchLewo) {
            nowyX -= 1;  
        } else if (ruchPrawo) {
            nowyX += 1;
        }

        if ((nowyX != graczX || nowyY != graczY) && czyPozycjaPoprawna(nowyX, nowyY)) {
            graczX = nowyX;
            graczY = nowyY;
            
            ustawPozycjeGracza();
            
            cooldowns.entrySet().forEach(entry -> {
                if (entry.getValue() > 0) {
                    entry.setValue(entry.getValue() - 1);
                }
            });
            
            czyRuszaSie = false;
        }
    }

    /**
     * Sprawdza, czy podana pozycja mieści się w granicach mapy i nie jest przeszkodą.
     *
     * @param kafelekX Współrzędna X kafelka.
     * @param kafelekY Współrzędna Y kafelka.
     * @return true, jeśli pozycja jest poprawna, false w przeciwnym razie.
     */
    private boolean czyPozycjaPoprawna(int kafelekX, int kafelekY) {
        if (kafelekX < 0 || kafelekY < 0) {
            return false;
        }
        
        if (kafelekX >= scianaKolumna) {
            return false;
        }
        
        if (kafelekY >= scianaRzad) {
            return false;
        }
        
        return true;
    }

    /**
     * Obsługuje interakcję gracza z otoczeniem (klawisz E).
     * Sprawdza, czy gracz znajduje się w strefie aktywności i jeśli tak, uruchamia odpowiednie zdarzenie.
     */
    private void obsluzInterakcje() {
        Event.ActivityType aktywnosc = null;
        if (czyWmiejscuAktywnosci(zakresLozka)) {
            aktywnosc = new Spanie();
        } else if (czyWmiejscuAktywnosci(zakresBiblioteki)) {
            aktywnosc = new Nauka();
        } else if (czyWmiejscuAktywnosci(zakresKwadratowej)) {
            aktywnosc = new Impreza();
        } else if (czyWmiejscuAktywnosci(zakresPracy)) {
            aktywnosc = new Praca();
        } else if (czyWmiejscuAktywnosci(zakresUczelni)) {
            aktywnosc = new Uczelnia();
        } else if (czyWmiejscuAktywnosci(zakresBiedronki)) {
            aktywnosc = new Sklep();
        } else if (czyWmiejscuAktywnosci(zakresCSA)) {
            aktywnosc = new Sport();
        } else if (czyWmiejscuAktywnosci(zakresAutobusu)) {
            aktywnosc = new BusActivity();
        }

        if (aktywnosc != null) {
            int currentCooldown = cooldowns.getOrDefault(aktywnosc.getNazwa(), 0);
            if (currentCooldown > 0) {
                pasekCechPozytywnych.setText("Musisz ochłonąć! Wróć tu za " + currentCooldown + " kroków.");
                return;
            }

            cooldowns.put(aktywnosc.getNazwa(), DEFAULT_COOLDOWN);

            wykonajAkcje(aktywnosc, "Wykonujesz aktywność: " + aktywnosc.getNazwa());

            pokazEvent(aktywnosc.getRandomEvent());
        } else {
            pasekCechPozytywnych.setText("Nic tu nie ma do interakcji!");
        }
    }

    /**
     * Aktualizuje graficzną pozycję gracza na ekranie na podstawie współrzędnych siatki.
     */
    private void ustawPozycjeGracza() {
        obrazGracza.setX(graczX * rozmiarKafelka);
        obrazGracza.setY(graczY * rozmiarKafelka);
        obrazGracza.toFront(); 
    }

    /**
     * Sprawdza, czy gracz znajduje się w określonym obszarze aktywności.
     *
     * @param obszar Tablica 4-elementowa definiująca prostokąt [xStart, yStart, xEnd, yEnd].
     * @return true, jeśli gracz znajduje się wewnątrz obszaru.
     */
    private boolean czyWmiejscuAktywnosci(int[] obszar) {
        int xStart = obszar[0];
        int yStart = obszar[1];
        int xEnd = obszar[2];
        int yEnd = obszar[3];
        
        return graczX >= xStart && graczX <= xEnd &&
               graczY >= yStart && graczY <= yEnd;
    }

    /**
     * Wykonuje logikę danej aktywności na obiekcie studenta i aktualizuje interfejs.
     *
     * @param aktywnosc Obiekt aktywności do wykonania.
     * @param tekstAkcji Tekst do wyświetlenia w pasku statusu.
     */
    private void wykonajAkcje(ActivityType aktywnosc, String tekstAkcji) {
        pasekCechPozytywnych.setText(tekstAkcji);
        aktywnosc.wykonaj(student);
        
        javafx.application.Platform.runLater(() -> {
            pasekCechPozytywnych.setText(student.getCechyPozytywneStatus());
            pasekCechNegatywnych.setText(student.getCechyNegatywneStatus());
        });
    }


    /**
     * Rozpoczyna nową grę. Resetuje pozycję gracza, statystyki studenta oraz licznik decyzji.
     */
    private void nowaGra() {
        graczX = 5;
        graczY = 5;
        ustawPozycjeGracza();
        
        student.reset();
        
        totalWyborow = 0;
        licznikDecyzji.setText("Decyzje: 0");
        
        pasekCechPozytywnych.setText("Rozpoczęto nową grę! Stan studenta zresetowany.");
        System.out.println("Rozpoczęto nową grę");
    }

    /**
     * Szuka pierwszego wolnego slotu zapisu (1-5) i zapisuje grę.
     * Jeśli wszystkie sloty są zajęte, nadpisuje slot nr 1.
     */
    private void zapiszGre() {
        for (int i = 1; i <= 5; i++) {
            String nazwaPliku = "save" + i + ".txt";
            if (!new File(nazwaPliku).exists()) {
                zapiszDoSlotu(i);
                odswiezListeZapisow();
                return;
            }
        }
        zapiszDoSlotu(1);
        odswiezListeZapisow();
    }

    /**
     * Zapisuje stan gry do pliku tekstowego odpowiadającego podanemu slotowi.
     * Zapisywane dane to: pozycja, ustawienia dźwięku, liczba wyborów oraz cechy studenta.
     * 
     * @param slot Numer slotu zapisu (zwykle 1-5).
     */
    private void zapiszDoSlotu(int slot) {
        String nazwaPliku = "save" + slot + ".txt";
        try (FileWriter writer = new FileWriter(nazwaPliku)) {
            String cechy = String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",
                student.getEmpatia(), student.getNieczulosc(),
                student.getAsertywnosc(), student.getUleglosc(),
                student.getSamowiadomosc(), student.getEgocentryzm(),
                student.getSamoregulacja(), student.getImpulsywnosc(),
                student.getUmiejetnoscWspolpracy(), student.getAgresja());
            
            writer.write(graczX + "," + graczY + "," + glosnosc + "," + muzykaWlaczona + "," + totalWyborow + "," + cechy);
            pasekCechPozytywnych.setText("Gra została zapisana w slocie " + slot + "!");
            System.out.println("Gra została zapisana do " + nazwaPliku);
            odswiezListeZapisow(); 
        } catch (IOException e) {
            pasekCechPozytywnych.setText("Błąd podczas zapisywania gry!");
            System.out.println("Błąd zapisu: " + e.getMessage());
        }
    }

    /**
     * Obsługuje proces wczytywania gry wybrany z listy rozwijanej (ComboBox).
     * Parsuje wybrany element UI, aby wydobyć numer slotu.
     */
    private void wczytajGre() {
        String wybranySlot = comboBoxWczytaj.getValue();
        if (wybranySlot == null || wybranySlot.isEmpty()) {
            pasekCechPozytywnych.setText("Wybierz slot zapisu do wczytania!");
            return;
        }

        int slot = -1;
        try {
            if (wybranySlot.startsWith("Slot ")) {
                slot = Integer.parseInt(wybranySlot.substring(5, 6));
            }
        } catch (NumberFormatException e) {
            pasekCechPozytywnych.setText("Błąd: nieprawidłowy format slotu!");
            return;
        }

        if (slot < 1 || slot > 5) {
            pasekCechPozytywnych.setText("Błąd: nieprawidłowy numer slotu!");
            return;
        }

        wczytajZeSlotu(slot);
    }

    /**
     * Odczytuje stan gry z pliku i przywraca stan aplikacji.
     * Wczytuje pozycję, ustawienia, statystyki oraz cechy studenta.
     * 
     * @param slot Numer slotu do wczytania.
     */
    private void wczytajZeSlotu(int slot) {
        String nazwaPliku = "save" + slot + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(nazwaPliku))) {
            String line = br.readLine();
            
            String[] data = line.split(",");
            graczX = Integer.parseInt(data[0]);
            graczY = Integer.parseInt(data[1]);
            glosnosc = Double.parseDouble(data[2]);
            muzykaWlaczona = Boolean.parseBoolean(data[3]);
            if (data.length > 4) {
                totalWyborow = Integer.parseInt(data[4]);
            } else {
                totalWyborow = 0; 
            }
            
            if (data.length >= 15) {
                student.setEmpatia(Integer.parseInt(data[5]));
                student.setNieczulosc(Integer.parseInt(data[6]));
                student.setAsertywnosc(Integer.parseInt(data[7]));
                student.setUleglosc(Integer.parseInt(data[8]));
                student.setSamowiadomosc(Integer.parseInt(data[9]));
                student.setEgocentryzm(Integer.parseInt(data[10]));
                student.setSamoregulacja(Integer.parseInt(data[11]));
                student.setImpulsywnosc(Integer.parseInt(data[12]));
                student.setUmiejetnoscWspolpracy(Integer.parseInt(data[13]));
                student.setAgresja(Integer.parseInt(data[14]));
            } else {
            }
            
            ustawPozycjeGracza();
            suwakGlosnosci.setValue(glosnosc);
            aktualizujGlosnosc();
            licznikDecyzji.setText("Decyzje: " + totalWyborow); 
            
            pasekCechPozytywnych.setText("Gra została wczytana ze slotu " + slot + "!");
            System.out.println("Gra została wczytana z " + nazwaPliku);
        } catch (IOException | NumberFormatException e) {
            pasekCechPozytywnych.setText("Błąd podczas wczytywania gry ze slotu " + slot + "!");
            System.out.println("Błąd wczytywania: " + e.getMessage());
        }
    }

    /**
     * Ustawia głośność odtwarzacza muzyki na podstawie wartości zmiennej glosnosc.
     */
    private void aktualizujGlosnosc() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(glosnosc);
        }
    }

    /**
     * Aktualizuje listę dostępnych zapisów w ComboBoxie.
     * Sprawdza fizyczną obecność plików save1.txt - save5.txt.
     */
    private void odswiezListeZapisow() {
        comboBoxWczytaj.getItems().clear();
        for (int i = 1; i <= 5; i++) { 
            String nazwaPliku = "save" + i + ".txt";
            if (new File(nazwaPliku).exists()) {
                comboBoxWczytaj.getItems().add("Slot " + i + " (zapisany)");
            } else {
                comboBoxWczytaj.getItems().add("Slot " + i + " (pusty)");
            }
        }
    }

    /**
     * Inicjalizuje odtwarzacz muzyki, ładując plik resources/background_music.mp3.
     */
    private void inicjalizujMuzyke() {
        try {
            String muzykaPath = getClass().getResource("/resources/background_music.mp3").toString();
            Media muzyka = new Media(muzykaPath);
            mediaPlayer = new MediaPlayer(muzyka);
            
            mediaPlayer.setVolume(glosnosc);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); 
            
            if (muzykaWlaczona) {
                mediaPlayer.play();
                przyciskMuzyka.setText("🔊");
            } else {
                przyciskMuzyka.setText("🔇");
            }
            
        } catch (Exception e) {
            przyciskMuzyka.setText("Muzyka niedostępna");
            przyciskMuzyka.setDisable(true);
        }
    }

    /**
     * Przełącza stan odtwarzania muzyki (włącz/wyłącz) i aktualizuje ikonę przycisku.
     */
    private void przelaczMuzyke() {
        if (mediaPlayer != null) {
            if (muzykaWlaczona) {
                mediaPlayer.pause();
                przyciskMuzyka.setText("🔇");
                muzykaWlaczona = false;
            } else {
                mediaPlayer.play();
                przyciskMuzyka.setText("🔊");
                muzykaWlaczona = true;
            }
        } else {
            pasekCechPozytywnych.setText("Muzyka nie jest dostępna - dodaj plik background_music.mp3 do src/resources!");
        }
        aktualizujGlosnosc();
    }

    /**
     * Metoda placeholder do inicjalizacji komponentów zdarzeń (obecnie pusta).
     */
    private void inicjalizujKomponentyEventow() {
    }

    /**
     * Wyświetla okno dialogowe z bieżącym wydarzeniem losowym.
     * Umożliwia graczowi wybór jednej z dwóch opcji (pozytywnej lub negatywnej).
     *
     * @param event Obiekt zdarzenia do wyświetlenia.
     */
    private void pokazEvent(Event event) {
        currentEvent = event;

        if (totalWyborow >= 10 && random.nextDouble() < 0.45 &&
            (student.getEmpatia() < 30 || student.getSamowiadomosc() < 30 || student.getUmiejetnoscWspolpracy() < 30 ||
             student.getAsertywnosc() < 30 || student.getAgresja() > 70 || student.getNieczulosc() > 70 || student.getEgocentryzm() > 70)) {
            pokazEventPomocy();
            return; 
        }

        eventDialog = new Alert(Alert.AlertType.NONE);
        eventDialog.setTitle("Sytuacja");
        eventDialog.setHeaderText(event.getSituation());
        eventDialog.setContentText("Wybierz swoją reakcję:");

        ButtonType positiveButton = new ButtonType(event.getPositiveChoice());
        ButtonType negativeButton = new ButtonType(event.getNegativeChoice());

        eventDialog.getButtonTypes().setAll(positiveButton, negativeButton);

        eventDialog.getDialogPane().setStyle("-fx-base: #e0e0e0; -fx-background-color: #f5f5f5; -fx-font-size: 14px;");
        eventDialog.getDialogPane().getStylesheets().clear();

        eventDialog.getDialogPane().setHeaderText(event.getSituation()); 
        if (eventDialog.getDialogPane().lookup(".header-panel") != null) {
            eventDialog.getDialogPane().lookup(".header-panel").setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        }

        eventDialog.showAndWait().ifPresent(response -> {
            String wynikWiadomosc = "";
            if (response == positiveButton) {
                wynikWiadomosc = currentEvent.applyPositiveChoice(student);
            } else if (response == negativeButton) {
                wynikWiadomosc = currentEvent.applyNegativeChoice(student);
            }
            
            Alert feedbackAlert = new Alert(Alert.AlertType.INFORMATION);
            feedbackAlert.setTitle("Wynik decyzji");
            feedbackAlert.setHeaderText(null);
            feedbackAlert.setContentText(wynikWiadomosc);
            feedbackAlert.getDialogPane().setStyle("-fx-font-size: 14px;");
            feedbackAlert.showAndWait();

            sprawdzStrictAlert();

            pasekCechPozytywnych.setText(student.getCechyPozytywneStatus());
            pasekCechNegatywnych.setText(student.getCechyNegatywneStatus());

            totalWyborow++; 
            licznikDecyzji.setText("Decyzje: " + totalWyborow); 

            currentEvent = null;
            eventDialog = null;
        });
    }

    /**
     * Wyświetla specjalne wydarzenie pomocowe, gdy statystyki studenta są na niskim poziomie.
     * Oferuje wybory mające na celu poprawę krytycznych cech.
     */
    private void pokazEventPomocy() {
        if (student.getEmpatia() < 30) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potrzebujesz pomocy!");
            alert.setHeaderText("Czujesz się samotny i potrzebujesz wsparcia przyjaciół.");
            alert.setContentText("Poprosić kolegę o pomoc ze sprawozdaniem?");
            alert.getDialogPane().setStyle("-fx-font-size: 14px;");

            ButtonType tak = new ButtonType("Tak");
            ButtonType nie = new ButtonType("Nie");
            alert.getButtonTypes().setAll(tak, nie);

            alert.showAndWait().ifPresent(response -> {
                if (response == tak) {
                    if (student.getEmpatia() < 30) {
                        Alert konsekwencje = new Alert(Alert.AlertType.INFORMATION);
                        konsekwencje.setTitle("Konsekwencje decyzji");
                        konsekwencje.setHeaderText("Kolega odmawia ci pomocy, bo uważa że jesteś chujem.");
                        konsekwencje.setContentText("Moral: Powinno się pomagać innym, żeby inni pomagali tobie.");
                        konsekwencje.getDialogPane().setStyle("-fx-font-size: 14px;");
                        konsekwencje.showAndWait();

                        student.changeEmpatia(-5);
                        student.changeNieczulosc(3);

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Odrzucona prośba o pomoc");
                        info.setContentText("Konsekwencje: Kolega odmówił, twoje relacje osłabły. Moral: Pracuj nad empatią i wzajemnością.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    } else {
                        student.changeUmiejetnoscWspolpracy(3);
                        student.changeEmpatia(2); 
                        student.changeAsertywnosc(1); 

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Kolega chętnie pomaga");
                        info.setContentText("Konsekwencje: Zyskałeś zaufanie i lepszą współpracę. Moral: Otwartość wzmacnia relacje.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    }
                } else {
                    student.changeUmiejetnoscWspolpracy(-2);
                    student.changeSamowiadomosc(-1); 
                    student.changeEgocentryzm(2); 

                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Skutki decyzji");
                    info.setHeaderText("Odmówiłeś prośby o pomoc");
                    info.setContentText("Konsekwencje: Uniknięcie może pogłębić izolację. Moral: Wsparcie buduje relacje.");
                    info.getDialogPane().setStyle("-fx-font-size: 14px;");
                    info.showAndWait();
                }
            });
        } else if (student.getSamowiadomosc() < 30) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potrzebujesz pomocy!");
            alert.setHeaderText("Nie wiesz jak sobie poradzić z sytuacją.");
            alert.setContentText("Poprosić o radę doświadczonego kolegę?");
            alert.getDialogPane().setStyle("-fx-font-size: 14px;");

            ButtonType tak = new ButtonType("Tak");
            ButtonType nie = new ButtonType("Nie");
            alert.getButtonTypes().setAll(tak, nie);

            alert.showAndWait().ifPresent(response -> {
                if (response == tak) {
                    if (student.getSamowiadomosc() < 30) {
                        Alert konsekwencje = new Alert(Alert.AlertType.INFORMATION);
                        konsekwencje.setTitle("Konsekwencje decyzji");
                        konsekwencje.setHeaderText("Kolega daje radę, ale krytykuje twoją naiwność.");
                        konsekwencje.setContentText("Moral: Lepiej być świadomym swoich słabości.");
                        konsekwencje.getDialogPane().setStyle("-fx-font-size: 14px;");
                        konsekwencje.showAndWait();

                        student.changeSamowiadomosc(3);
                        student.changeAsertywnosc(-2);

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Krytyczna rada");
                        info.setContentText("Konsekwencje: Otrzymałeś ostrą, ale pomocną krytykę. Moral: Ucz się na feedbacku.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    } else {
                        student.changeSamowiadomosc(2);
                        student.changeEmpatia(1); 
                        student.changeUmiejetnoscWspolpracy(1); 

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Przyjęta rada");
                        info.setContentText("Konsekwencje: Czujesz się pewniej i lepiej rozumiesz swoje ograniczenia. Moral: Prośba o radę się opłaca.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    }
                } else {
                    student.changeSamowiadomosc(1);
                    student.changeAgresja(2); 
                    student.changeNieczulosc(1); 

                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Skutki decyzji");
                    info.setHeaderText("Odrzucona pomoc");
                    info.setContentText("Konsekwencje: Uniknąłeś konfrontacji, ale straciłeś szansę na rozwój. Moral: Czasem warto zaufać innym.");
                    info.getDialogPane().setStyle("-fx-font-size: 14px;");
                    info.showAndWait();
                }
            });
        } else if (student.getUmiejetnoscWspolpracy() < 30) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potrzebujesz pomocy!");
            alert.setHeaderText("Masz problemy z pracą zespołową.");
            alert.setContentText("Dołączyć do grupy studyjnej?");
            alert.getDialogPane().setStyle("-fx-font-size: 16px;");

            ButtonType tak = new ButtonType("Tak");
            ButtonType nie = new ButtonType("Nie");
            alert.getButtonTypes().setAll(tak, nie);

            alert.showAndWait().ifPresent(response -> {
                if (response == tak) {
                    if (student.getUmiejetnoscWspolpracy() < 30) {
                        Alert konsekwencje = new Alert(Alert.AlertType.INFORMATION);
                        konsekwencje.setTitle("Konsekwencje decyzji");
                        konsekwencje.setHeaderText("Grupa nie chce cię przyjąć, bo jesteś zbyt egoistyczny.");
                        konsekwencje.setContentText("Moral: Współpraca wymaga wzajemności.");
                        konsekwencje.getDialogPane().setStyle("-fx-font-size: 16px;");
                        konsekwencje.showAndWait();

                        student.changeUmiejetnoscWspolpracy(-3);
                        student.changeEgocentryzm(4);

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Odrzuceni przez grupę");
                        info.setContentText("Konsekwencje: Nie dostałeś miejsca w grupie, twoje umiejętności społeczne wymagają pracy. Moral: Współpraca to dwukierunkowa praca.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    } else {
                        student.changeUmiejetnoscWspolpracy(2);
                        student.changeEmpatia(1); 
                        student.changeAsertywnosc(1); 

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Dołączyłeś do grupy");
                        info.setContentText("Konsekwencje: Zyskałeś doświadczenie w pracy zespołowej i nowe kontakty. Moral: Współpraca rozwija umiejętności.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    }
                } else {
                    student.changeUmiejetnoscWspolpracy(1);
                    student.changeEgocentryzm(3); 
                    student.changeAgresja(1); 

                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Skutki decyzji");
                    info.setHeaderText("Uniknąłeś grupy");
                    info.setContentText("Konsekwencje: Pozostałeś samodzielny, ale straciłeś okazję do nauki zespołowej. Moral: Czasem warto zaryzykować.");
                    info.getDialogPane().setStyle("-fx-font-size: 14px;");
                    info.showAndWait();
                }
            });
        } else if (student.getAsertywnosc() < 30) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potrzebujesz pomocy!");
            alert.setHeaderText("Czujesz się niepewnie w wyrażaniu swoich opinii.");
            alert.setContentText("Zgłosić swoje pomysły na spotkaniu grupy?");
            alert.getDialogPane().setStyle("-fx-font-size: 16px;");

            ButtonType tak = new ButtonType("Tak");
            ButtonType nie = new ButtonType("Nie");
            alert.getButtonTypes().setAll(tak, nie);

            alert.showAndWait().ifPresent(response -> {
                if (response == tak) {
                    if (student.getAsertywnosc() < 30) {
                        Alert konsekwencje = new Alert(Alert.AlertType.INFORMATION);
                        konsekwencje.setTitle("Konsekwencje decyzji");
                        konsekwencje.setHeaderText("Próbujesz mówić, ale głos ci drży i nikt cię nie słyszy.");
                        konsekwencje.setContentText("Moral: Asertywność wymaga praktyki i odwagi.");
                        konsekwencje.getDialogPane().setStyle("-fx-font-size: 16px;");
                        konsekwencje.showAndWait();

                        student.changeAsertywnosc(2);
                        student.changeSamowiadomosc(-1);

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Próba wyrażenia siebie");
                        info.setContentText("Konsekwencje: Mimo tremy spróbowałeś — to krok do pewności siebie. Moral: Ćwiczenie przynosi rezultaty.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    } else {
                        student.changeAsertywnosc(3);
                        student.changeUmiejetnoscWspolpracy(2); 
                        student.changeEmpatia(1); 

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Sukces na spotkaniu");
                        info.setContentText("Konsekwencje: Twoje pomysły zostały docenione — zdobywasz reputację. Moral: Odwaga się opłaca.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    }
                } else {
                    student.changeAsertywnosc(-1);
                    student.changeEgocentryzm(2); 
                    student.changeAgresja(1); 

                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Skutki decyzji");
                    info.setHeaderText("Wycofałeś się");
                    info.setContentText("Konsekwencje: Straciłeś okazję do wpływu na grupę. Moral: Czasem warto spróbować.");
                    info.getDialogPane().setStyle("-fx-font-size: 14px;");
                    info.showAndWait();
                }
            });
        } else if (student.getAgresja() > 70) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potrzebujesz pomocy!");
            alert.setHeaderText("Czujesz narastający gniew w trudnej sytuacji.");
            alert.setContentText("Spróbować uspokoić się i porozmawiać spokojnie?");
            alert.getDialogPane().setStyle("-fx-font-size: 16px;");

            ButtonType tak = new ButtonType("Tak");
            ButtonType nie = new ButtonType("Nie");
            alert.getButtonTypes().setAll(tak, nie);

            alert.showAndWait().ifPresent(response -> {
                if (response == tak) {
                    if (student.getAgresja() > 70) {
                        Alert konsekwencje = new Alert(Alert.AlertType.INFORMATION);
                        konsekwencje.setTitle("Konsekwencje decyzji");
                        konsekwencje.setHeaderText("Próbujesz się uspokoić, ale słowa same wychodzą ostro.");
                        konsekwencje.setContentText("Moral: Kontrola gniewu wymaga świadomego wysiłku.");
                        konsekwencje.getDialogPane().setStyle("-fx-font-size: 16px;");
                        konsekwencje.showAndWait();

                        student.changeAgresja(-3);
                        student.changeSamowiadomosc(2);

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Udało się ochłonąć");
                        info.setContentText("Konsekwencje: Uspokoiłeś się i odzyskałeś kontrolę. Moral: Praca nad gniewem się opłaca.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    } else {
                        student.changeAgresja(-2);
                        student.changeEmpatia(2); 
                        student.changeAsertywnosc(1); 

                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Skutki decyzji");
                        info.setHeaderText("Skuteczne uspokojenie");
                        info.setContentText("Konsekwencje: Utrzymałeś spokój i zyskałeś pozytywną reakcję otoczenia. Moral: Panowanie nad emocjami pomaga.");
                        info.getDialogPane().setStyle("-fx-font-size: 14px;");
                        info.showAndWait();
                    }
                } else {
                    student.changeAgresja(2);
                    student.changeUmiejetnoscWspolpracy(-2); 
                    student.changeNieczulosc(1); 

                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Skutki decyzji");
                    info.setHeaderText("Zignorowałeś potrzebę uspokojenia");
                    info.setContentText("Konsekwencje: Złość narasta, a relacje się psują. Moral: Otwarcie się na techniki uspokojenia pomaga.");
                    info.getDialogPane().setStyle("-fx-font-size: 14px;");
                    info.showAndWait();
                }
            });
        }

        pasekCechPozytywnych.setText(student.getCechyPozytywneStatus());
        pasekCechNegatywnych.setText(student.getCechyNegatywneStatus());
    }

    /**
     * Sprawdza, czy którakolwiek z cech osiągnęła poziom krytyczny (bardzo niski lub bardzo wysoki).
     * Jeśli tak, wyświetla odpowiedni komunikat ostrzegawczy z poradą.
     */
    private void sprawdzStrictAlert() {
        if (random.nextDouble() < 0.45) {
            if (student.getEmpatia() < 15) {
                pokazStrictAlert(
                    "Czujesz się całkowicie odizolowany od świata. Przyjaciele wydają się odlegli, a każda interakcja wywołuje niepokój.",
                    "Twoja empatia spadła poniżej krytycznego poziomu (poniżej 15). To może prowadzić do poważnych problemów społecznych, depresji i trudności w nawiązywaniu relacji. Rozważ pracę nad umiejętnościami społecznymi lub konsultację ze specjalistą."
                );
            } else if (student.getSamowiadomosc() < 15) {
                pokazStrictAlert(
                    "Życie wydaje się chaosem. Nie potrafisz ocenić swoich decyzji, a każda porażka uderza z pełną siłą.",
                    "Twoja samoświadomość osiągnęła krytycznie niski poziom (poniżej 15). Brak refleksji nad własnymi działaniami może prowadzić do błędnych decyzji i chronicznego stresu. Praca nad mindfulness i journaling może pomóc."
                );
            } else if (student.getUmiejetnoscWspolpracy() < 15) {
                pokazStrictAlert(
                    "Czujesz się jak samotny wilk. Współpraca z innymi wydaje się niemożliwa, a każdy zespół to potencjalne pole bitwy.",
                    "Twoja umiejętność współpracy spadła poniżej krytycznego poziomu (poniżej 15). To może utrudnić karierę zawodową i relacje interpersonalne. Ćwiczenia z pracy zespołowej i terapia grupowa mogą być pomocne."
                );
            } else if (student.getNieczulosc() > 85) {
                pokazStrictAlert(
                    "Świat wydaje się wrogi. Emocje innych nie mają znaczenia, a współczucie to słabość.",
                    "Twoja nieczułość osiągnęła krytycznie wysoki poziom (powyżej 85). Brak empatii może prowadzić do alienacji społecznej i problemów prawnych. Praca nad inteligencją emocjonalną jest niezbędna."
                );
            } else if (student.getAgresja() > 85) {
                pokazStrictAlert(
                    "Gniew buzuje w Tobie jak wulkan. Każda drobna rzecz może wywołać wybuch.",
                    "Twoja agresja osiągnęła krytycznie wysoki poziom (powyżej 85). To może prowadzić do przemocy, problemów zdrowotnych i utraty kontroli. Techniki zarządzania gniewem i terapia są pilnie potrzebne."
                );
            } else if (student.getEgocentryzm() > 85) {
                pokazStrictAlert(
                    "Świat kręci się wokół Ciebie. Potrzeby innych są nieważne, liczy się tylko Twój komfort.",
                    "Twój egocentryzm osiągnął krytycznie wysoki poziom (powyżej 85). To może zniszczyć relacje i utrudnić współpracę. Praca nad pokorą i empatią jest konieczna."
                );
            }
        }

        if (random.nextDouble() < 0.45) {
            if (student.getEmpatia() > 85) {
                pokazPositiveAlert(
                    "Czujesz głębokie połączenie z innymi. Przyjaciele zawsze szukają Twojej rady i wsparcia.",
                    "Twoja empatia osiągnęła doskonały poziom (powyżej 85). To pozwala na budowanie silnych relacji, pomaga w życiu społecznym i zwiększa satysfakcję z życia."
                );
            } else if (student.getSamowiadomosc() > 85) {
                pokazPositiveAlert(
                    "Życie wydaje się klarowne i zrozumiałe. Łatwo oceniasz swoje decyzje i uczysz się na błędach.",
                    "Twoja samoświadomość osiągnęła doskonały poziom (powyżej 85). Refleksja nad własnymi działaniami prowadzi do lepszych decyzji i zmniejsza stres."
                );
            } else if (student.getUmiejetnoscWspolpracy() > 85) {
                pokazPositiveAlert(
                    "Czujesz się częścią zespołu. Współpraca z innymi przychodzi naturalnie i przynosi sukces.",
                    "Twoja umiejętność współpracy osiągnęła doskonały poziom (powyżej 85). To ułatwi karierę zawodową i poprawi relacje interpersonalne."
                );
            } else if (student.getAsertywnosc() > 85) {
                pokazPositiveAlert(
                    "Wyrażasz swoje potrzeby pewnie i szanujesz innych. Ludzie słuchają Twoich opinii.",
                    "Twoja asertywność osiągnęła doskonały poziom (powyżej 85). To pozwala na efektywne komunikowanie się i osiąganie celów."
                );
            } else if (student.getNieczulosc() < 15) {
                pokazPositiveAlert(
                    "Świat wydaje się przyjazny. Emocje innych są ważne, a współczucie przychodzi naturalnie.",
                    "Twoja nieczułość osiągnęła minimalny poziom (poniżej 15). Wysoka empatia pomaga w relacjach i zmniejsza konflikty."
                );
            } else if (student.getAgresja() < 15) {
                pokazPositiveAlert(
                    "Czujesz spokój wewnętrzny. Nawet trudne sytuacje nie wywołują gniewu.",
                    "Twoja agresja osiągnęła minimalny poziom (poniżej 15). Kontrola emocji prowadzi do lepszego zdrowia i relacji."
                );
            } else if (student.getEgocentryzm() < 15) {
                pokazPositiveAlert(
                    "Świat nie kręci się tylko wokół Ciebie. Potrzeby innych są równie ważne.",
                    "Twój egocentryzm osiągnął minimalny poziom (poniżej 15). Pokora i empatia budują trwałe relacje."
                );
            }
        }
    }

    /**
     * Wyświetla ostrzeżenie o krytycznym stanie cechy.
     */
    private void pokazStrictAlert(String czescFabularna, String czescMerytoryczna) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("⚠️ Krytyczna Sytuacja ⚠️");
        alert.setHeaderText("OSTRZEŻENIE: Krytyczny poziom cechy!");
        alert.setContentText("**Część fabularna:**\n" + czescFabularna + "\n\n**Część merytoryczna:**\n" + czescMerytoryczna);
        alert.getDialogPane().setStyle("-fx-font-size: 14px;");
        alert.showAndWait();
    }

    /**
     * Wyświetla gratulacje po osiągnięciu pozytywnego, wysokiego poziomu cechy.
     */
    private void pokazPositiveAlert(String czescFabularna, String czescMerytoryczna) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🎉 Świetny postęp! 🎉");
        alert.setHeaderText("GRATULACJE: Doskonały poziom cechy!");
        alert.setContentText("**Część fabularna:**\n" + czescFabularna + "\n\n**Część merytoryczna:**\n" + czescMerytoryczna);
        alert.getDialogPane().setStyle("-fx-font-size: 14px;");
        alert.showAndWait();
    }

    /**
     * Punkt wejścia aplikacji. Uruchamia środowisko JavaFX.
     * @param args Argumenty wiersza poleceń.
     */
    public static void main(String[] args) {
        launch();
    }
}
