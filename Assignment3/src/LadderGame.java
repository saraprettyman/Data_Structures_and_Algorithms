import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class LadderGame {
    ArrayList<ArrayList<String>> organizedWords = new ArrayList<>();

    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public abstract void play(String start, String end);

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        // TODO: Write some good stuff here
        // Initialization of solution array
        ArrayList<String> oneAwayWords = new ArrayList<>();
        // Create a sub array list with words of the same number of letters
        ArrayList<String> words = organizedWords.get(word.length());
        // Create an initial ladder (using the WordInfo class),
        // consisting of the start word, and add it to a partial solution queue
        for (String testWord : words) {
            if (diff(word, testWord) == 1) {
                oneAwayWords.add(testWord);

            }
        }
        if (withRemoval) {
            for (String testWord : oneAwayWords) {
                organizedWords.get(word.length()).remove(testWord);
            }
        }
        return oneAwayWords;
    }

    public void listWords(int length, int howMany) {
        // TODO: Write some good stuff here
        ArrayList<String> subList = organizedWords.get(length);
        for (int i = 0; i < howMany; i++) {
            System.out.println(subList.get(i));
        }

    }

    /*
        Reads a list of words from a file, putting all words of the same length into the same array.
     */


    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }

            // TODO: You need to do something here to organize the words into groups/arrays of words with the same size
            // Create an arrayList as long as longestWord
            for (int j = 0; j <= longestWord; j++) {
                organizedWords.add(new ArrayList<>());
            }
            // Create sub arrays of words of same size
            for (String word : allWords) {
                organizedWords.get(word.length()).add(word);
            }

        } catch (IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }

    // Finding the difference of two words of the same length
    public int diff(String word1, String word2) {
        int letter = 0;
        for (int i = 0; i < word2.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                letter++;
            }
        }
        return letter;
    }
}