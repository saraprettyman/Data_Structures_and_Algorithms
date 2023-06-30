public class TaskByDeadline extends Task implements Comparable<Task>{
    public TaskByDeadline(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(this.deadline, o.deadline);
    }
}
