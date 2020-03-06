package org.axlceb.goose;

import org.axlceb.goose.exception.PlayerNotFoundException;
import org.axlceb.goose.game.Board;
import org.axlceb.goose.shared.Space;

import java.util.*;

public class GooseGame2 {

    public static final int FINAL_SPACE = 63;
    private final Map<String, Integer> players;
    private final List<String> gameListeners;
    private final Board board;

    public GooseGame2(Board board) {
        this.board = board;
        this.players = new HashMap<>();
        this.gameListeners = new ArrayList<>();
    }


    public void movePlayer(String playerName, List<Integer> rolls) throws PlayerNotFoundException {
        Integer actualSpaceIndex = players.get(playerName);
        if (actualSpaceIndex == null) {
            throw new PlayerNotFoundException(playerName);
        }

        Integer rollsSum = rolls.stream()
                .mapToInt(Integer::intValue)
                .sum();

        Integer newSpaceIndex = actualSpaceIndex + rollsSum;

        newSpaceIndex = evaluateSpaceRule(playerName, rollsSum, newSpaceIndex);

        handleAnotherPlayerInSpace(playerName, actualSpaceIndex, newSpaceIndex);
    }

    private void handleAnotherPlayerInSpace(String actualPlayerName, Integer actualPlayerSpace, Integer spaceIndex) {
        players.entrySet().stream()
                .filter(e -> !e.getKey().equals(actualPlayerName))
                .filter(e -> e.getValue().equals(spaceIndex))
                .forEach(e -> {
                    e.setValue(actualPlayerSpace);
                });
    }

    private Integer evaluateSpaceRule(String playerName, Integer rollsSum, Integer newSpaceIndex) {

        if (newSpaceIndex > FINAL_SPACE) {
            newSpaceIndex = 2 * FINAL_SPACE - newSpaceIndex;
        } else if (newSpaceIndex == FINAL_SPACE) {
        }
        return null;

//        Integer evaluatedLandingSpaceRule = board.getSpace(newSpaceIndex).getSpaceRule().apply(rollsSum);
//        if (!evaluatedLandingSpaceRule.equals(newSpaceIndex)) {
//            notifyAllOnPlayerJump(playerName, board.getSpace(evaluatedLandingSpaceRule));
//            return evaluateSpaceRule(playerName, rollsSum, evaluatedLandingSpaceRule);
//        }
//
//        players.put(playerName, evaluatedLandingSpaceRule);
//
//        return evaluatedLandingSpaceRule;
    }

}
