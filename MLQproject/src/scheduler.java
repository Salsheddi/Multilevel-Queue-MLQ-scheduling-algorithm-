import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class scheduler {
    private static ArrayList<PCB> Q1 = new ArrayList<>();
    private static ArrayList<PCB> Q2 = new ArrayList<>();
    private static ArrayList<PCB> scheduledOrder = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Enter process information");
            System.out.println("2. Report detailed information about each process and different scheduling criteria");
            System.out.println("3. Exit the program");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    enterProcessInformation(scanner);
                    break;
                case 2:
                    reportInformation();
                    break;
                case 3:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
        
        scanner.close();
    }

    private static void enterProcessInformation(Scanner scanner) {
        System.out.print("Enter the total number of processes in the system: ");
        int totalProcesses = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 1; i <= totalProcesses; i++) {
            System.out.println("Process " + i + ":");
            System.out.print("Enter process priority (1 or 2): ");
            int priority = scanner.nextInt();

            // make sure that priority vaild
            while (priority > 2 || priority < 1) { 
                System.out.println("Enter process priorty for process #" + (i + 1));
                priority = scanner.nextInt();
            }
            
            System.out.print("Enter arrival time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter CPU burst time: ");
            int cpuBurstTime = scanner.nextInt();
            scanner.nextLine(); // Consume newline

           PCB pcb = new PCB("" + i, priority, arrivalTime, cpuBurstTime);
            if (priority == 1)
                Q1.add(pcb);
            else
                Q2.add(pcb);
        }
    }

    private static void scheduleProcesses() {
        int currentTime = 0; // the start of the chantt chart is 0

        while (!Q1.isEmpty() || !Q2.isEmpty()) {
            if (!Q1.isEmpty()) {// by having Q1 checked first we ensure befor processing any low priority process that theres no high prority process
                PCB process = Q1.remove(0);
                scheduledOrder.add(process);
                process.ResponseTime = currentTime - process.ArrivalTime;
                process.StartTime = currentTime;
                process.terminationTime = Math.min(currentTime + 3, currentTime + process.CPU_burst);
                process.TurnArroundTime= process.terminationTime - process.ArrivalTime;
                process.WaitingTime= process.TurnArroundTime - process.CPU_burst;
                currentTime = process.terminationTime;
            } else {
                // SJF algorithm for Q2
                Q2.sort((p1, p2) -> p1.CPU_burst - p2.CPU_burst);
                PCB process = Q2.remove(0);
                scheduledOrder.add(process);
                process.ResponseTime = currentTime - process.ArrivalTime;
                process.StartTime = currentTime;
                process.terminationTime = currentTime + process.CPU_burst;
                process.TurnArroundTime = process.terminationTime - process.ArrivalTime;
                process.WaitingTime = process.TurnArroundTime - process.CPU_burst;
                currentTime = process.terminationTime;
            }
        }
    }

    private static void reportInformation() { 
        scheduleProcesses();

        
        if (scheduledOrder.isEmpty()) {
            System.out.println("No processes scheduled yet.");
            return;
        }

       

        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        double avgResponseTime = 0;

        try {
            FileWriter writer = new FileWriter("Report.txt");
            writer.write("Scheduling order of the processes: ");
            writer.write("[");
            System.out.print('[');
            for (PCB process : scheduledOrder) {
                writer.write(process.PId + " | ");
                System.out.print(process.PId+" | ");

            }
            writer.write("\n\n");
          

            for (PCB process : scheduledOrder) {
                writer.write(process.toString()+"\n");
                System.out.println(process.toString());
                avgTurnaroundTime += process.TurnArroundTime;
                avgWaitingTime += process.WaitingTime;
                avgResponseTime += process.ResponseTime;
            }

            avgTurnaroundTime /= scheduledOrder.size();
            avgWaitingTime /= scheduledOrder.size();
            avgResponseTime /= scheduledOrder.size();

            writer.write("\nAverage Turnaround Time: " + avgTurnaroundTime + "\n");
            writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
            writer.write("Average Response Time: " + avgResponseTime + "\n");
            System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime + "\n");
           System.out.println("Average Waiting Time: " + avgWaitingTime + "\n");
            System.out.println("Average Response Time: " + avgResponseTime + "\n");

            writer.close();

            System.out.println("Report has been written to Report.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
