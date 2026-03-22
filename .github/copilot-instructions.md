# GitHub Copilot Instructions

## Om projektet

Detta är ett Java-projekt för Algorithms Seminar 3 med tre separata uppgifter:

- **task1/** – Binär Heap (min-heap, traversaler, komplexitetsmätning)
- **task2/** – Hashtabell med kollisionshantering (chaining, linjär/kvadratisk sondering)
- **task3/** – Ordpusselösare (8-riktningssökning med prefix-beskärning)

## Projektstruktur

Varje task är ett fristående Java-projekt med:
- `Main.java` – startar ett Swing-baserat GUI
- `gui/` – GUI-komponenter
- Uppgiftsspecifik logik i egna paket (t.ex. `heap/`, `hash/`, `solver/`)

## Hur man kör projektet

Kompilera och kör respektive `Main.java` för att starta GUI för önskad uppgift:

```bash
# Task 1
javac -d out task1/main/Main.java task1/heap/*.java task1/gui/*.java task1/task/*.java
java -cp out main.Main

# Task 2
javac -d out task2/Main.java task2/hash/*.java task2/gui/*.java task2/data/*.java task2/visualizer/*.java
java -cp out Main

# Task 3
javac -d out task3/Main.java task3/solver/*.java task3/gui/*.java
java -cp out Main
```

## Kodkonventioner

- Språk: Java
- GUI-ramverk: Swing
- Ingen extern beroendehantering (inga Maven/Gradle-filer)
- Källkod och `.class`-filer separeras via `out/`-katalogen (ignoreras av git)
