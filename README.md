# Dobry Student - Symulator Życia Studenta

Gra symulacyjna w języku Java z wykorzystaniem JavaFX, przedstawiająca codzienne życie studenta na kampusie uniwersyteckim.

## Opis gry

"Dobry Student" to gra symulacyjna, w której wcielasz się w rolę studenta przemierzającego kampus uniwersytecki. Twoim celem jest utrzymanie równowagi między różnymi aspektami życia studenckiego poprzez podejmowanie decyzji wpływających na cechy charakteru.

### Główne cechy gry:

- **Tile-based movement**: Poruszanie się po mapie kampusu w systemie kafli
- **System cech charakteru**: 10 cech wpływających na rozwój postaci:
  - Pozytywne: Empatia, Asertywność, Samoregulacja, Samowiadomość, Współpraca
  - Negatywne: Nieczułość, Uległość, Egocentryzm, Impulsywność, Agresja

- **Aktywności na kampusie**:
  - 📚 **Biblioteka** - Nauka z eventami grupowymi
  - 🏠 **Dom** - Spanie z eventami samoregulacji
  - 🎉 **Kwadratowa** - Imprezy z eventami społecznymi
  - 💼 **Praca** - Doświadczenia zawodowe
  - 🎓 **Uczelnia** - Wykłady i dyskusje
  - 🛒 **Biedronka** - Zakupy z interakcjami społecznymi
  - ⚽ **CSA** - Aktywność sportowa
  - 🚌 **Autobus** - Podróże komunikacją miejską

- **System eventów**: Każda aktywność zawiera unikalne sytuacje decyzyjne wpływające na cechy charakteru
- **System zapisywania**: Możliwość zapisywania i wczytywania postępów gry
- **Muzyka w tle**: Opcjonalna ścieżka dźwiękowa podczas rozgrywki

## Sterowanie

- **WASD** - Poruszanie się po mapie (góra, dół, lewo, prawo)
- **E** - Interakcja z obiektami w okolicy
- **Menu dolne** - Kontrola gry, zapisywanie, muzyka

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

## Autor

**Mateusz Moćko** - s197675

Projekt zrealizowany w ramach przedmiotu "Języki Programowania dla Windows Phone" (JPWP).

## Licencja

Ten projekt jest dostępny na licencji MIT - zobacz plik [LICENSE](LICENSE) dla szczegółów.