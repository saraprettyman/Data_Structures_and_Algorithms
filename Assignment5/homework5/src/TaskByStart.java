public class TaskByStart extends Task implements Comparable<Task>{
    public TaskByStart(int ID, int start, int deadline, int duration) {
        super(ID, start, deadline, duration);
    }

    @Override
    public int compareTo(Task o) {
        if(this.start > o.start){
            return 1;
        }else if(this.start < o.start){
            return -1;
        }else{
            if (this.duration > o.duration){
                return 1;
            }else if(this.duration < o.duration){
                return -1;
            }else{
                return 0;
            }
        }
    }
}
