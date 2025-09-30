package bwgroup4;

import java.util.Scanner;

public class ClientMenu {
    private final Scanner scanner;

    public ClientMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu Utente ---");
            System.out.println("1. Acquista biglietto");
            System.out.println("2. Acquista abbonamento");
            System.out.println("3. Verifica validità abbonamento");
            System.out.println("4. Vidima biglietto");
            System.out.println("5. Esci");
            System.out.print("Scegli un'opzione: ");
            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1":
                    acquistaBiglietto();
                    break;
                case "2":
                    acquistaAbbonamento();
                    break;
                case "3":
                    verificaAbbonamento();
                    break;
                case "4":
                    vidimaBiglietto();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    private void acquistaBiglietto() {
        System.out.println("[Stub] Acquisto biglietto");
    }

    private void acquistaAbbonamento() {
        System.out.println("[Stub] Acquisto abbonamento");
    }

    private void verificaAbbonamento() {
        System.out.println("[Stub] Verifica validità abbonamento");
    }

    private void vidimaBiglietto() {
        System.out.println("[Stub] Vidimazione biglietto");
    }
}

