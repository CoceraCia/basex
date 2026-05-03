package com.coceracia.basex;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BaseXService service = new BaseXService();
        runMenu(service);
    }

    private static void runMenu(BaseXService service) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        try {
            while (running) {
                printMenu();
                String option = sc.nextLine().trim();

                switch (option) {
                    case "1" -> service.connectCheck();
                    case "2" -> service.createDatabase();
                    case "3" -> service.queryAuthors();
                    case "4" -> service.addPremios();
                    case "5" -> service.showDatabase();
                    case "6" -> running = false;
                    default -> System.out.println("Invalid option. Please choose 1-6.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }

    }

    private static void printMenu() {
        System.out.println();
        System.out.println("-------- Activity 2 - BaseX connection --------");
        System.out.println("1. Connect to BaseX");
        System.out.println("2. Create AutoresDB");
        System.out.println("3. Query Authors");
        System.out.println("4. Add Premios to author id=1");
        System.out.println("5. Show Database");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }
}
