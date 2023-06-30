public class WordInfoPriority implements Comparable<WordInfoPriority> {
    private int estimatedWork;
    private String word;
    private int moves;
    private String history;
    private int priority;

    public WordInfoPriority(String word, int moves, int estimatedWork){
        this.word = word;
        this.moves = moves;
        this.estimatedWork = estimatedWork;
        this.history = word;
        this.priority = estimatedWork;
    }

    public WordInfoPriority(String word, int moves, int estimatedWork, String history) {
        this.word = word;
        this.moves = moves;
        this.estimatedWork = estimatedWork;
        this.history = history;
        this.priority = estimatedWork;
    }

    public String getWord() {
        return this.word;
    }

    public int getMoves() {
        return this.moves;
    }

    public int getPriority() {return this.estimatedWork;}

    public String getHistory() {
        return this.history;
    }

    public void setPriority(int priority) {this.estimatedWork = priority;}

    @Override
    public String toString() {
        return String.format("Word %s Moves %d : History[%s]",
                word, moves, history);
    }

    @Override
    public int compareTo(WordInfoPriority o) {
        if (o.priority > this.priority) {
            return -1;
        }else if (o.priority < this.priority){
            return 1;
        }
        return 0;
    }
}
