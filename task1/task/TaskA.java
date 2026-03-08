
// TaskA.java
package task1.task;

import task1.heap.BinaryHeap;

public class TaskA {

    public static String run(){
        return run(new int[]{10,12,1,14,6,5,8,15,3,9,7,4,11,13,2});
    }

    public static String run(int[] input){
        /** Task 1a execution

        Inserts elements one-by-one into an initially empty heap and
        prints the heap after each insertion, with detailed explanation.
        */

        StringBuilder sb = new StringBuilder();

        // ── Title ──
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  Task 1a – Insert ett-i-taget (percolate up)\n");
        sb.append("═══════════════════════════════════════════════════════════════\n\n");

        // ── Input ──
        sb.append("Input-array: [");
        for(int i = 0; i < input.length; i++){
            if(i > 0) sb.append(", ");
            sb.append(input[i]);
        }
        sb.append("]\n\n");

        // ── What is insert one-by-one? ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Vad är \"Insert ett-i-taget\"?\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("Denna metod bygger en heap genom att starta med en tom heap\n");
        sb.append("och sätta in (insert) ett element i taget. Varje gång ett nytt\n");
        sb.append("element läggs till placeras det sist i arrayen och \"bubblar\n");
        sb.append("upp\" (percolate up) tills heap-ordningen är uppfylld.\n\n");

        // ── How percolate up works ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Hur fungerar Percolate Up?\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("1. Placera det nya elementet på nästa lediga position\n");
        sb.append("   (sist i arrayen, index = size + 1).\n\n");
        sb.append("2. Jämför elementet med sin förälder (index ⌊i / 2⌋).\n\n");
        sb.append("3. Om elementet är MINDRE än föräldern → byt plats.\n");
        sb.append("   Upprepa steg 2-3 tills elementet är på rätt plats\n");
        sb.append("   (dvs. det är ≥ sin förälder, eller har nått roten).\n\n");
        sb.append("I en min-heap garanterar detta att roten alltid\n");
        sb.append("innehåller det minsta värdet.\n\n");

        // ── Step-by-step insertions ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Steg-för-steg insättning\n");
        sb.append("───────────────────────────────────────────────────────────────\n");

        BinaryHeap heap = new BinaryHeap(input.length + 10);
        int stepNum = 1;

        for(int v : input){
            sb.append("Steg ").append(stepNum++).append(" – Insert(").append(v).append(")\n");

            // Show where value is placed
            int positionBefore = heap.size() + 1;
            sb.append("  Placeras på index ").append(positionBefore).append(".\n");

            // Track percolate up swaps
            int[] arrBefore = new int[heap.size() + 2];
            for(int i = 1; i <= heap.size(); i++){
                arrBefore[i] = heap.get(i);
            }
            arrBefore[positionBefore] = v;

            // Simulate percolate up to show swaps
            int idx = positionBefore;
            StringBuilder swapLog = new StringBuilder();
            int tempVal = v;
            while(idx > 1 && tempVal < arrBefore[idx / 2]){
                swapLog.append("    Byt ").append(tempVal)
                       .append(" ↔ ").append(arrBefore[idx / 2])
                       .append("  (index ").append(idx)
                       .append(" ↔ ").append(idx / 2).append(")\n");
                arrBefore[idx] = arrBefore[idx / 2];
                arrBefore[idx / 2] = tempVal;
                idx = idx / 2;
            }
            if(swapLog.length() > 0){
                sb.append("  Percolate up:\n").append(swapLog);
            } else {
                sb.append("  Ingen uppbubbling behövs.\n");
            }

            heap.insert(v);

            sb.append("  Resultat: [");
            for(int i = 1; i <= heap.size(); i++){
                if(i > 1) sb.append(", ");
                sb.append(heap.get(i));
            }
            sb.append("]\n\n");
        }

        // ── Final result ──
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

        // ── Why O(n log n)? ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Varför är tidskomplexiteten O(n log n)?\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("Vid varje insättning kan det nya elementet behöva bubbla\n");
        sb.append("ända upp till roten. Trädets höjd är ⌊log₂ n⌋, så varje\n");
        sb.append("insert-operation kostar O(log n) i värsta fall.\n\n");
        sb.append("Med n insättningar blir totala kostnaden:\n");
        sb.append("  T(n) = Σ log₂ i  för i = 1 till n\n");
        sb.append("       ≤ n × log₂ n\n");
        sb.append("       = O(n log n)\n\n");
        sb.append("I praktiken är insert ofta snabbare än O(log n) per operation\n");
        sb.append("eftersom de flesta element inte behöver bubbla hela vägen upp.\n");
        sb.append("Men i värsta fall (t.ex. sorterad indata i fallande ordning)\n");
        sb.append("nås den övre gränsen O(n log n).\n\n");

        // ── Comparison ──
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("  Jämförelse med BuildHeap (Task 1b)\n");
        sb.append("───────────────────────────────────────────────────────────────\n");
        sb.append("Insert ett-i-taget bygger heapen \"uppifrån\" genom percolate UP.\n");
        sb.append("BuildHeap bygger heapen \"nedifrån\" genom percolate DOWN.\n\n");
        sb.append("Samma indata ger OLIKA, men båda giltiga, min-heapar.\n");
        sb.append("BuildHeap är snabbare (O(n) mot O(n log n)) men kräver att\n");
        sb.append("hela datamängden finns tillgänglig från början.\n");

        return sb.toString();
    }
}
