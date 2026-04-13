# 🐟 Oceanarium Manager

Projekt wykonany w ramach laboratorium z przedmiotu **PROJEKTOWANIE APLIKACJI UŻYTKOWYCH**.

Aplikacja desktopowa umożliwiająca zarządzanie akwariami, rybami oraz ich ocenami. Działa z wykorzystaniem **Hibernate ORM**, **bazy danych H2** oraz interfejsu graficznego w **Swing**.

---

## Funkcjonalności

### Zarządzanie akwariami
- dodawanie i usuwanie akwariów
- wyświetlanie listy akwariów
- sortowanie według zapełnienia

### Zarządzanie rybami
- dodawanie i usuwanie ryb
- zmiana stanu ryby (zdrowa, chora, kwarantanna itp.)
- filtrowanie po nazwie / gatunku
- filtrowanie po stanie

### System ocen (Rating)
- dodawanie ocen (0–5)
- przypisanie oceny do akwarium
- komentarz + data
- obliczanie:
  - liczby ocen
  - średniej ocen dla akwarium

### Statystyki
- wykorzystanie **Criteria API**
- grupowanie (GROUP BY)
- wyświetlanie statystyk w UI

### Zapis i odczyt danych
- zapis/odczyt do pliku binarnego (serializacja)
- eksport/import danych do CSV

---

## Technologie

- Java 17
- Hibernate ORM
- H2 Database
- Maven
- Swing (GUI)
- JDBC / JPA
- Criteria API
- HQL

---

## Struktura projektu
├── model # encje (Aquarium, Fish, Rating)
├── dao # dostęp do bazy danych (DAO)
├── service # logika biznesowa (statystyki, pliki)
├── facade # warstwa pośrednia (API dla UI)
├── ui # interfejs graficzny (Swing)
├── dto # obiekty transferowe do tabel
├── config # konfiguracja Hibernate

---

