import java.util.*;

public class WordFreqInfo {
    private String word;
    private int occurCount;
    private ArrayList<Frequency> followList;

    public WordFreqInfo(String word, int count) {
        this.word = word;
        this.occurCount = count;
        this.followList = new ArrayList<Frequency>();
    }

    public WordFreqInfo() {

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word :" + word+":");
        sb.append(" (" + occurCount + ") : ");
        for (Frequency f : followList) {
            sb.append(f.toString());
        }

        return sb.toString();
    }

    public void updateFollows(String follow) {
        this.occurCount++;
        boolean updated = false;
        for (Frequency f : followList) {
            if (follow.compareTo(f.follow) == 0) {
                f.followCount++;
                updated = true;
            }
        }
        if (!updated) {
            followList.add(new Frequency(follow, 1));
        }
    }

    public int getOccurCount() {
        return this.occurCount;
    }
    // Added by Sara.P
    public String getFollowWord(int count) {
        // create a list of all follow list
        ArrayList<String> followWords = new ArrayList<>();
        for (Frequency f : followList){
            int followCount = f.followCount;
            for (int i = 0; i < followCount; i++){
                followWords.add(f.follow);
            }
        }
        // randomly select which index to use
        int rnd = (int)(Math.random() * count);
        return followWords.get(rnd);
    }

    public int createCount() {
        // find n
        int n = 0;
        for (Frequency f : followList){
            n = n + f.followCount;
        }
        // randomize count
        Random rnd = new Random();
        int count = rnd.nextInt(n);
        return count;
    }
    // end of added by Sara.P

    private class Frequency {
        String follow;
        int followCount;

        public Frequency(String follow, int ct) {
            this.follow = follow;
            this.followCount = ct;
        }

        @Override
        public String toString() {
            return follow + " [" + followCount + "] ";
        }

        @Override
        public boolean equals(Object f2) {
            return this.follow.equals(((Frequency)f2).follow);
        }
    }
}
