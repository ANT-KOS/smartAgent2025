package ak.main.ontology;

public class Task implements Comparable<Task> {
    private int priority = 10;  // Priority level: higher values indicate higher priority
    private String taskDescription = null;

    public Task(int priority, String taskDescription) {
        this.priority = priority;
        this.taskDescription = taskDescription;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    public int getPriority() {
        return priority;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
}