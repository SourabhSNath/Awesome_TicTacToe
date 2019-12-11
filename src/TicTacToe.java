import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicTacToe {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {

        //@formatter:off
        String[][] gameLines =
                       {{"   ", "|", "   ", "|", "   "},
                        {"---", "+", "---", "+", "---"},
                        {"   ", "|", "   ", "|", "   "},
                        {"---", "+", "---", "+", "---"},
                        {"   ", "|", "   ", "|", "   "}};
        //@formatter:on
        System.out.println("-------------------------------------------------------");
        System.out.println("Select 1 to play against another player");
        System.out.println("Select 2 to play against an incredibly stupid CPU");
        System.out.println("-------------------------------------------------------");
        int p2 = scanner.nextInt();

        String player2;
        if (p2 == 1) {
            player2 = "Player2";
        } else {
            player2 = "CPU";
        }

        System.out.println("Enter a position between 1 and 9: ");

        boolean check = true; //Stops when false
        Set<Integer> trackPlayerInput = new HashSet<>(); //keeps track of player input

        int i = 0;
        while (check) {

            if (i % 2 == 0) {
                System.out.print("Enter: ");

                String playerInputString = scanner.next().replaceAll("[^1-9]+", "").trim();
                int playerInput = Integer.parseInt(playerInputString);
                check = enterMark("Player1", playerInput, gameLines, trackPlayerInput);

            } else {

                if (player2.equals("CPU")) {
                    int cpuInput = getPlayerInput(random, trackPlayerInput);
                    if (cpuInput != 0) {
                        System.out.println("AI Tries: " + cpuInput);
                    }
                    check = enterMark(player2, cpuInput, gameLines, trackPlayerInput);
                } else {
                    System.out.println("Enter: ");
                    int playerInput = scanner.nextInt();
                    check = enterMark(player2, playerInput, gameLines, trackPlayerInput);
                }

            }

            getMarkedPos(trackPlayerInput);

            gameLinePrinter(gameLines);
            i++;
        }


    }

    private static void getMarkedPos(Set<Integer> trackPlayerInput) {
        System.out.println("Positions marked: " + Arrays.toString(trackPlayerInput.toArray()));
    }

    private static int getPlayerInput(Random random, Set<Integer> trackPlayerInput) {
        int num = random.nextInt(9) + 1;
        System.out.println(num);
        if (trackPlayerInput.contains(num)) {
            getPlayerInput(random, trackPlayerInput);
        }
        return num;
    }

    private static boolean enterMark(String player, int playerInput, String[][] gameLines, Set<Integer> trackPlayerInput) {

        Set<Integer> trackInputRange = IntStream.range(1, 10).boxed().collect(Collectors.toSet()); //Used to check if trackPlayerInput is full


        String mark = " X ";
        if (player.equals("Player1")) {
            mark = " O ";
        }


        if (trackPlayerInput.contains(playerInput) && playerInput != 0) {

            if (player.equals("CPU")) {
                int cpuInput = getPlayerInput(random, trackPlayerInput);
                enterMark(player, cpuInput, gameLines, trackPlayerInput);
                System.out.println("AI Tries: " + cpuInput);
            } else {
                System.out.println("Marked already, choose a different position.");
                enterMark(player, scanner.nextInt(), gameLines, trackPlayerInput);
            }

        } else {


            switch (playerInput) {
                case 1:
                    gameLines[0][0] = mark;
                    trackPlayerInput.add(1);
                    break;
                case 2:
                    gameLines[0][2] = mark;
                    trackPlayerInput.add(2);
                    break;
                case 3:
                    gameLines[0][4] = mark;
                    trackPlayerInput.add(3);
                    break;
                case 4:
                    gameLines[2][0] = mark;
                    trackPlayerInput.add(4);
                    break;
                case 5:
                    gameLines[2][2] = mark;
                    trackPlayerInput.add(5);
                    break;
                case 6:
                    gameLines[2][4] = mark;
                    trackPlayerInput.add(6);
                    break;
                case 7:
                    gameLines[4][0] = mark;
                    trackPlayerInput.add(7);
                    break;
                case 8:
                    gameLines[4][2] = mark;
                    trackPlayerInput.add(8);
                    break;
                case 9:
                    gameLines[4][4] = mark;
                    trackPlayerInput.add(9);
                    break;
                default:
                    break;
            }
        }

        return !trackPlayerInput.containsAll(trackInputRange); //Returns false when it contains all

    }


    static void gameLinePrinter(String[][] gameLines) {

        for (String[] gameRow : gameLines) {
            for (String gameCol : gameRow) {
                System.out.print(gameCol);
            }
            System.out.println();
        }
    }
}


