package org.axlceb.goose.game;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axlceb.goose.Game;
import org.axlceb.goose.exception.PlayerNotFoundException;
import org.axlceb.goose.shared.Commands;
import org.axlceb.goose.shared.Dice;
import org.axlceb.goose.shared.Player;
import org.axlceb.goose.shared.Space;

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

    @Setter(AccessLevel.NONE)
    private boolean onGoing = false;

    public void run() {
        System.out.println(this.name + " - Start!");

        try (Scanner scanner = new Scanner(System.in)) {
            this.onGoing = true;
            do {
                System.out.println("?: ");
                String text = scanner.nextLine();                                                                        // read user input
                var argument = Arrays.stream(text.split(" ", 2)).skip(1).findFirst().orElse("");        // get command argument. Null check avoid

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
                        break;
                    default:
                        System.out.println("Sorry. It is not a recognized command. Please try it again");
                }
            } while (this.onGoing);

        } catch (RuntimeException e) {
            log.error("Scanner initialization fails", e);
        }
        System.out.println(this.name + " - Ends!");
    }

    public void addPlayer(String name) {
        if (getPlayerByName(name) == null) {
            players.add(Player.create(name));
            System.out.println("The player " + name + " is entering the game!");
        } else {
            System.out.println("Sorry the player with the name " + name + " is already in the game. Please try it again");
        }
    }

    public void movePlayer(String name) {

        var player = getPlayerByName(name);
        try {
            if (getPlayerByName(name) != null) {
                players.stream()
                        .filter(p -> p.getName().equals(name))
                        .findAny()
                        .ifPresent(
                                (p) -> p.move(dices.stream()
                                        .mapToInt(d -> d.roll())
                                        .sum())
                        );

                handleSteps(player);
            } else {
                System.out.println("Sorry the player with the name " + name + " is not in the game. Please try it again");
                throw new PlayerNotFoundException(name);
            }
        } catch (RuntimeException e) {
            log.warn("Demo purpose, no useful in the logic. This is just an example of handle custom Exceptions", e);
        }
    }

    public void performExit() {
        this.onGoing = false;
        System.out.println("Cleaning the data and prepare to exit " + this.name);
    }

    private Player getPlayerByName(String name) {
        return players.stream()
                .filter(p -> name.equals(p.getName()))
                .findAny()
                .orElse(null);
    }

    private void handleSteps(Player player) {

        Space space = Space.fromString(board.getSteps().get(player.getStep()));         // Get the Space of the player

        switch (space) {
            case START:
                System.out.println("Player " + player.getName() + " is on " + Space.START.name());
                break;
            case BRIDGE:
                System.out.println("Player " + player.getName() + " step on " + Space.BRIDGE.name());
                player.setStep(12);
                System.out.println("Player " + player.getName() + " move to step " + player.getStep());
                break;
            case GOOSE:
                System.out.println("Player " + player.getName() + " step on " + Space.GOOSE.name());
                System.out.println("Player " + player.getName() + " move to " + player.getStep());
                break;
            case FINISH:
                System.out.println("Player " + player.getName() + " reach the step " + player.getStep());
                System.out.println("Player " + player.getName() + " WINS!");
                performExit();
                break;
            default:
        }
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

