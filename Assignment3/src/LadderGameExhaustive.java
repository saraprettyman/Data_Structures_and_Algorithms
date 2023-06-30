import java.util.ArrayList;

public class LadderGameExhaustive extends LadderGame{
    public LadderGameExhaustive(String dictionaryFile) {
        super(dictionaryFile);
    }

    @Override
    public void play(String start, String end) {
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
                    System.out.printf("Seeking exhaustive solution from %s -> %s \n[%s %s] total enqueues %d \n", start, end, tester.getHistory(), end, totalEnqueues);
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
}
