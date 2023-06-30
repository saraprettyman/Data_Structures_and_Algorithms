import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment5 {
    public static void main(String[] args) {
        simpleQueueTest();
        scheduleTasks("taskset1.txt");
        scheduleTasks("taskset2.txt");
        scheduleTasks("taskset3.txt");
        scheduleTasks("taskset4.txt");
        scheduleTasks("taskset5.txt");
    }

    public static void scheduleTasks(String taskFile) {
        // TODO: Uncomment code here as you complete the tasks and scheduling algorithm
        ArrayList<Task> tasksByDeadline = new ArrayList<>();
        ArrayList<Task> tasksByStart = new ArrayList<>();
        ArrayList<Task> tasksByDuration = new ArrayList<>();

        readTasks(taskFile, tasksByDeadline, tasksByStart, tasksByDuration);

        schedule("Deadline Priority : " + taskFile, tasksByDeadline);
        schedule("Startime Priority : " + taskFile, tasksByStart);
        schedule("Duration priority : " + taskFile, tasksByDuration);
    }

    public static void simpleQueueTest() {
        // TODO: Uncomment code here for a simple test of your priority queue code
//        PriorityQueue<Integer> queue = new PriorityQueue<>();
//        queue.enqueue(9);
//        queue.enqueue(7);
//        queue.enqueue(5);
//        queue.enqueue(3);
//        queue.enqueue(1);
//        queue.enqueue(10);
//
//        while (!queue.isEmpty()) {
//            System.out.println(queue.dequeue());
//        }
    }

    /**
     * Reads the task data from a file, and creates the three different sets of tasks for each
     */
    public static void readTasks(String filename,
                                 ArrayList<Task> tasksByDeadline,
                                 ArrayList<Task> tasksByStart,
                                 ArrayList<Task> tasksByDuration) {
        // TODO: Write your task reading code here
        // initialize variables
        File taskFile = new File(filename);
        Scanner scanner = null;
        // Error check, see if file is found
        try {
            scanner = new Scanner(taskFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create an arrayList of task
        ArrayList<ArrayList<Integer>> taskList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            ArrayList<Integer> task = new ArrayList<>();
            String line = scanner.nextLine();
            String[] values = line.trim().split("\\s+");
            for (String value : values) {
                task.add(Integer.parseInt(value));
            }
            taskList.add(task);
        }
        // input task into Task class
        for (int j = 0; j < taskList.size(); j++) {
            ArrayList<Integer> currentTask = taskList.get(j);
            int ID = j + 1;
            int start = currentTask.get(0);
            int deadline = currentTask.get(1);
            int duration = currentTask.get(2);
            tasksByDeadline.add(new TaskByDeadline(ID, start, deadline, duration));
            tasksByStart.add(new TaskByStart(ID, start, deadline, duration));
            tasksByDuration.add(new TaskByDuration(ID, start, deadline, duration));
        }
    }

    /**
     * Given a set of tasks, schedules them and reports the scheduling results
     */
    public static void schedule(String label, ArrayList<Task> tasks) {
        // TODO: Write your scheduling algorithm here
        System.out.println(label);
        // initialize variable
        int time = 1;
        int totalLateTime = 0;
        int lateCount = 0;
        PriorityQueue<Task> TaskQueue = new PriorityQueue<>();
        while (time <= 18) {
            for (Task currentTask : tasks) {
                if ((currentTask.start == time)) {
                    TaskQueue.enqueue(currentTask);
                }
            }
            Task currentPriority = TaskQueue.dequeue();

            // If no task available to work on
            if (currentPriority == null) {
                System.out.println("Time " + time + ": ---");
                time++;
                continue;
            }
            // One task is completed during one time moment
            System.out.print("Time " + time + ": " + currentPriority);
            currentPriority.duration--;
            // If task is not complete, and may be late
            if (currentPriority.duration == 0) {
                System.out.print(" **");
                int late = time - currentPriority.deadline;
                if (late>0){
                    System.out.print(" Late " + late);
                    lateCount++;
                    totalLateTime = totalLateTime + late;
                }
            }

            // If task is not complete
            if (currentPriority.duration != 0) {
                TaskQueue.enqueue(currentPriority);
            }
            System.out.println();
            time++;
        }
        System.out.println("Tasks late " + lateCount +" Total Late " + totalLateTime);
        System.out.println();

    }
}
