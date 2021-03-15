# AddingApplication
Aplikacja Adding została stworzona specjalnie na potrzeby firmy Decerto.
Usługa umożliwia dodawanie dowolnych wartości do siebie.

## Opis usługi
**Zaimplementowane źródła danych:**
- Baza danych (H2 pamięciowa - przykładowe dane są ładowane przy uruchomieniu aplikacji)
- Random API (https://www.random.org)
- Wewnętrzny generator liczb

Aplikacja jest otwarta na dodatkowe źródła danych, wymagane rozszerzenie klasy o interfejs IDataSource oraz nazwanie beana serwisu z przedrostkiem "work."

**Zaimplementowane operację dodawania:**
- Dodawanie liczb rzeczywistych bez maksymalnego zakresu

Aplikacja jest otwarta na dodatkowe operację, wymagane rozszerzenie klasy o interface IOperation.


**Pobudzanie operacji**
- W aplikacji został zaimplementowany Scheduler, który pobudza wszystkie źródła danych co 5 sekund.
Wynik operacji zapisywany jest do bazy danych.
Aktualne stan licznika można zobaczysz w logach bądź przy odpytaniu endpoint GET /result


## Proces deploymentu
Do usługi został zaimplementowany gitlab-ci, umożliwiający deployment aplikacji do chmury AWS ECS. 
- przy każdym commit uruchamiany jest stage testów
- budowanie jest możliwe po utworzeniu taga ze strukturą x.y.z (gdzie x.y.z to wersja aplikacji). Stworzony tag umożliwia wysłanie obrazu dockerowego na chmurę i wybudowanie środowiska DEV i RC. 

## Uruchomienie lokalne aplikacji
- po zaimportowaniu do np.: Intellij
- standardowym komendami spring-boot/maven (np.: mvn spring-boot:run)
- zbudowanie obrazu dockerowego (Dockerfile included)


## Najważniejsze technologie/biblioteki
- Java 11
- Spring Boot 2.4
- Maven 3.6
- Hibernate
- Liquibase
- Lombok
- H2
- Docker (deployment)
