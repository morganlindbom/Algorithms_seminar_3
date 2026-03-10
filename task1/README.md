# Task 1 – Binär Heap

## Syfte

Task 1 handlar om att förstå och implementera en **min-heap** – en trädbaserad datastruktur där varje förälder alltid är mindre än sina barn. Uppgiften jämför två metoder för att bygga en heap, analyserar deras tidskomplexitet och visualiserar resultaten.

---

## Datastruktur – BinaryHeap

Heapen lagras som ett 1-indexerat array (`heap[1]` = rotnod). Det innebär att:
- Förälder till nod `i` finns på index `⌊i / 2⌋`
- Vänster barn på index `2i`
- Höger barn på index `2i + 1`

Grundoperationer:
| Operation     | Komplexitet   |
|---------------|---------------|
| `insert`      | O(log n)      |
| `deleteMin`   | O(log n)      |
| `buildHeap`   | O(n)          |
| `getMin`      | O(1)          |

---

## Deluppgifter

### Task 1a – Insert ett-i-taget (Percolate Up)

Element sätts in ett i taget i en tom heap. Varje nytt värde placeras sist i arrayen och "bubbljar upp" (*percolate up*) tills heap-ordningen är uppfylld.

- Varje enskild insert kostar O(log n) i värsta fall.
- Totalt kostar n insertioner **O(n log n)**.
- Visar steg-för-steg hur varje värde bubblar upp med exakta byten.

### Task 1b – BuildHeap (Bottom-Up, linjär tid)

Hela input-arrayen kopieras in i heapen och sedan körs `percolateDown` på alla interna noder (index `⌊n/2⌋` ner till `1`).

- De flesta noder är löv och behöver inga byten.
- Totalkostnaden konvergerar matematiskt till **O(n)**.
- Visar steg-för-steg varje `percolateDown`-anrop.

**Jämförelse:**
| Egenskap             | Insert ett-i-taget | BuildHeap |
|----------------------|--------------------|-----------|
| Tidskomplexitet      | O(n log n)         | O(n)      |
| Riktning             | Percolate UP       | Percolate DOWN |
| Kräver hela datan i förväg? | Nej       | Ja        |
| Resulterar i identisk heap? | Nej (men båda giltiga) | Nej |

### Task 1c – Traversaler

Båda heaparna (från 1a och 1b) traverseras med fyra metoder:
- **Level-order** – bredd-först (samma som array-ordning)
- **Pre-order** – rot, vänster, höger
- **In-order** – vänster, rot, höger
- **Post-order** – vänster, höger, rot

### Task 1d – Komplexitetsmätning

Mäter faktisk exekveringstid för båda algoritmerna på ökande datastorlekar: **100, 1 000, 10 000, 100 000** element. Bekräftar den teoretiska skillnaden O(n log n) vs O(n) i praktiken.

### Task 1e – Prioritetskö: insert vs deleteMin

Jämför kostnaden för `insert` och `deleteMin` på en prioritetskö med 10 000 element, mätt över 5 000 operationer. Slutsats: `deleteMin` är konsekvent dyrare eftersom det alltid kräver full `percolateDown` från roten.

---

## GUI

Startas via `task1/main/Main.java`. Visualiserar heapen som ett träd och visar traversalresultat och komplexitetsgrafik.
