package org.axlceb.goose.gui.cli;

import org.axlceb.goose.CommandExecutor;
import org.axlceb.goose.shared.Dice;
import org.axlceb.goose.GooseGame2;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class CommandExecutorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private GooseGame2 gooseGame2;
    private Dice dice;
    private PrintStream printStream;

    private CommandExecutor commandExecutor;

    @Before
    public void setUp() {
        gooseGame2 = mock(GooseGame2.class);
        dice = mock(Dice.class);
        printStream = mock(PrintStream.class);

        commandExecutor = new CommandExecutor(gooseGame2, dice, printStream);
    }

    @Test
    public void executeNotFoundGameCommandThrowsException() throws Exception {
        expectedException.expect(CommandNotFoundException.class);
        commandExecutor.executeGameCommand("invalid command");
    }

    @Test
    public void executeAddPlayerGameCommandCallsGooseGameAddPlayer() throws Exception {
        when(gooseGame2.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 0));

        commandExecutor.executeGameCommand("add player Pluto");

        verify(gooseGame2).addPlayer("Pluto");
        verify(printStream).print("players: Pluto");
    }

    @Test
    public void executeMovePlayerGameCommandCallsGooseGameMovePlayer() throws Exception {
        when(dice.roll()).thenReturn(4, 2);

        commandExecutor.executeGameCommand("move Pluto");

        verify(gooseGame2).movePlayer("Pluto", Arrays.asList(4, 2));
        verify(printStream).print("Pluto rolls 4, 2. ");
    }

    @Test
    public void executeExitCommandThrowsException() throws Exception {
        expectedException.expect(GameStoppedException.class);
        commandExecutor.executeGameCommand("exit");
    }
}
