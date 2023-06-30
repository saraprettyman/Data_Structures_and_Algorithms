import java.util.ArrayList;

public class LadderGamePriority extends LadderGame {
    public LadderGamePriority(String dictionaryFile) {
        super(dictionaryFile);
    }

    @Override
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
        if (!verify) {
            throw new IllegalArgumentException("Start and end values are not adequate to use for ladder");
        }
        // Create initial ladder
        int totalEnqueues = 1;
        int moves = 0;
        int estimatedWork = diff(start, end);
        AVLTree<WordInfoPriority> priorityQueue = new AVLTree<>();
        WordInfoPriority initialState = new WordInfoPriority(start, totalEnqueues, estimatedWork);
        priorityQueue.insert(initialState);
        // reorganize queue by smallest expected total work in priority queue.
        while (!priorityQueue.isEmpty()) { // and a solution has not been found
            WordInfoPriority testing = priorityQueue.deleteMin();
            ArrayList<String> oneAwayList = oneAway(testing.getWord(), true);
            for (String testWord: oneAwayList){
                if(testWord.equals(end)){
                    String[] temp = testing.getHistory().split("\\s+");
                    System.out.printf("Seeking A* solution from %s -> %s \n[%s %s] total enqueues %d \n", start, end, testing.getHistory(), end, totalEnqueues);
                    // Reset dictionary
                    organizedWords = new ArrayList<>();
                    for (ArrayList<String> newElement : copyDictionary) {
                        organizedWords.add(new ArrayList<>(newElement));
                    }
                    return;
                }
                // finding total number of moves so far
                String[] temp = testing.getHistory().split("\\s+");
                moves = temp.length;
                totalEnqueues++;
                estimatedWork = moves + diff(testWord, end);
                priorityQueue.insert(new WordInfoPriority(testWord, moves + 1, estimatedWork,testing.getHistory() + " " + testWord) );
            }
        }
        // Resetting dictionary
        organizedWords = new ArrayList<>();
        for (ArrayList<String> newElement : copyDictionary) {
            organizedWords.add(new ArrayList<>(newElement));
        }
    }

}