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
        this.printToConsole(this.name, " - Start!");

        try (Scanner scanner = new Scanner(System.in)) {
            this.onGoing = true;
            do {
                this.printToConsole("?: ");
                String text = scanner.nextLine();                                                                        // read user input
                var argument = Arrays.stream(text.split(" ", 2)).skip(1).findFirst().orElse("");        // get command argument. Null check param avoid

                switch (Commands.fromString(text)) {
                    case INFO:
                        this.printToConsole(this.toString());
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
                        this.printToConsole("Sorry. It is not a recognized command. Please try it again");
                }
            } while (this.onGoing);

        } catch (RuntimeException e) {
            log.error("Scanner initialization fails", e);
        }
        this.printToConsole(this.name, " - Ends!");
    }

    public void addPlayer(String name) {
        if (this.getPlayerByName(name) == null) {
            players.add(Player.create(name));
            this.printToConsole("The player ", name, " is entering the game!");
            this.printToConsole(name, " is on ", Space.START.name());
        } else {
            this.printToConsole("Sorry the player with the name ", name, " is already in the game. Please try it again");
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
                                        .mapToInt(Dice::roll)
                                        .sum())
                        );

                handleSteps(player);
            } else {
                // NOTE: This is just an example of using custom Exception. Not required in the game.
                this.printToConsole("Sorry the player with the name ", name, " is not in the game. Please try it again");
                throw new PlayerNotFoundException(name);
            }
        } catch (RuntimeException e) {
            log.warn("Demo purpose, no useful in the logic. This is just an example of handle custom Exceptions", e);
        }
    }

    public void performExit() {
        this.onGoing = false;
        this.printToConsole("Cleaning the data and prepare to exit ", this.name);
    }

    private void handleSteps(Player player) {
        Space space = Space.fromString(board.getSteps().get(player.getStep()));                                 // Get the Space of the player

        switch (space) {
            case START:
                this.printToConsole(player.getName(), " is on ", Space.START.name());
                break;
            case BRIDGE:
                this.printToConsole(false, player.getName(), " step on ", Space.BRIDGE.name());
                player.setStep(12);
                this.printToConsole(player.getName(), " move to step ", player.getStep().toString());
                break;
            case GOOSE:
                this.printToConsole(false, player.getName(), " step on ", Space.GOOSE.name(), " ");
                this.printToConsole(false, player.getName(), " move from ", player.getStep().toString(), " ");
                player.setStep(player.getStep() + dices.stream()                                                // Advance the same amount of steps
                        .mapToInt(Dice::getResult)
                        .sum());
                this.printToConsole(" to ", player.getStep().toString());
                this.handleSteps(player);                                                                       // Handle next Step
                break;
            case FINISH:
                this.printToConsole(false, player.getName(), " reach the step ", player.getStep().toString());
                this.printToConsole(player.getName(), " WINS!");
                performExit();
                break;
            default:
        }
    }

    private Player getPlayerByName(String name) {
        return players.stream()
                .filter(p -> name.equals(p.getName()))
                .findAny()
                .orElse(null);
    }

    private void printToConsole(String... text) {
        System.out.println(String.join("", text));
    }

    /**
     * This is just an example of Overloading a method printToConsole
     * @param newLine parameter that define if new line is created
     * @param text Array of String to print to console
     */
    private void printToConsole(boolean newLine, String... text) {
        if (newLine)
            System.out.println(String.join("", text));
        else
            System.out.print(String.join("", text));
    }

    @Override
    public String toString() {
        // NOTE: This is just an example of using stringBuffer to work faster with String.
        // But in this case it is more code clean and surely faster a simple concatenation of Strings

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("GameGoose {\n")
                .append("    name = '").append(name).append("\' \n")
                .append("    step-count = ").append(board.getStepCount()).append(" \n")
                .append("    dices = ").append(dices.size()).append(" \n")
                .append("    players = ").append(players).append(" \n")
                .append("}");
        return stringBuilder.toString();
    }
}

