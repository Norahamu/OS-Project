package os.project;
public class PCB {
    private String processID;
    private int priority;
    private int arrivalTime;
    private int cpuBurstTime;
    private int startTime;
    private int terminationTime;
    private int turnaroundTime;
    private int waitingTime;
    private int responseTime;

    // Constructor
    public PCB(String processID, int priority, int arrivalTime, int cpuBurstTime) {
        this.processID = processID;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurstTime = cpuBurstTime;
        this.startTime = -1; //initially
        this.terminationTime = -1; //initially

    }

}
