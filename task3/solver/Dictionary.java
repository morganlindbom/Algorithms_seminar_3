// Dictionary.java

package task3.solver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dictionary {

    private Set<String> words;
    private Set<String> prefixes;

    public Dictionary(){
        /** constructor

        Initializes the dictionary structure and loads the
        word list from dictionary.txt. Prefixes are generated
        for fast prefix pruning during the puzzle search.
        */

        words = new HashSet<>();
        prefixes = new HashSet<>();

        loadDictionary("task3/dictionary.txt");
    }

    public Dictionary(List<String> wordList){
        /** constructor from word list

        Builds dictionary from a provided list of words
        instead of reading from file.
        */

        words = new HashSet<>();
        prefixes = new HashSet<>();

        for(String word : wordList){
            word = word.trim().toLowerCase();
            if(word.isEmpty()) continue;
            words.add(word);
            for(int i=1;i<=word.length();i++){
                prefixes.add(word.substring(0,i));
            }
        }
    }

    private void loadDictionary(String file){
        /** dictionary loader

        Reads all lines from the dictionary file and stores them
        in a HashSet for O(1) lookup. Each word also generates
        prefixes used to terminate search paths early.
        */

        try{

            List<String> lines = Files.readAllLines(Path.of(file));

            for(String word : lines){

                word = word.trim().toLowerCase();

                if(word.isEmpty())
                    continue;

                words.add(word);

                for(int i=1;i<=word.length();i++){

                    prefixes.add(word.substring(0,i));

                }
            }

        }catch(Exception e){

            System.out.println("Dictionary load failed: " + e.getMessage());

        }
    }

    public boolean isWord(String w){
        /** dictionary lookup

        Returns true if the provided string exists as a full word
        in the dictionary.
        */

        return words.contains(w);
    }

    public boolean isPrefix(String p){
        /** prefix lookup

        Returns true if the provided string is a valid prefix
        of at least one word in the dictionary.
        */

        return prefixes.contains(p);
    }

    public List<String> getWords(){
        /** word list getter

        Returns a sorted list of all words in the dictionary.
        */

        List<String> sorted = new ArrayList<>(words);
        sorted.sort(String::compareTo);
        return sorted;
    }
}