package org.axlceb.goose;

import lombok.*;
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
        System.out.println(this.name + " - Start!");

        try (Scanner scanner = new Scanner(System.in)) {
            var continueGame = true;
            do {
                System.out.println("?: ");
                String text = scanner.nextLine();                                                                        // read user input
                var argument = Arrays.stream(text.split(" ", 2)).skip(1).findFirst().orElse("");        // get command argument

                switch (Commands.fromString(text)) {
                    case INFO:
                        System.out.println(this.toString());
                        break;
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
                        System.out.println("Sorry. It is not a recognized command. Please try it again");
                }
            } while (continueGame);

        } catch (RuntimeException e) {
            log.error("Scanner initialization fails", e);
        }
        System.out.println(this.name + " - Ends!");
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

    public void movePlayer(String name) {

        var newPlayer = Player.create(name);

        if (players.contains(newPlayer)) {
            players.stream()
                    .filter(p -> p.getName().equals(name))
                    .findAny()
                    .ifPresent(
                            (p) -> p.move(dices.stream()
                                    .mapToInt(d -> d.roll())
                                    .sum())
                    );

            // System.out.println(this.board.getSteps().get());

            // TODO: Get player fom name and check the space

        } else {
            System.out.println("Sorry the player with the name " + name + " is not in the game. Please try it again");
        }


    }

    public void performExit() {
        System.out.println("Cleaning the data and prepare to exit " + this.name);
    }

    @Override
    public String toString() {
        // NOTE: This is just an example of using stringBuffer to work faster with String.
        // But in this case it is more code clean and surely faster a simple concatenation of Strings

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("GameGoose {\n")
                .append("    name = '" + name + "\' \n")
                .append("    step-count = " + board.getStepCount() + " \n")
                .append("    dices = " + dices.size() + " \n")
                .append("    players = " + players + " \n")
                .append("}");
        return stringBuilder.toString();
    }
}
