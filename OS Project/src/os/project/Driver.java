package os.project;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalProcesses;
        PCB[] q1;
        PCB[] q2;

        System.out.println("Enter the number of processes: ");
        totalProcesses = scanner.nextInt();

        q1 = new PCB[totalProcesses];
        q2 = new PCB[totalProcesses];

        // Prompt user to enter process information and create PCB objects
        for (int i = 0; i < totalProcesses; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ":");
            // Read priority, arrival time, and CPU burst time
            int priority = scanner.nextInt();
            int arrivalTime = scanner.nextInt();
            int cpuBurstTime = scanner.nextInt();

            // Create PCB object based on priority and add to the respective queue
            PCB pcb = new PCB("P" + (i + 1), priority, arrivalTime, cpuBurstTime);
            if (priority == 1) {
                q1[i] = pcb;
            } else if (priority == 2) {
                q2[i] = pcb;
            }
        }
}
}
