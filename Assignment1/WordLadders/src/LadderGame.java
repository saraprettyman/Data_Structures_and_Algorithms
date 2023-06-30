import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class LadderGame {
    ArrayList<ArrayList<String>> organizedWords = new ArrayList<>();

    public LadderGame(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }

    public void play(String start, String end) {
        // TODO: Write some good stuff here
        // Creating copy of un-removed dictionary
        ArrayList<ArrayList<String>> copyDictionary = new ArrayList<>();
        for (ArrayList<String> organizedWord : organizedWords) {
            copyDictionary.add(new ArrayList<>(organizedWord));
        }
        // Casting
        start = start.toLowerCase();
        end = end.toLowerCase();
        // Verify start = end length
        boolean verify = (start.length() == end.length() && organizedWords.get(start.length()).contains(start) && organizedWords.get(end.length()).contains(end));
        if (!verify){
            throw new IllegalArgumentException("Start and end values are not adequate to use for ladder");
        }
        // Create initial ladder
        Queue<WordInfo> partialQueue = new Queue<>();
        int totalEnqueues = 1;
        partialQueue.enqueue(new WordInfo(start, totalEnqueues, start));
        // While queue is not empty and word ladder is not complete
        while (!partialQueue.isEmpty()) {
            // Create a sub array list dictionary with words of the same number of letters
            WordInfo tester = partialQueue.dequeue();
            ArrayList<String> oneAwayList = oneAway(tester.getWord(), true);
            for (String testWord : oneAwayList) {
                if (testWord.equals(end)) {
                    // finding total number of moves so far
                    String[] temp = tester.getHistory().split("\\s+");
                    int moves = temp.length;
                    System.out.printf("%s -> %s : %d Moves [%s %s] total enqueues %d \n", start, end, moves, tester.getHistory(), end, totalEnqueues);
                    // Reset dictionary
                    organizedWords = new ArrayList<>();
                    for (ArrayList<String> newElement : copyDictionary) {
                        organizedWords.add(new ArrayList<>(newElement));
                    }
                    return;
                }
                // finding total number of moves so far
                String[] temp = tester.getHistory().split("\\s+");
                int moves = temp.length;
                totalEnqueues++;
                partialQueue.enqueue(new WordInfo(testWord, moves , tester.getHistory() + " " + testWord));
            }
        }
        System.out.printf("%s -> %s : No Ladder was found \n", start, end);
        // Resetting dictionary
        organizedWords = new ArrayList<>();
        for (ArrayList<String> newElement : copyDictionary) {
            organizedWords.add(new ArrayList<>(newElement));
        }
    }

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
    private int diff(String word1, String word2) {
        int letter = 0;
        for (int i = 0; i < word2.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                letter++;
            }
        }
        return letter;
    }
}