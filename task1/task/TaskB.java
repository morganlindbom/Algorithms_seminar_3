
// TaskB.java
package task1.task;

import task1.heap.BinaryHeap;

public class TaskB {

    public static String run(){
        return run(new int[]{10,12,1,14,6,5,8,15,3,9,7,4,11,13,2});
    }

    public static String run(int[] input){
        /** Task 1b execution

        Builds the heap using the linear-time buildHeap algorithm
        and provides a detailed explanation of how and why it works.
        */

        StringBuilder sb = new StringBuilder();

        // ── Title ──
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  Task 1b – BuildHeap (bottom-up, linjär tid)\n");
        sb.append("═══════════════════════════════════════════════════════════════\n\n");

        // ── Input ──
        sb.append("Input-array: [");
        for(int i = 0; i < input.length; i++){
            if(i > 0) sb.append(", ");
            sb.append(input[i]);
        }
        sb.append("]\n\n");

        // ── What is buildHeap? ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Vad är BuildHeap?\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("BuildHeap (även kallad \"heapify\") är en algoritm som omvandlar\n");
        sb.append("en godtycklig array till en giltig min-heap. Till skillnad från\n");
        sb.append("att sätta in element ett i taget (Task 1a), arbetar buildHeap\n");
        sb.append("nedifrån-och-upp (bottom-up) och uppnår linjär tidskomplexitet.\n\n");

        // ── How does it work? ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Hur fungerar det? (Steg för steg)\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("1. Kopiera hela input-arrayen till heapens interna array.\n");
        sb.append("   Arrayen lagras från index 1 (index 0 lämnas tomt).\n\n");
        sb.append("2. Börja vid den sista noden som har barn, det vill säga\n");
        sb.append("   index ⌊n/2⌋ = ").append(input.length / 2).append(" (n = ").append(input.length).append(").\n\n");
        sb.append("3. Utför percolateDown (sänk-ned) på varje nod från\n");
        sb.append("   index ⌊n/2⌋ ner till index 1.\n\n");
        sb.append("   percolateDown jämför en nod med sina barn och byter\n");
        sb.append("   plats med det minsta barnet om noden är större.\n");
        sb.append("   Processen upprepas tills heap-ordningen är uppfylld.\n\n");

        // ── Step-by-step demo ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Visuell genomgång med vår input\n");
        sb.append("───────────────────────────────────────────────────────────────\n");

        // Copy array into temporary array to show steps
        int n = input.length;
        int[] arr = new int[n + 1];
        for(int i = 0; i < n; i++){
            arr[i + 1] = input[i];
        }

        sb.append("Steg 0 – Utgångsläge (arrayen kopierad):\n");
        sb.append("  [");
        for(int i = 1; i <= n; i++){
            if(i > 1) sb.append(", ");
            sb.append(arr[i]);
        }
        sb.append("]\n\n");

        int stepNum = 1;
        for(int i = n / 2; i >= 1; i--){
            int before = arr[i];
            // Perform percolateDown manually
            int idx = i;
            boolean swapped = false;
            StringBuilder swapLog = new StringBuilder();
            while(idx * 2 <= n){
                int child = idx * 2;
                if(child + 1 <= n && arr[child + 1] < arr[child]){
                    child++;
                }
                if(arr[idx] > arr[child]){
                    swapLog.append("    Byt ").append(arr[idx]).append(" ↔ ").append(arr[child]);
                    swapLog.append("  (index ").append(idx).append(" ↔ ").append(child).append(")\n");
                    int temp = arr[idx];
                    arr[idx] = arr[child];
                    arr[child] = temp;
                    idx = child;
                    swapped = true;
                } else {
                    break;
                }
            }

            sb.append("Steg ").append(stepNum++);
            sb.append(" – percolateDown(").append(i).append(")");
            sb.append("  [nod = ").append(before).append("]\n");
            if(swapped){
                sb.append(swapLog);
            } else {
                sb.append("    Ingen byte behövs (redan korrekt).\n");
            }
            sb.append("  Resultat: [");
            for(int j = 1; j <= n; j++){
                if(j > 1) sb.append(", ");
                sb.append(arr[j]);
            }
            sb.append("]\n\n");
        }

        // ── Final result via BinaryHeap ──
        BinaryHeap heap = new BinaryHeap(n + 10);
        heap.buildHeap(input);

        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Slutgiltig heap\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("Array:  [");
        for(int i = 1; i <= heap.size(); i++){
            if(i > 1) sb.append(", ");
            sb.append(heap.get(i));
        }
        sb.append("]\n");
        sb.append("Min:     ").append(heap.getMin()).append("\n\n");

        // ── Index mapping ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Förälder/Barn-relationer i arrayen\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("I en heap lagrad som array (1-indexerad) gäller:\n");
        sb.append("  • Förälder till nod i:    ⌊i / 2⌋\n");
        sb.append("  • Vänster barn till nod i: 2i\n");
        sb.append("  • Höger barn till nod i:   2i + 1\n\n");
        sb.append("Exempel från resultatet:\n");
        for(int i = 1; i <= Math.min(5, heap.size()); i++){
            sb.append("  Index ").append(i).append(" = ").append(heap.get(i));
            if(i * 2 <= heap.size()){
                sb.append("  →  barn: ").append(heap.get(i * 2));
                if(i * 2 + 1 <= heap.size()){
                    sb.append(", ").append(heap.get(i * 2 + 1));
                }
            } else {
                sb.append("  (lövnod, inga barn)");
            }
            sb.append("\n");
        }

        // ── Why O(n)? ──
        sb.append("\n───────────────────────────────────────────────────────────────\n");
        sb.append("  Varför är tidskomplexiteten O(n)?\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("Det kan verka intuitivt att buildHeap borde vara O(n log n)\n");
        sb.append("eftersom vi anropar percolateDown (som är O(log n)) för n/2 noder.\n\n");
        sb.append("Men detta är en överskattning! Nyckeln ligger i att:\n\n");
        sb.append("  • Hälften av alla noder är löv → 0 byten\n");
        sb.append("  • 1/4 av noderna har höjd 1   → max 1 byte\n");
        sb.append("  • 1/8 av noderna har höjd 2   → max 2 byten\n");
        sb.append("  • ...och så vidare\n");
        sb.append("  • Bara 1 nod (roten) har höjd log n\n\n");
        sb.append("Total arbete = Σ (antal noder på höjd h) × h\n");
        sb.append("             = Σ ⌊n / 2^(h+1)⌋ × h   för h = 0 till log n\n");
        sb.append("             ≤ n × Σ h/2^(h+1)        för h = 0 till ∞\n");
        sb.append("             = n × 1\n");
        sb.append("             = O(n)\n\n");
        sb.append("Serien Σ h/2^(h+1) konvergerar mot 1, vilket ger O(n).\n\n");

        // ── Comparison with insert one-by-one ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Jämförelse: BuildHeap vs. Insert ett-i-taget\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("┌────────────────────────┬────────────────────┬──────────────┐\n");
        sb.append("│ Egenskap               │ Insert ett-i-taget │ BuildHeap    │\n");
        sb.append("├────────────────────────┼────────────────────┼──────────────┤\n");
        sb.append("│ Tidskomplexitet        │ O(n log n)         │ O(n)         │\n");
        sb.append("│ Metod                  │ Percolate UP       │ Percolate DN │\n");
        sb.append("│ Riktning               │ Nedifrån → upp     │ Uppifrån → ned│\n");
        sb.append("│ Arbetar från           │ Tomt träd, lägger  │ Full array,  │\n");
        sb.append("│                        │ till ett i taget   │ fixar i bulk │\n");
        sb.append("│ Kräver sorterad input? │ Nej                │ Nej          │\n");
        sb.append("│ Resultat identiskt?    │ Nej (olika ordning)│ Nej          │\n");
        sb.append("│ Båda ger giltig heap?  │ Ja                 │ Ja           │\n");
        sb.append("└────────────────────────┴────────────────────┴──────────────┘\n\n");

        sb.append("Sammanfattning:\n");
        sb.append("BuildHeap är att föredra när hela datamängden är tillgänglig\n");
        sb.append("från början, eftersom det är asymptotiskt snabbare (O(n) mot\n");
        sb.append("O(n log n)). Insert ett-i-taget används när element anländer\n");
        sb.append("dynamiskt, t.ex. i en prioritetskö.\n");

        return sb.toString();
    }
}
