package org.axlceb.goose;

import org.axlceb.goose.exception.PlayerNotFoundException;
import org.axlceb.goose.shared.Dice;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandExecutor {

    private final GooseGame2 gooseGame2;
    private final Dice dice;

    private final PrintStream printStream;

    public CommandExecutor(GooseGame2 gooseGame2, Dice dice, PrintStream printStream) {
        this.gooseGame2 = gooseGame2;
        this.dice = dice;
        this.printStream = printStream;
    }

    public void executeGameCommand(String userString) throws Exception {
    }

    private void handleMovePlayer(List<String> userArguments) throws PlayerNotFoundException {
        String playerName = userArguments.get(0);

        List<Integer> rolls = Arrays.asList(dice.roll(), dice.roll());

        String rollsString = rolls.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        printStream.print(playerName + " rolls " + rollsString + ". ");

        gooseGame2.movePlayer(playerName, rolls);
    }

    private void handleAddPlayer(List<String> userArguments) {
    }
}
