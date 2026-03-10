# Task 2 – Hashtabell med kollisionshantering

## Syfte

Task 2 handlar om att förstå och implementera en **hashtabell** med tre olika strategier för att hantera kollisioner – situationer där två värden mappas till samma index.

---

## Hashfunktion

Använder en enkel modulofunktion:

```
h(x) = x mod tableSize
```

Med tabellstorlek 10 returnerar funktionen ett index mellan 0 och 9.

**Exempel:** `h(6173) = 6173 mod 10 = 3`

---

## Kollisionsstrategier

### 1. Separat länkning (Chaining)

Varje index i tabellen innehåller en **lista** (`ArrayList`). När en kollision uppstår läggs det nya värdet till i listan vid det aktuella indexet.

```
index 3 → [1323, 6173]
```

- Insertion alltid O(1) – ingen sökning efter ledig plats krävs.
- Ingen övre gräns på antalet element per index.
- Använder extra minne för liststrukturer.

### 2. Linjär sondering (Linear Probing)

Varje index rymmer exakt ett värde. Vid kollision söker algoritmen **linjärt** framåt tills ett ledigt index hittas (med wrap-around).

```
Kollision vid index 3 → prova 4, 5, 6, ...
```

- Enkel att implementera.
- Skapar **primär klustring** – långa kedjor av ockuperade platser bildas nära kollisionspunkten.
- Prestanda försämras när tabellen fylls upp.

### 3. Kvadratisk sondering (Quadratic Probing)

Liknar linjär sondering men hoppar med kvadratiska steg vid kollision:

```
index + 1², index + 2², index + 3², ...
```

**Exempel:** Kollision vid index 3 → prova 4, 7, 12 (mod 10 = 2), ...

- Minskar primär klustring jämfört med linjär sondering.
- Kan i vissa fall misslyckas med att hitta en ledig plats om tabellen är mer än halvfull.

---

## Jämförelse

| Strategi               | Kollisionshantering        | Klustring | Minnesbehov |
|------------------------|----------------------------|-----------|-------------|
| Separat länkning       | Lista per index            | Nej       | Extra (listor) |
| Linjär sondering       | Nästa lediga index         | Ja (primär) | Lågt      |
| Kvadratisk sondering   | Kvadratiska hopp           | Reducerad | Lågt       |

---

## GUI

Startas via `task2/Main.java`. Visualiserar tabellen för alla tre strategier med samma indata, så att skillnaderna i kollisionshantering enkelt kan jämföras.
