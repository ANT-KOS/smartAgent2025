package ak.main.ontology;

class Task implements Comparable<Task> {
    int priority;  // Priority level: higher values indicate higher priority
    String taskDescription;

    Task(int priority, String taskDescription) {
        this.priority = priority;
        this.taskDescription = taskDescription;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }
}