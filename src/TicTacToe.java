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
        int playerInput = 0;
        while (check) {

            if (i % 2 == 0) {
                System.out.print("Enter: ");

                String playerInputString = scanner.next().replaceAll("[^1-9]+", "").trim();
                playerInput = Integer.parseInt(playerInputString);
                check = enterMark("Player1", playerInput, gameLines, trackPlayerInput);

            } else {

                if (player2.equals("CPU")) {
                    System.out.println("P I : " + playerInput);
//                    int cpuInput = getCPUInput(random, trackPlayerInput);
                    int cpuInput = getIntelligentCPUInput(random, trackPlayerInput, playerInput);
                    if (cpuInput != 0) {
                        System.out.println("AI Tries: " + cpuInput);
                    }
                    check = enterMark(player2, cpuInput, gameLines, trackPlayerInput);
                } else {
                    System.out.println("Enter: ");
                    int playerInput2 = scanner.nextInt();
                    check = enterMark(player2, playerInput2, gameLines, trackPlayerInput);
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


    private static int getIntelligentCPUInput(Random random, Set<Integer> trackPlayerInput, int prevPlayerInput) {
//        int num = random.nextInt(9) + 1;

        int cpuInput = getCPUInput(prevPlayerInput);

        System.out.println(cpuInput);
        if (trackPlayerInput.contains(cpuInput) && cpuInput == 0) {
            getIntelligentCPUInput(random, trackPlayerInput, cpuInput);
        }
        return cpuInput;
    }

    private static int getCPUInput(int prevPlayerInput) {

        int[] ar1 = {2, 4, 5};
        int[] ar2 = {1, 3, 5, 4, 6};
        int[] ar3 = {2, 5, 6};
        int[] ar4 = {1, 2, 5, 7, 8};
        int[] ar5 = {1, 2, 3, 4, 6, 7, 8, 9};
        int[] ar6 = {2, 3, 5, 8, 9};
        int[] ar7 = {4, 5, 8};
        int[] ar8 = {7, 4, 5, 6, 9};
        int[] ar9 = {5, 8, 6};

        switch (prevPlayerInput) {
            case 1:
                return getRandom(ar1);

            case 2:
                return getRandom(ar2);
            case 3:
                return getRandom(ar3);
            case 4:
                return getRandom(ar4);
            case 5:
                return getRandom(ar5);
            case 6:
                return getRandom(ar6);
            case 7:
                return getRandom(ar7);
            case 8:
                return getRandom(ar8);
            case 9:
                return getRandom(ar9);
            default:
                return 0;
        }


    }

    private static int getRandom(int[] array) {
        System.out.println("Rand: " + Arrays.toString(array));
        int randIndex = random.nextInt(array.length);
        return array[randIndex];
    }

//    private static int getCPUInput(Random random, Set<Integer> trackPlayerInput) {
//        int num = random.nextInt(9) + 1;
//        System.out.println(num);
//        if (trackPlayerInput.contains(num)) {
//            getCPUInput(random, trackPlayerInput);
//        }
//        return num;
//    }

    private static boolean enterMark(String player, int playerInput, String[][] gameLines, Set<Integer> trackPlayerInput) {

        Set<Integer> trackInputRange = IntStream.range(1, 10).boxed().collect(Collectors.toSet()); //Used to check if trackPlayerInput is full


        String mark = " X ";
        if (player.equals("Player1")) {
            mark = " O ";
        }


        if (trackPlayerInput.contains(playerInput) && playerInput != 0) {

            if (player.equals("CPU")) {
                int cpuInput = getIntelligentCPUInput(random, trackPlayerInput, playerInput);
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


