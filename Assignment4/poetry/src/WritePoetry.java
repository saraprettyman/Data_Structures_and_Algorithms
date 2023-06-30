import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class WritePoetry extends WordFreqInfo {
    public WritePoetry() {
        super();
    }

    /**
     * @param file contains the document to read
     * @param startWord is the first word of the poem to generate
     * @param length is how many words (including punctuation) to generate
     */
    public String writePoem(String file, String startWord, int length, boolean printHashtable) {
       HashTable<String, WordFreqInfo> poemHashed = createHashTable(file);

        if(printHashtable){
            System.out.println(poemHashed.toString(100));
        }

        String currentWord = startWord;
        StringBuilder poem = new StringBuilder();
        poem.append(currentWord).append(" ");

        for (int i = 0; i < length; i++){
            WordFreqInfo startWordHashed = poemHashed.find(currentWord);
            String followWord = startWordHashed.getFollowWord(startWordHashed.createCount());
            // If punctuation detected, go to new line
            if (followWord.matches("[.?!,]")){
                if (poem.toString().endsWith(" ")){
                    poem.deleteCharAt(poem.length() - 1);
                }
                poem.append(followWord).append(System.lineSeparator());
            }else {
                poem.append(followWord).append(" ");
            }
            currentWord = followWord;
        }
        if (!poem.toString().endsWith(".") && !poem.toString().endsWith("?") && !poem.toString().endsWith("!") && !poem.toString().endsWith(System.lineSeparator())) {
            if (poem.toString().endsWith(" ")){poem.deleteCharAt(poem.length() - 1);}
            poem.append(".");
        }
    return poem.toString();
    }

    private HashTable createHashTable(String file){
        // initialize variables
        File poem = new File(file);
        Scanner scanner = null;
        HashTable<String,WordFreqInfo> hashTable = new HashTable<>();


        // Error check, see if file is found
        try {
            scanner = new Scanner(poem);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Read poem into an array, one line at a time
        ArrayList<String> lines = new ArrayList();
        for (int i = 0; scanner.hasNextLine(); i++){
            String line = scanner.nextLine();
            if (!Objects.equals(line, "")){lines.add(line);}
        }
        // Split sentence into individual words to load into hashtable
        for (int j = 0; j < lines.size(); j++) {
            String sentence = lines.get(j).toLowerCase();
            String[] words = sentence.split("\\s|(?=[!.,?])|(?<=[!.,?])");
            for (int i = 0; i < words.length; i++) {
                // Set word varaible
                String word = words[i];
                // Prevent adding empty space
                if (word.isEmpty()) {
                    continue;
                }
                // If word is not already hashed
                if (!hashTable.contains(word)){
                    hashTable.insert(word, new WordFreqInfo(word, 0));
                }
                WordFreqInfo wordHashed = hashTable.find(word);
                // UPDATE FOLLOW WORDS
                // check following word
                if (i < words.length - 1){
                    String followingWord = words[i+1].toLowerCase();
                    wordHashed.updateFollows(followingWord);
                }else {
                    // if not the last word in line, check next line first word
                    if (j < lines.size() - 1){
                        String[] followingLine = lines.get(j+1).split(" ");
                        wordHashed.updateFollows(followingLine[0].toLowerCase());
                    }
                }

            }
        }
        return hashTable;
    }



}
