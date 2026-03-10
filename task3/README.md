# Task 3 – Ordpusselösare

## Syfte

Task 3 implementerar en **ordpusselösare** som söker igenom ett 2D-rutnät av bokstäver och hittar alla giltiga ord från en ordlista. Sökningen sker i alla 8 riktningar och använder prefix-baserad beskärning för att undvika onödiga kontroller.

---

## Hur algoritmen fungerar

### 1. Ordlistan (Dictionary)

Ordlistan laddas från `dictionary.txt`. Två nyckeloperationer används:
- `isWord(s)` – kontrollerar om strängen `s` är ett giltigt ord.
- `isPrefix(s)` – kontrollerar om strängen `s` är ett prefix till något ord i listan.

Prefix-kontrollen möjliggör **tidig avbrytning** (*pruning*): om en delsekvens inte kan leda till något giltigt ord fortsätter algoritmen inte längre i den riktningen.

### 2. Riktningar (Direction)

Sökningen sker i exakt **8 riktningar**:

| Riktning    | Rad-steg (dr) | Kolumn-steg (dc) |
|-------------|---------------|------------------|
| Höger       | 0             | +1               |
| Vänster     | 0             | −1               |
| Ned         | +1            | 0                |
| Upp         | −1            | 0                |
| Ned-höger   | +1            | +1               |
| Ned-vänster | +1            | −1               |
| Upp-höger   | −1            | +1               |
| Upp-vänster | −1            | −1               |

### 3. Söksteg (SolverStep)

Algoritmen är designad för steg-för-steg-exekvering så att GUI:t kan animera processen. Varje anrop till `nextStep()` utvärderar **ett** kandidatord och returnerar om det:
- Är ett giltigt prefix (sökning fortsätter).
- Är ett komplett ord (funnet!).
- Inte är ett giltigt prefix (avbryt denna riktning).

### 4. Sökordning

För varje cell `(rad, kolumn)` i rutnätet:
1. Kontrollera om enskild bokstav är ett prefix/ord.
2. För varje riktning: bygg successivt längre ord och kontrollera prefix + ord.
3. Avbryt en riktning så snart prefixet inte längre kan leda till ett giltigt ord.
4. Gå vidare till nästa cell.

---

## Komplexitet och effektivitet

- **Utan beskärning:** O(R × C × 8 × L) där R = rader, C = kolumner, L = max ordlängd.
- **Med prefix-beskärning:** Drastiskt färre kontroller i praktiken – grenar avbryts tidigt.
- Algoritmen räknar antalet utförda kontroller (`checkCounter`) som visas i GUI:t.

---

## Filer

| Fil                   | Ansvar                                               |
|-----------------------|------------------------------------------------------|
| `WordPuzzleSolver.java` | Kärnalgoritm, steg-för-steg-sökning i rutnätet    |
| `Dictionary.java`     | Laddar ordlistan, prefix- och ordkontroll            |
| `Direction.java`      | Enum med 8 riktningar och deras rörelsevektor        |
| `SolverStep.java`     | Representerar ett enstaka algoritmsteg för GUI-animation |
| `dictionary.txt`      | Ordlista som laddas vid start                        |

---

## GUI

Startas via `task3/Main.java`. Visualiserar rutnätet och animerar sökningen steg för steg, markerar funna ord och visar antalet utförda kontroller samt en komplexitetsgraf.
