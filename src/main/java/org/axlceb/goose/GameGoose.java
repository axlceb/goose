package org.axlceb.goose;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

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
                String text = scanner.nextLine();

                switch (Commands.fromString(text)) {
                    case ADD:
                        addPlayer(text.split(" ", 2)[1]);
                        break;
                    case MOVE:
                        System.out.println(text.split(" ", 2)[1]);
                        break;
                    case EXIT:
                        System.out.println("exit");
                        continueGame = false;
                        break;
                    default:
                        System.out.println("Illegal command");
                }
            } while (continueGame);

        } catch (RuntimeException e) {
            log.error("Scanner initialization fails", e);
        }
        System.out.println(name + " - Ends!");
    }

    public void addPlayer(String name) {
        players.add(Player.create(name));
    }

    public void movePlayer(String name) {}

}
