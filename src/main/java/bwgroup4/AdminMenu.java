package bwgroup4;

import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner;

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu Amministratore ---");
            System.out.println("1. Statistiche biglietti vidimati");
            System.out.println("2. Gestione mezzi");
            System.out.println("3. Gestione tratte");
            System.out.println("4. Gestione manutenzioni");
            System.out.println("5. Esci");
            System.out.print("Scegli un'opzione: ");
            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1":
                    statisticheBiglietti();
                    break;
                case "2":
                    gestioneMezzi();
                    break;
                case "3":
                    gestioneTratte();
                    break;
                case "4":
                    gestioneManutenzioni();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    private void statisticheBiglietti() {
        System.out.println("[Stub] Statistiche biglietti vidimati");
    }

    private void gestioneMezzi() {
        System.out.println("[Stub] Gestione mezzi");
    }

    private void gestioneTratte() {
        System.out.println("[Stub] Gestione tratte");
    }

    private void gestioneManutenzioni() {
        System.out.println("[Stub] Gestione manutenzioni");
    }
}
