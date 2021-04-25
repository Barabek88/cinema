package cinema;

import java.util.Scanner;

public class Cinema {
    static private final Scanner scanner = new Scanner(System.in);
    static private final int maxSeatsInSmallRoom = 60;
    static private final int costSeatInSmallRoom = 10;
    static private final int costFrontSeatInLargeRoom = 10;
    static private final int costBackSeatInLargeRoom = 8;
    static private int rows;
    static private int seatsInRow;
    static private int[][] seatsCost;
    static private String[][] seats;

    public static void main(String[] args) {
        boolean needContinueReq = true;

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        seatsInRow = scanner.nextInt();

        fillSeats();
        fillSeatsCost();

        while (needContinueReq) {
            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int itemChosen = scanner.nextInt();

            switch (itemChosen) {
                case 1:
                    showSeats();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    showStatistics();
                    break;
                case 0:
                    needContinueReq = false;
                    break;
                default:
                    System.out.println("unknown operation");
                    break;
            }
        }
    }

    private static void fillSeats() {
        seats = new String[rows][seatsInRow];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsInRow; j++) {
                seats[i][j] = "S";
            }
        }
    }

    private static void fillSeatsCost() {
        int allSeats = rows * seatsInRow;
        int rowsBackStart = rows - rows / 2 - rows % 2;

        seatsCost = new int[rows][seatsInRow];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsInRow; j++) {
                if (allSeats <= maxSeatsInSmallRoom) {
                    seatsCost[i][j] = costSeatInSmallRoom;
                } else if (i < rowsBackStart) {
                    seatsCost[i][j] = costFrontSeatInLargeRoom;
                } else {
                    seatsCost[i][j] = costBackSeatInLargeRoom;
                }
            }
        }
    }

    private static void showSeats() {
        System.out.print("Cinema: \n  ");

        for (int i = -1; i < rows; i++) {
            if (i > -1) {
                System.out.print("\n" + (i + 1) + " ");
            }

            for (int j = 0; j < seatsInRow; j++) {
                System.out.print(i == -1 ? (j + 1 + " ") : seats[i][j] + " ");
            }
        }
    }

    private static void buyTicket() {
        boolean needContinueReq = true;

        while (needContinueReq) {
            System.out.println("\nEnter a row number:");
            int rowNumber = scanner.nextInt() - 1;

            System.out.println("\nEnter a seat number in that row:");
            int seatNumber = scanner.nextInt() - 1;

            if (rowNumber >= 0 && rowNumber < rows && seatNumber >= 0 && seatNumber < seatsInRow) {
                if ("S".equals(seats[rowNumber][seatNumber])) {
                    System.out.println("Ticket price: $" + seatsCost[rowNumber][seatNumber]);
                    seats[rowNumber][seatNumber] = "B";
                    needContinueReq = false;
                } else {
                    System.out.println("That ticket has already been purchased!");
                }
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    private static void showStatistics() {
        int purchasedTicket = 0;
        int allSeats = rows * seatsInRow;
        int currentIncome = 0;
        int totalIncome = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsInRow; j++) {
                if ("B".equals(seats[i][j])) {
                    purchasedTicket++;
                    currentIncome += seatsCost[i][j];
                }
                totalIncome += seatsCost[i][j];
            }
        }

        System.out.printf("Number of purchased tickets: %d%n", purchasedTicket);
        System.out.printf("Percentage: %.2f%%%n", purchasedTicket * 100.0 / allSeats);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", totalIncome);
    }
}