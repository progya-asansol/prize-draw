import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PrizeDraw {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Random Prize Draw ===");

        // Step 1: Get number of participants
        System.out.print("Enter number of participants: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (n < 1) {
            System.out.println("At least one participant required!");
            return;
        }

        // Step 2: Get participant names
        List<String> participants = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name of participant " + (i + 1) + ": ");
            String name = scanner.nextLine();
            participants.add(name);
        }

        // Step 3: Ask for number of winners
        System.out.print("How many winners do you want? ");
        int winnersCount = scanner.nextInt();

        if (winnersCount < 1 || winnersCount > n) {
            System.out.println("Invalid number of winners!");
            return;
        }

        // Step 4: Shuffle and pick winners
        Collections.shuffle(participants);
        List<String> winners = participants.subList(0, winnersCount);

        // Step 5: Display winners
        System.out.println("\n🎉 Winners 🎉");
        for (String winner : winners) {
            System.out.println("- " + winner);
        }
    }
}
