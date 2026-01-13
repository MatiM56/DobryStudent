# Dobry Student - Symulator Życia Studenta

Gra serious game w języku Java z wykorzystaniem JavaFX, mająca na celu podnieść intelgiencję emocjonalną gracza poprzez zagłębienie się w wybory gracza i pozwolenie mu lepiej zrozmieć idące za tym konsekwencje

## Opis gry

"Dobry Student" to gra serious game, w której wcielasz się w rolę studenta przemierzającego kampus uniwersytecki. Twoim celem jest dokonywanie wyborów w różnych aktywnościach oraz wzmacnianie odpowiednich cech charakteru zgodnie ze swoją wolą.

### Główne cechy gry:

- **Tile-based movement**: Poruszanie się po mapie kampusu w systemie kafli
- **System cech charakteru**: 10 cech wpływających na rozwój postaci:
  - Pozytywne: Empatia, Asertywność, Samoregulacja, Samowiadomość, Współpraca
  - Negatywne: Nieczułość, Uległość, Egocentryzm, Impulsywność, Agresja

- **Aktywności na kampusie**:
  - 📚 **Biblioteka** - Nauka z eventami grupowymi
  - 🏠 **Dom** - Odpoczynek i jego wpływ na funkcjonowanie
  - 🎉 **Kwadratowa** - Imprezy z eventami społecznymi
  - 💼 **Praca** - Doświadczenia w środowisku pracy
  - 🎓 **Uczelnia** - Uczestnitwo w życiu na kampusie
  - 🛒 **Biedronka** - Zakupy z interakcjami społecznymi
  - ⚽ **CSA** - Zdrowa rywalizacja w sporcie
  - 🚌 **Autobus** - Podróże komunikacją miejską

- **System eventów**: Każda aktywność zawiera unikalne sytuacje decyzyjne wpływające na cechy charakteru
- **System zapisywania**: Możliwość zapisywania i wczytywania postępów gry
- **Muzyka w tle**: Opcjonalna ścieżka dźwiękowa podczas rozgrywki

## Sterowanie

- **WASD** - Poruszanie się po mapie (góra, dół, lewo, prawo)
- **E** - Interakcja z obiektami w okolicy
- **Menu dolne** - Kontrola gry, zapisywanie, muzyka

## Dokumentacja

Pełna dokumentacja techniczna wygenerowana za pomocą narzędzia Javadoc dostępna jest w folderze:
`dokumentacja/apidocs/index.html`

Aby ją otworzyć:
1. Przejdź do folderu `dokumentacja/apidocs/`.
2. Otwórz plik `index.html` w dowolnej przeglądarce internetowej.

## Technologie

- **Java 21** - Język programowania
- **JavaFX** - Framework GUI
- **Maven** - Zarządzanie projektem i zależnościami

## Wymagania systemowe

- Java 21 lub nowszy
- System operacyjny: Windows/Linux/macOS

## Instalacja i uruchomienie

### Wymagania wstępne:
1. Zainstaluj JDK 21 (można pobrać z [Adoptium](https://adoptium.net/))
2. Zainstaluj Maven (można pobrać z [Apache Maven](https://maven.apache.org/))

### Uruchomienie gry:
```bash
# Sklonuj repozytorium
git clone https://github.com/MatiM56/DobryStudent.git
cd DobryStudent

# Zbuduj projekt
mvn clean compile

# Uruchom grę
mvn javafx:run
```

Alternatywnie, można użyć skryptów uruchomieniowych z katalogu `target/image/bin/` po zbudowaniu runtime image:
```bash
mvn clean javafx:jlink
# Następnie uruchom student-simulator.bat (Windows) lub student-simulator (Linux/macOS)
```

## Struktura projektu

```
src/
├── game/
│   └── Game.java          # Główna klasa aplikacji
├── model/
│   ├── Event.java         # System eventów i cech charakteru
│   ├── Student.java       # Klasa postaci gracza
│   ├── ActivityType.java  # Abstrakcyjna klasa aktywności
│   ├── Spanie.java        # Aktywność snu
│   ├── Nauka.java         # Aktywność nauki
│   ├── Impreza.java       # Aktywność imprez
│   ├── Praca.java         # Aktywność pracy
│   ├── Uczelnia.java      # Aktywność uczelni
│   ├── Sklep.java         # Aktywność zakupów
│   ├── Sport.java         # Aktywność sportowa
│   └── BusActivity.java   # Aktywność autobusu
└── resources/
    ├── mapa.png           # Grafika mapy kampusu
    ├── student.png        # Grafika postaci gracza
    └── background_music.mp3 # Muzyka w tle
```

  ## Zmiany w wersji z 2025-12-02

  - Dodano system **alertów** ostrzegających przy skrajnie niskich lub wysokich poziomach wybranych cech (np. empatii, samoświadomości, współpracy, agresji, egocentryzmu, nieczułości).
  - Dodano system **pozytywnych alertów** gratulujących przy bardzo dobrych poziomach cech (wysokie cechy pozytywne, niskie negatywne), z częścią fabularną i merytoryczną.
  - Rozszerzono logikę eventów wsparcia tak, aby lepiej reagowały na niskie wartości kluczowych cech (empatia, samoświadomość, współpraca).
  - W tej wersji nadal rozwijane zostały rodzaje eventów które gracz może spotkac w trakcie rozgrywki

## Autor

**Mateusz Moćko** - s197675

Projekt zrealizowany w ramach przedmiotu "Języki Programowania Wysokiego Poziomu" (JPWP).
