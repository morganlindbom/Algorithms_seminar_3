
# Algorithms – Seminar 3

Detta projekt samlar tre separata uppgifter inom algoritmer och datastrukturer. Fokus ligger inte bara på att implementera lösningarna, utan också på att jämföra strategier, visualisera hur algoritmerna arbetar och koppla praktiskt resultat till teoretisk tidskomplexitet.

Varje task har en egen delmapp, en egen startklass och ett eget GUI som gör det lättare att se vad algoritmen gör steg för steg.

---

## Projektöversikt

Projektet består av tre huvuddelar:

1. **Task 1:** min-heap och jämförelse mellan två sätt att bygga heapen.
2. **Task 2:** hashtabeller och tre olika metoder för kollisionshantering.
3. **Task 3:** en ordpusselösare som söker ord i ett bokstavsrutnät med hjälp av prefix-baserad beskärning.

Syftet med projektet är att visa hur olika datastrukturer fungerar i praktiken, hur deras operationer påverkar prestanda och hur algoritmer kan visualiseras för att bli lättare att förstå.

---

## Task 1 – Binär Heap

Task 1 behandlar en **min-heap**, alltså en binär trädstruktur där det minsta värdet alltid finns i roten och varje förälder är mindre än eller lika med sina barn.

Den här delen visar framför allt två olika sätt att bygga upp en heap:

- **Insert ett-i-taget:** varje nytt element läggs in separat och flyttas uppåt tills heap-egenskapen gäller.
- **BuildHeap:** alla element placeras först in i arrayen och heapen byggs sedan bottom-up genom att noder flyttas nedåt vid behov.

Det som demonstreras i tasken är:

- hur en heap lagras i en array,
- hur operationerna `insert`, `deleteMin` och `buildHeap` fungerar,
- varför insert ett-i-taget ger **O(n log n)** medan buildHeap ger **O(n)**,
- hur samma indata kan ge olika men ändå korrekta heap-strukturer,
- hur heapen kan traverseras med level-order, pre-order, in-order och post-order,
- hur faktisk exekveringstid stämmer överens med teorin.

Task 1 innehåller också en jämförelse mellan operationerna `insert` och `deleteMin` i en prioritetskö, för att visa vilken operation som i praktiken blir mest kostsam.

Startpunkt: `task1/main/Main.java`

Detaljerad dokumentation finns i [task1/README.md](task1/README.md).

---

## Task 2 – Hashtabell med kollisionshantering

Task 2 fokuserar på **hashtabeller**, där data placeras i en tabell med hjälp av en hashfunktion. Eftersom flera värden kan hamna på samma index behöver kollisioner hanteras på ett genomtänkt sätt.

I denna uppgift används hashfunktionen:

```text
h(x) = x mod tableSize
```

Tre olika kollisionsstrategier jämförs:

- **Separat länkning (chaining):** varje tabellposition innehåller en lista med alla värden som hashats till samma index.
- **Linjär sondering (linear probing):** vid kollision söks nästa lediga plats sekventiellt framåt.
- **Kvadratisk sondering (quadratic probing):** vid kollision används kvadratiska hopp för att minska klustring.

Det som tasken förklarar och visualiserar är:

- vad en hashfunktion gör,
- varför kollisioner uppstår,
- hur olika kollisionsstrategier påverkar tabellens struktur,
- varför linjär sondering kan skapa primär klustring,
- varför kvadratisk sondering ofta sprider ut elementen bättre,
- vilka kompromisser som finns mellan minnesanvändning, enkelhet och prestanda.

Den här tasken är alltså inte bara en implementation av en hashtabell, utan också en tydlig jämförelse mellan tre klassiska lösningar på samma problem.

Startpunkt: `task2/Main.java`

Detaljerad dokumentation finns i [task2/README.md](task2/README.md).

---

## Task 3 – Ordpusselösare

Task 3 implementerar en **ordpusselösare** som söker efter giltiga ord i ett tvådimensionellt rutnät av bokstäver. Algoritmen undersöker varje startposition i rutnätet och fortsätter sedan i alla **8 riktningar**: höger, vänster, upp, ned och diagonalerna.

För att sökningen inte ska bli onödigt dyr används en ordlista med två centrala kontroller:

- `isWord(s)` avgör om en sträng är ett fullständigt ord.
- `isPrefix(s)` avgör om strängen kan vara början på något ord i ordlistan.

Det gör att algoritmen kan avbryta en sökväg tidigt så fort en bokstavssekvens inte längre är ett giltigt prefix. Den tekniken kallas **prefix-baserad beskärning** och minskar antalet onödiga jämförelser kraftigt.

Task 3 visar därför tydligt:

- hur sökning i ett bokstavsrutnät kan organiseras systematiskt,
- hur alla riktningar testas från varje cell,
- hur prefixkontroll förbättrar effektiviteten,
- hur algoritmen kan köras steg för steg för att passa en GUI-animation,
- hur man kan mäta arbetet genom att räkna antalet kontroller som utförs.

Detta gör tasken till en kombination av sökalgoritm, optimering och visualisering.

Startpunkt: `task3/Main.java`

Detaljerad dokumentation finns i [task3/README.md](task3/README.md).

---

## Köra projektet

Varje uppgift körs separat via sin egen `Main.java`:

- `task1/main/Main.java` startar heap-visualiseringen.
- `task2/Main.java` startar hashtabell-visualiseringen.
- `task3/Main.java` startar ordpusselösaren.

Kompilera och kör den `Main`-klass som hör till den task du vill demonstrera.

---

## Sammanfattning

Tillsammans visar de tre uppgifterna tre olika typer av algoritmiska problem:

- **Task 1:** hur en trädbaserad datastruktur byggs och analyseras.
- **Task 2:** hur uppslagning i tabeller påverkas av kollisionshantering.
- **Task 3:** hur sökning i ett rutnät kan optimeras med hjälp av prefix.

Projektet ger därför både en praktisk implementation och en tydlig koppling mellan datastruktur, algoritm och tidskomplexitet.
