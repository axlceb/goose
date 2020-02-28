package org.axlceb.goose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameGoose implements Game {

    private String name;
    private Board board;
    private List<Dice> dices;
    private List<Player> players;

    public void run() {
        System.out.println(name + " - Start!");

        try (Scanner scanner = new Scanner(System.in)) {
            var continueGame = true;
            do {
                System.out.print("?: ");
                String text = scanner.nextLine();                                                                        // read user input
                var argument = Arrays.stream(text.split(" ", 2)).skip(1).findFirst().orElse("");        // get command argument

                switch (Commands.fromString(text)) {
                    case ADD:
                        addPlayer(argument);
                        break;
                    case MOVE:
                        movePlayer(argument);
                        break;
                    case EXIT:
                        performExit();
                        continueGame = false;
                        break;
                    default:
                        System.out.println("Illegal command. Please try it again");
                }
            } while (continueGame);

        } catch (RuntimeException e) {
            log.error("Scanner initialization fails", e);
        }
        System.out.println(name + " - Ends!");
    }

    public void addPlayer(String name) {
        var newPlayer = Player.create(name);

        if (players.contains(newPlayer)) {
            System.out.println("Sorry the player with the name " + name + " is already in the game. Please try it again");
        } else {
            players.add(newPlayer);
            System.out.println("The player " + name + " is entering the game!");
        }
    }

    public void movePlayer(String name) {}

    private void performExit() {
        System.out.println("Cleaning the data and prepare to Exit");
    }
}
