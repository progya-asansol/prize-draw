import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Participant {
    int id;
    String name;
    String contact;

    Participant(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }
}

public class PrizeDraw {
    private static List<Participant> participants = new ArrayList<>();
    private static int idCounter = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        while (true) {
            showMenu();
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                scanner.nextLine();
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addParticipant();
                case 2 -> viewParticipants();
                case 3 -> startLottery();
                case 4 -> {
                    System.out.println("Exiting program.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Mall Lottery System ===");
        System.out.println("1. Add Participant");
        System.out.println("2. View Participants");
        System.out.println("3. Start Lottery");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addParticipant() {
        System.out.print("Enter participant name: ");
        String name = scanner.nextLine();

        System.out.print("Enter participant contact: ");
        String contact = scanner.nextLine();

        if (isContactDuplicate(contact)) {
            System.out.println("Error: Contact already exists! Duplicate entries not allowed.");
            return;
        }

        participants.add(new Participant(idCounter++, name, contact));
        System.out.println("Participant '" + name + "' added successfully.");
    }

    private static boolean isContactDuplicate(String contact) {
        for (Participant p : participants) {
            if (p.contact.equals(contact)) return true;
        }
        return false;
    }

    private static void viewParticipants() {
        if (participants.isEmpty()) {
            System.out.println("No participants registered.");
            return;
        }
        System.out.println("\n--- Participants ---");
        for (Participant p : participants) {
            System.out.println("ID: " + p.id + ", Name: " + p.name + ", Contact: " + p.contact);
        }
    }

    private static void startLottery() {
        if (participants.size() < 2) {
            System.out.println("At least 2 participants are required to start the lottery.");
            return;
        }

        Random random = new Random();
        int k = random.nextInt(5) + 1;
        System.out.println("Randomly chosen step count (k): " + k);

        List<Participant> list = new ArrayList<>(participants);
        StringBuilder log = new StringBuilder();

        int index = 0;
        while (list.size() > 1) {
            index = (index + k - 1) % list.size();
            Participant eliminated = list.remove(index);
            System.out.println("Eliminated: ID " + eliminated.id + ", Name: " + eliminated.name);
            log.append("Eliminated: ID ").append(eliminated.id)
               .append(", Name: ").append(eliminated.name)
               .append(", Contact: ").append(eliminated.contact).append("\n");
        }

        Participant winner = list.get(0);
        System.out.println("\n🎉 Winner 🎉");
        System.out.println("ID: " + winner.id + ", Name: " + winner.name + ", Contact: " + winner.contact);
        log.append("Winner: ID ").append(winner.id)
           .append(", Name: ").append(winner.name)
           .append(", Contact: ").append(winner.contact).append("\n");

        saveLog(log.toString());
        participants.clear();
        idCounter = 1;
    }

    private static void saveLog(String content) {
        try (FileWriter writer = new FileWriter("lottery_results.txt")) {
            writer.write(content);
            System.out.println("Lottery results saved to lottery_results.txt");
        } catch (IOException e) {
            System.out.println("Error saving lottery results: " + e.getMessage());
        }
    }
}
