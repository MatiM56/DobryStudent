package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
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
public class Game extends Application {

    private final Student student = new Student();
    private final Label pasekCechPozytywnych = new Label();
    private final Label pasekCechNegatywnych = new Label();
    private final Label sterowanie = new Label();
    private final Label debugInfo = new Label();  // 🛠️ NARZĘDZIE: Informacje debugowania
    private ImageView obrazGracza = new ImageView((new Image("resources/student.png")));  // Obraz gracza

    // Event system
    private Event currentEvent = null;
    private Alert eventDialog = null;
    private Pane panelMapy;
    private final Set<KeyCode> wcisnieteKlawisze = new HashSet<>();
    private boolean czyRuszaSie = false;
    
    // Menu i dźwięk
    private boolean muzykaWlaczona = true;
    private double glosnosc = 0; // 0.0 - 1.0
    private MediaPlayer mediaPlayer; // odtwarzacz muzyki
    private HBox panelMenu;
    private Button przyciskNowaGra;
    private Button przyciskZapisz;
    private ComboBox<String> comboBoxWczytaj;
    private Button przyciskMuzyka; // przycisk włącz/wyłącz muzykę
    private Slider suwakGlosnosci;

    // Rozmiar mapy i pozycji (prosty grid)
    private final int rozmiarKafelka = 50;
    
    // Pozycja gracza w kafelkach (grid)
    private int graczX = 5;
    private int graczY = 5;
    
    // Rozmiary mapy w kafelkach - stałe
    private final int mapaSzerokoscKafelki = 42;  // szerokość mapy w kafelkach
    private final int mapaWysokoscKafelki = 32;   // wysokość mapy w kafelkach
    
    // Ściany
    private final int scianaKolumna = 37;  // ściana w kolumnie 37
    private final int scianaRzad = 16;     // ściana w rzędzie 17 (było 30, teraz 17)
    
   /* // Dodatkowe ściany blokujące ruch - format: {xStart, yStart, xEnd, yEnd}
    private final int[][] sciany = {
        {5, 5, 10, 8},    // przykładowa ściana 1
        {15, 10, 20, 12}, // przykładowa ściana 2
        {25, 15, 30, 18}  // przykładowa ściana 3
    };*/
    
    // Obszary interakcji jako pozycje kafelków: [xStart, yStart, xEnd, yEnd]
    private final int[] zakresLozka = {12, 12, 16, 16};        // Dom (12→16, 12→16ddd)
    private final int[] zakresBiblioteki = {30, 6, 36, 8};    // Biblioteka (30→35, 6→9)
    private final int[] zakresKwadratowej = {28, 0, 36, 4};  // Kwadratowa (28→36, 0→4)
    private final int[] zakresPracy = {20, 11, 26, 15};         // Praca (20→25, 12→15)
    private final int[] zakresUczelni = {0, 0, 10, 6};
    private final int[] zakresBiedronki = {16, 0, 22, 3};
    private final int[] zakresCSA = {30, 10, 36, 15};
    private final int[] zakresAutobusu = {0, 11, 6, 13};        // Autobus (0→6, 11→13) - ZMIEŃ WSPÓŁRZĘDNE!
    @Override
    public void start(Stage stage) {
        stage.setTitle("Dobry Student");
        
        // Zmaksymalizowane okno - zajmuje cały ekran z paskiem tytułowym
        stage.setMaximized(true);
        stage.setResizable(true);
        
        // Pobierz wymiary ekranu dla obliczenia rozmiaru mapy
        javafx.stage.Screen ekran = javafx.stage.Screen.getPrimary();
        javafx.geometry.Rectangle2D granice = ekran.getVisualBounds();
        double szerokoscEkranu = granice.getWidth();
        double wysokoscEkranu = granice.getHeight();

        // Mapka jako Pane - zajmie cały dostępny ekran
        panelMapy = new Pane();
        panelMapy.setPrefSize(szerokoscEkranu, wysokoscEkranu);

        // Wczytaj obraz mapy z zasobów jako tło
        ImageView tloMapy;
        try {
            Image obrazMapy = new Image(getClass().getResourceAsStream("/resources/mapa.png"));
            tloMapy = new ImageView(obrazMapy);
            
            // Powiąż rozmiar tła z rozmiarem panelu mapy dla automatycznego rozmiaru
            tloMapy.fitWidthProperty().bind(panelMapy.widthProperty());
            tloMapy.fitHeightProperty().bind(panelMapy.heightProperty());
            tloMapy.setX(0);
            tloMapy.setY(0);
            
            // Dodaj tło jako pierwszy element (żeby było na spodzie)
            panelMapy.getChildren().add(tloMapy);
        } catch (Exception e) {
            System.out.println("Nie można załadować obrazu mapy: " + e.getMessage());
            panelMapy.setStyle("-fx-background-color: #2d2d2d;");
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

        // Menu gry - pozycjonowane na dole po lewej
        panelMenu = new HBox(15); // większy odstęp między elementami
        panelMenu.setStyle("-fx-alignment: center-left; -fx-background-color: rgba(0,0,0,0.8); -fx-background-radius: 5;");

        // Przycisk nowej gry - większy
        przyciskNowaGra = new Button("Nowa gra");
        przyciskNowaGra.setStyle("-fx-font-size: 14px; -fx-min-width: 100px;");
        przyciskNowaGra.setOnAction(e -> nowaGra());

        // Przycisk zapisu - większy
        przyciskZapisz = new Button("Zapisz grę");
        przyciskZapisz.setStyle("-fx-font-size: 14px; -fx-min-width: 100px;");
        przyciskZapisz.setOnAction(e -> zapiszGre());

        // Lista rozwijana do wczytywania - większa
        comboBoxWczytaj = new ComboBox<>();
        comboBoxWczytaj.setPromptText("Wybierz zapis");
        comboBoxWczytaj.setStyle("-fx-font-size: 14px; -fx-min-width: 140px;");
        comboBoxWczytaj.setOnAction(e -> wczytajGre());
        odswiezListeZapisow();

        // Przycisk muzyki - większy
        przyciskMuzyka = new Button("🔊");
        przyciskMuzyka.setStyle("-fx-font-size: 18px; -fx-min-width: 50px;");
        przyciskMuzyka.setOnAction(e -> przelaczMuzyke());

        // Slider głośności - większy
        suwakGlosnosci = new Slider(0, 100, glosnosc);
        suwakGlosnosci.setShowTickLabels(true);
        suwakGlosnosci.setShowTickMarks(true);
        suwakGlosnosci.setMajorTickUnit(25);
        suwakGlosnosci.setBlockIncrement(10);
        suwakGlosnosci.setStyle("-fx-font-size: 14px; -fx-min-width: 40px; -fx-text-fill: white;");

        suwakGlosnosci.valueProperty().addListener((obs, oldVal, newVal) -> {
            glosnosc = newVal.doubleValue() / 100.0; // konwertuj z procentów na 0.0-1.0
            aktualizujGlosnosc();
        });

        panelMenu.getChildren().addAll(przyciskNowaGra, przyciskZapisz, comboBoxWczytaj, przyciskMuzyka, suwakGlosnosci);

        // Usuń menu z panelu mapy
        panelMapy.getChildren().remove(panelMenu);

        // Zmień pozycjonowanie menu - usuń bindingi i ustaw prostą pozycję
        panelMenu.setLayoutX(20); // 20px od lewej krawędzi
        panelMenu.setLayoutY(0); // będzie ustawione przez layout

        // Inicjalizuj muzykę po utworzeniu przycisków menu
        inicjalizujMuzyke();

        // Inicjalizuj komponenty eventów
        inicjalizujKomponentyEventow();

        // Pasek cech pozytywnych
        pasekCechPozytywnych.setText(student.getCechyPozytywneStatus());
        pasekCechPozytywnych.setStyle("-fx-padding: 10; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        pasekCechPozytywnych.setWrapText(true);

        // Pasek cech negatywnych
        pasekCechNegatywnych.setText(student.getCechyNegatywneStatus());
        pasekCechNegatywnych.setStyle("-fx-padding: 10; -fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        pasekCechNegatywnych.setWrapText(true);

        // Sterowanie
        sterowanie.setText("WASD - Ruch (4 kierunki) | E - Interakcja");
        sterowanie.setStyle("-fx-font-size: 20px; -fx-text-fill: gray;");
        
        // Panel z informacjami o pozycji gracza
        debugInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: lime; -fx-font-family: 'Courier New';");
        aktualizujDebugInfo();

        // Pasek statusu i informacje - prawa strona
        VBox prawaStrona = new VBox(5, pasekCechPozytywnych, pasekCechNegatywnych, sterowanie, debugInfo);
        prawaStrona.setMaxWidth(Double.MAX_VALUE);

        // Główny layout poziomy - menu po lewej, reszta po prawej
        HBox dolnyPanel = new HBox(20, panelMenu, prawaStrona);
        HBox.setHgrow(prawaStrona, javafx.scene.layout.Priority.ALWAYS);
        dolnyPanel.setStyle("-fx-alignment: center-left; -fx-padding: 10; -fx-background-color: rgba(0,0,0,0.5);");

        // Prosty układ bez niestandardowego paska tytułowego
        VBox uklad = new VBox(10, panelMapy, dolnyPanel);
        uklad.setStyle("-fx-alignment: center-left; -fx-background-color: #1a1a1a;");

        Scene widok = new Scene(uklad);

        // Obsługa klawiatury - Wciśnięcie
        widok.setOnKeyPressed(event -> {
            wcisnieteKlawisze.add(event.getCode());
            
            // Obsłuż ruch za pomocą WASD
            if (!czyRuszaSie) {
                obsluzRuch();
            }

            // Interakcja z obiektami (klawisz E)
            if (event.getCode() == KeyCode.E) {
                obsluzInterakcje();
            }
        });

        // Obsługa klawiatury - Puszczenie
        widok.setOnKeyReleased(event -> {
            wcisnieteKlawisze.remove(event.getCode());
        });

        stage.setScene(widok);
        stage.show();
        panelMapy.requestFocus(); // ustawia fokus na mapę, żeby klawiatura od razu działała
    }

    private void obsluzRuch() {
        int nowyX = graczX;
        int nowyY = graczY;
        
        // Sprawdź ruch WASD (tylko jeden kierunek na raz)
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

        // Sprawdź czy ruch jest poprawny (w granicach i nie w ścianę)
        if (czyPozycjaPoprawna(nowyX, nowyY)) {
            graczX = nowyX;
            graczY = nowyY;
            
            // Przesuń natychmiast bez animacji
            ustawPozycjeGracza();
            aktualizujDebugInfo();
            czyRuszaSie = false;
        }
    }

    // Sprawdź czy pozycja jest poprawna (w granicach mapy i nie w ściany)
    private boolean czyPozycjaPoprawna(int kafelekX, int kafelekY) {
        // Sprawdź czy w granicach mapy
        if (kafelekX < 0 || kafelekX >= mapaSzerokoscKafelki || 
            kafelekY < 0 || kafelekY >= mapaWysokoscKafelki) {
            return false;
        }
        
        // Sprawdź czy uderzenie w ścianę kolumny 37
        if (kafelekX >= scianaKolumna) {
            return false;
        }
        
        // Sprawdź czy uderzenie w ścianę rzędu 17
        if (kafelekY >= scianaRzad) {
            return false;
        }
        
        return true;
    }

    // Obsłuż interakcję z pobliskimi obiektami
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
            // Wykonaj aktywność (zmiany energii, stresu, itp.)
            wykonajAkcje(aktywnosc, "Wykonujesz aktywność: " + aktywnosc.getNazwa());

            // Pokaż losowy event dla tej aktywności
            pokazEvent(aktywnosc.getRandomEvent(), aktywnosc);
        } else {
            pasekCechPozytywnych.setText("Nic tu nie ma do interakcji!");
        }
    }

    // Ustawienie pozycji gracza na mapie (przelicz kafelki na piksele)
    private void ustawPozycjeGracza() {
        obrazGracza.setX(graczX * rozmiarKafelka);
        obrazGracza.setY(graczY * rozmiarKafelka);
        obrazGracza.toFront(); // Upewnij się że gracz jest zawsze na wierzchu po ruchu
    }

    // Sprawdzenie, czy gracz jest wewnątrz obszaru aktywności (używa kafelków)
    private boolean czyWmiejscuAktywnosci(int[] obszar) {
        // Pobierz zakres obszaru: [xStart, yStart, xEnd, yEnd]
        int xStart = obszar[0];
        int yStart = obszar[1];
        int xEnd = obszar[2];
        int yEnd = obszar[3];
        
        // Sprawdź czy gracz jest w prostokącie (od startu do końca)
        return graczX >= xStart && graczX <= xEnd &&
               graczY >= yStart && graczY <= yEnd;
    }

    // Interakcja z aktywnością
    private void wykonajAkcje(ActivityType aktywnosc, String tekstAkcji) {
        pasekCechPozytywnych.setText(tekstAkcji);
        aktywnosc.wykonaj(student);
        
        // Zaktualizuj status po krótkiej chwili żeby pokazać akcję
        javafx.application.Platform.runLater(() -> {
            pasekCechPozytywnych.setText(student.getCechyPozytywneStatus());
            pasekCechNegatywnych.setText(student.getCechyNegatywneStatus());
        });
    }
    
    // 🛠️ NARZĘDZIE: Aktualizuj informacje debugowania o pozycji gracza
    private void aktualizujDebugInfo() {
        // Przelicz pozycję kafelków na piksele
        int pixelX = graczX * rozmiarKafelka;
        int pixelY = graczY * rozmiarKafelka;
        
        // Sprawdź w którym obszarze jest gracz
        String obszar = "BRAK";
        if (czyWmiejscuAktywnosci(zakresLozka)) obszar = "DOM (Spanie)";
        else if (czyWmiejscuAktywnosci(zakresBiblioteki)) obszar = "BIBLIOTEKA (Nauka)";
        else if (czyWmiejscuAktywnosci(zakresKwadratowej)) obszar = "KWADRATOWA (Impreza)";
        else if (czyWmiejscuAktywnosci(zakresPracy)) obszar = "PRACA (Praca)";
        else if (czyWmiejscuAktywnosci(zakresUczelni)) obszar = "UCZELNIA";
        else if (czyWmiejscuAktywnosci(zakresBiedronki)) obszar = "BIEDRONKA";
        else if (czyWmiejscuAktywnosci(zakresCSA)) obszar = "CSA";
        else if (czyWmiejscuAktywnosci(zakresAutobusu)) obszar = "AUTOBUS";
        
        debugInfo.setText(String.format(
            "🛠️ DEBUG: Kafelek:(%d, %d) | Piksel:(%d, %d) | Obszar: %s",
            graczX, graczY,
            pixelX, pixelY,
            obszar
        ));
    }

    // Menu: Nowa gra
    private void nowaGra() {
        // Resetuj pozycję gracza
        graczX = 5;
        graczY = 5;
        ustawPozycjeGracza();
        aktualizujDebugInfo();
        
        pasekCechPozytywnych.setText("Rozpoczęto nową grę! (Stan studenta nie został zresetowany)");
        System.out.println("Rozpoczęto nową grę");
    }

    // Menu: Zapisz grę
    private void zapiszGre() {
        // Prosty wybór slotu - na razie zapisujemy do pierwszego dostępnego slotu
        for (int i = 1; i <= 5; i++) {
            String nazwaPliku = "save" + i + ".txt";
            if (!new File(nazwaPliku).exists()) {
                // Znaleziono pusty slot
                zapiszDoSlotu(i);
                odswiezListeZapisow();
                return;
            }
        }
        // Wszystkie sloty zajęte - nadpisz pierwszy
        zapiszDoSlotu(1);
        odswiezListeZapisow();
    }

    // Zapisz grę do konkretnego slotu
    private void zapiszDoSlotu(int slot) {
        String nazwaPliku = "save" + slot + ".txt";
        try (FileWriter writer = new FileWriter(nazwaPliku)) {
            writer.write(graczX + "," + graczY + "," + glosnosc + "," + muzykaWlaczona);
            pasekCechPozytywnych.setText("Gra została zapisana w slocie " + slot + "!");
            System.out.println("Gra została zapisana do " + nazwaPliku);
            odswiezListeZapisow(); // Odśwież listę po zapisaniu
        } catch (IOException e) {
            pasekCechPozytywnych.setText("Błąd podczas zapisywania gry!");
            System.out.println("Błąd zapisu: " + e.getMessage());
        }
    }

    // Menu: Wczytaj grę
    private void wczytajGre() {
        String wybranySlot = comboBoxWczytaj.getValue();
        if (wybranySlot == null || wybranySlot.isEmpty()) {
            pasekCechPozytywnych.setText("Wybierz slot zapisu do wczytania!");
            return;
        }

        // Wyciągnij numer slotu z tekstu (np. "Slot 1 (zapisany)" -> 1)
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

    // Wczytaj grę z konkretnego slotu
    private void wczytajZeSlotu(int slot) {
        String nazwaPliku = "save" + slot + ".txt";
        try (BufferedReader br = new BufferedReader(new FileReader(nazwaPliku))) {
            String line = br.readLine();
            
            String[] data = line.split(",");
            graczX = Integer.parseInt(data[0]);
            graczY = Integer.parseInt(data[1]);
            glosnosc = Double.parseDouble(data[2]);
            muzykaWlaczona = Boolean.parseBoolean(data[3]);
            
            ustawPozycjeGracza();
            aktualizujDebugInfo();
            suwakGlosnosci.setValue(glosnosc);
            aktualizujGlosnosc();
            
            pasekCechPozytywnych.setText("Gra została wczytana ze slotu " + slot + "!");
            System.out.println("Gra została wczytana z " + nazwaPliku);
        } catch (IOException | NumberFormatException e) {
            pasekCechPozytywnych.setText("Błąd podczas wczytywania gry ze slotu " + slot + "!");
            System.out.println("Błąd wczytywania: " + e.getMessage());
        }
    }

    // Menu: Aktualizuj głośność muzyki
    private void aktualizujGlosnosc() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(glosnosc);
        }
        // Debugowanie głośności usunięte
    }

    // Odśwież listę dostępnych zapisów w ComboBox
    private void odswiezListeZapisow() {
        comboBoxWczytaj.getItems().clear();
        for (int i = 1; i <= 5; i++) { // 5 slotów zapisu
            String nazwaPliku = "save" + i + ".txt";
            if (new File(nazwaPliku).exists()) {
                comboBoxWczytaj.getItems().add("Slot " + i + " (zapisany)");
            } else {
                comboBoxWczytaj.getItems().add("Slot " + i + " (pusty)");
            }
        }
    }

    // Inicjalizuj muzykę w tle
    private void inicjalizujMuzyke() {
        try {
            // Spróbuj załadować plik muzyczny z zasobów
            String muzykaPath = getClass().getResource("/resources/background_music.mp3").toString();
            Media muzyka = new Media(muzykaPath);
            mediaPlayer = new MediaPlayer(muzyka);
            
            // Ustaw głośność i włącz pętlę
            mediaPlayer.setVolume(glosnosc);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // pętla nieskończona
            
            // Jeśli muzyka ma być włączona na starcie
            if (muzykaWlaczona) {
                mediaPlayer.play();
                przyciskMuzyka.setText("🔊");
            } else {
                przyciskMuzyka.setText("🔇");
            }
            
        } catch (Exception e) {
            // Informacja o braku pliku muzycznego - zachowaj dla użytkownika
            przyciskMuzyka.setText("Muzyka niedostępna");
            przyciskMuzyka.setDisable(true);
        }
    }

    // Przełącz muzykę włącz/wyłącz
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

    private void inicjalizujKomponentyEventow() {
        // Event dialog będzie tworzony dynamicznie przy każdym evencie
    }

    private void pokazEvent(Event event, ActivityType aktywnosc) {
        currentEvent = event;

        // Tworzenie modalnego dialogu
        eventDialog = new Alert(Alert.AlertType.NONE);
        eventDialog.setTitle("Sytuacja");
        eventDialog.setHeaderText(event.getSituation());
        eventDialog.setContentText("Wybierz swoją reakcję:");

        // Tworzenie przycisków z neutralnymi kolorami
        ButtonType positiveButton = new ButtonType(event.getPositiveChoice());
        ButtonType negativeButton = new ButtonType(event.getNegativeChoice());

        eventDialog.getButtonTypes().setAll(positiveButton, negativeButton);

        // Dostosowanie stylu przycisków - neutralne kolory i większe czcionki
        eventDialog.getDialogPane().setStyle("-fx-base: #e0e0e0; -fx-background-color: #f5f5f5; -fx-font-size: 30px;");
        eventDialog.getDialogPane().getStylesheets().clear();

        // Ustaw większy rozmiar czcionki dla header text
        eventDialog.getDialogPane().setHeaderText(event.getSituation()); // Upewnij się że header jest ustawiony
        if (eventDialog.getDialogPane().lookup(".header-panel") != null) {
            eventDialog.getDialogPane().lookup(".header-panel").setStyle("-fx-font-size: 56px; -fx-font-weight: bold;");
        }

        // Obsługa wyniku
        eventDialog.showAndWait().ifPresent(response -> {
            if (response == positiveButton) {
                currentEvent.applyPositiveChoice(student);
            } else if (response == negativeButton) {
                currentEvent.applyNegativeChoice(student);
            }

            // Aktualizacja pasków statusu
            pasekCechPozytywnych.setText(student.getCechyPozytywneStatus());
            pasekCechNegatywnych.setText(student.getCechyNegatywneStatus());

            currentEvent = null;
            eventDialog = null;
        });
    }    private void ukryjEvent() {
        if (eventDialog != null) {
            eventDialog.close();
            eventDialog = null;
        }
        currentEvent = null;
    }

    public static void main(String[] args) {
        launch();
    }
}
