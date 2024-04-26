import java.io.*;
import java.util.*;
 class SchedulerX {

    private List<PCB> Q1;
    private List<PCB> Q2;
    private String orderChart = "";

    int clockTime = 0;
    int quantum = 3;
    int Counter = 0;

    public SchedulerX(List<PCB> Q1, List<PCB> Q2) {
        this.Q1 = Q1;
        this.Q2 = Q2;

        sortByArrivalTime(this.Q1);
        sortByArrivalTime(this.Q2);
    }

    public void run() {
        PCB excProcess = null;
        List<PCB> rQ1 = new ArrayList<>();
        List<PCB> rQ2 = new ArrayList<>();

        clockTime = 0;
        Counter = 0;
        orderChart = "";

        for (PCB process : Q1) {
            process.timeInCPU = 0;
            process.StartTime = 0;
            process.terminationTime = 0;
        }

        for (PCB process : Q2) {
            process.timeInCPU = 0;
            process.StartTime = 0;
            process.terminationTime = 0;
        }

        while (!Q1.isEmpty() || !Q2.isEmpty() || !rQ1.isEmpty() || !rQ2.isEmpty() || excProcess != null) {

            while (!Q1.isEmpty() && Q1.get(0).ArrivalTime <= clockTime) {
                PCB process = Q1.remove(0);
                rQ1.add(process);
            }

            while (!Q2.isEmpty() && Q2.get(0).ArrivalTime <= clockTime) {
                PCB process = Q2.remove(0);
                rQ2.add(process);
                sortByBurstTime(rQ2);
            }

            if (excProcess == null) {
                PCB process = null;
                if (!rQ1.isEmpty()) {
                    process = rQ1.remove(0);
                } else if (!rQ2.isEmpty()) {
                    process = rQ2.remove(0);
                }

                if (process != null)
                    excProcess = execute(process);

            } else {
                if (excProcess.priority == 1 && !rQ1.isEmpty() && Counter == quantum) {
                    rQ1.add(excProcess);
                    excProcess = execute(rQ1.remove(0));

                } else if (excProcess.priority == 2 && !rQ1.isEmpty()) {
                    rQ2.add(excProcess);
                    sortByBurstTime(rQ2);
                    excProcess = execute(rQ1.remove(0));
                }
            }

            clockTime++;

            if (excProcess != null) {
                excProcess.timeInCPU++;
                Counter++;
                if (excProcess.timeInCPU == excProcess.CPU_burst) {
                    terminate(excProcess);
                    excProcess = null;
                }
            }
        }
    }

    private PCB execute(PCB process) {
        Counter = 0;
        orderChart += process.PId + " | ";

        if (process.timeInCPU == 0)
            process.StartTime = clockTime;

        return process;
    }

    private void terminate(PCB process) {
        process.terminationTime = clockTime;
        process.WaitingTime = process.terminationTime - process.ArrivalTime - process.CPU_burst;
        process.ResponseTime = process.StartTime - process.ArrivalTime;
        process.TurnArroundTime = process.terminationTime - process.ArrivalTime;
    }

    public String getOrderChart() {
        return orderChart;
    }

    private void sortByBurstTime(List<PCB> array) {
        Collections.sort(array, Comparator.comparingInt(a -> a.CPU_burst));
    }

    private void sortByArrivalTime(List<PCB> array) {
        Collections.sort(array, Comparator.comparingInt(a -> a.ArrivalTime));
    }
}
public class scheduler {

    static Scanner scr = new Scanner(System.in);
    
    static SchedulerX scheduler;
    static List<PCB> array = new ArrayList<>();
    static List<PCB> Q1 = new ArrayList<>();
    static List<PCB> Q2 = new ArrayList<>();
    

    public static void main(String[] args) {

        int choice = 0;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Enter Processes Information");
            System.out.println("2. Report detailed information about each process and different scheduling criteria");
            System.out.println("3. Exit the program");
            System.out.print("Enter your choice: ");
            if (scr.hasNextInt()) {
                choice = scr.nextInt();
                switch (choice) {
                    case 1:
                      enterProcessInformation();
                        break;
                    case 2:
                        if (array.isEmpty()) {
                            System.out.println("theres No processes.");
                        } else {
                            scheduler = new SchedulerX(Q1, Q2);
                            scheduler.run();

                            printConsole();
                            printFile();
                        }
                        break;
                    case 3:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println(" Please enter valid  choice.");
                scr.next(); 
            }

        } while (choice != 3);
    }

    public static void enterProcessInformation() {
        System.out.println("Please enter the number of processes:");
        if (scr.hasNextInt()) {
            int numOfProcesses = scr.nextInt();

            if (numOfProcesses <= 0) {
                System.out.println("Number of processes must be > 0");
                return;
            }

            Q1.clear();
            Q2.clear();
            array.clear();

           

            for (int i = 0; i < numOfProcesses; i++) {
                System.out.println("Process [" + (i + 1) + "]:");
                System.out.print("Arrival Time: ");
                if (scr.hasNextInt()) {
                    int arrivalTime = scr.nextInt();

                    if (arrivalTime < 0) {
                        System.out.println("Arrival Time must be >= 0");
                        return;
                    }

                    System.out.print("CPU burst: ");
                    if (scr.hasNextInt()) {
                        int burstTime = scr.nextInt();
                        if (burstTime <= 0) {
                            System.out.println("CPU burst must be > 0");
                            return;
                        }

                        System.out.print("Priority: ");
                        if (scr.hasNextInt()) {
                            int priority = scr.nextInt();
                            if (priority != 1 && priority != 2) {
                                System.out.println("Priority must be either 1 or 2");
                                return;
                            }

                            array.add(new PCB("P" + (i + 1),priority , arrivalTime,burstTime ));
                        } else {
                            System.out.println("Please enter a valid  priority.");
                            scr.next(); // Clear the invalid input from the scanner
                            return;
                        }
                    } else {
                        System.out.println("Please enter a valid CPU burst.");
                        scr.next(); // Clear the invalid input from the scanner
                        return;
                    }
                } else {
                    System.out.println("Please enter a valid arrival time.");
                    scr.next(); // Clear the invalid input from the scanner
                    return;
                }
            }

            for (PCB process : array) {
                if (process.priority == 1)
                    Q1.add(process);
                else
                    Q2.add(process);
            }
        } else {
            System.out.println("Invalid input! Please enter a valid integer for the number of processes.");
            scr.next(); // Clear the invalid input from the scanner
        }
    }

    public static void printConsole() {
        System.out.println("Processes Information:");
        System.out.println("----------------------");
        for (PCB process : array) {
            System.out.println(process);
            }

        System.out.println();
        System.out.println(" Gantt Chart: | " + scheduler.getOrderChart());
        System.out.println();

        int size = array.size();
        double totalTurnAround = 0, totalWait = 0, totalResponse = 0;

        for (PCB process : array) {
            totalWait += process.WaitingTime;
            totalTurnAround += process.TurnArroundTime;
            totalResponse += process.ResponseTime;
        }

        System.out.println("Processes Scheduling Criteria:");
        System.out.println("-----------------------------");
        System.out.printf("Avg Turnaround Time : %.3f \n", totalTurnAround / size);
        System.out.printf("Avg Waiting Time    : %.3f \n", totalWait / size);
        System.out.printf("Avg Response Time   : %.3f \n", totalResponse / size);
        System.out.println();
    }

    public static void printFile() {
        try {
            PrintWriter pw = new PrintWriter("Report.txt");

            pw.println("Processes Information:");
            pw.println("----------------------");
            for (PCB process : array){
                pw.println(process);
                 }

            pw.println();
            pw.println(" Gantt Chart: | " + scheduler.getOrderChart());
            pw.println();

            int size = array.size();
            double totalTurnAround = 0, totalWait = 0, totalResponse = 0;

            for (PCB process : array) {
                totalWait += process.WaitingTime;
                totalTurnAround += process.TurnArroundTime;
                totalResponse += process.ResponseTime;
            }

            pw.println("Processes Scheduling Criteria:");
            pw.println("-----------------------------");
            pw.printf("Avg Turnaround Time : %.3f \n", totalTurnAround / size);
            pw.printf("Avg Waiting Time    : %.3f \n", totalWait / size);
            pw.printf("Avg Response Time   : %.3f \n", totalResponse / size);
            pw.println();

            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}



