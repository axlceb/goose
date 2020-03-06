package org.axlceb.goose.engine;

import org.axlceb.goose.GooseGame2;
import org.axlceb.goose.board.Board2;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GooseGameGoose2Test {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private GameListener gameListener;

    private GooseGame2 gooseGame2;
    private Board2 board2;

    @Before
    public void setUp() {
        board2 = new Board2();

        gameListener = mock(GameListener.class);

        gooseGame2 = new GooseGame2(board2);
        gooseGame2.addGameListener(gameListener);
    }

    @Test
    public void testAddPlayer() throws Exception {
        String playerName = "Pippo";

        gooseGame2.addPlayer(playerName);

        assertThat(gooseGame2.getPlayers(), hasEntry(playerName, 0));
        verifyZeroInteractions(gameListener);
    }

    @Test
    public void testAddExistingPlayerThrowsException() throws Exception {
        String playerName = "Pippo";

        gooseGame2.addPlayer(playerName);

        expectedException.expect(PlayerAlreadyExistsException.class);
        gooseGame2.addPlayer(playerName);
    }

    @Test
    public void testMovePlayerUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame2.addPlayer(playerName);

        gooseGame2.movePlayer(playerName, Arrays.asList(2, 2));

        verify(gameListener).onPlayerMoved(playerName, board2.getSpace(0), board2.getSpace(4));
        assertThat(gooseGame2.getPlayers().get(playerName), equalTo(4));
    }

    @Test
    public void testMovePlayerOnBridgeSpaceUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame2.addPlayer(playerName);

        gooseGame2.movePlayer(playerName, Arrays.asList(4, 2));

        verify(gameListener).onPlayerMoved(playerName, board2.getSpace(0), board2.getSpace(6));
        verify(gameListener).onPlayerJump(playerName, board2.getSpace(12));
        assertThat(gooseGame2.getPlayers().get(playerName), equalTo(12));
    }

    @Test
    public void testMovePlayerWinUpdatePlayerSpaceAndNotifyWin() throws Exception {
        String playerName = "Pippo";
        gooseGame2.addPlayer(playerName);

        gooseGame2.movePlayer(playerName, Arrays.asList(60, 3));

        verify(gameListener).onPlayerMoved(playerName, board2.getSpace(0), board2.getSpace(63));
        verify(gameListener).onPlayerWin(playerName);
        assertThat(gooseGame2.getPlayers().get(playerName), equalTo(63));
    }

    @Test
    public void testMovePlayerOverMaxSpaceBounceAndUpdatePlayerSpaceAndNotifyBounced() throws Exception {
        String playerName = "Pippo";
        gooseGame2.addPlayer(playerName);

        gooseGame2.movePlayer(playerName, Arrays.asList(60, 5));

        verify(gameListener).onPlayerMoved(playerName, board2.getSpace(0), board2.getSpace(63));
        verify(gameListener).onPlayerBounced(playerName, board2.getSpace(61));
        verifyNoMoreInteractions(gameListener);
        assertThat(gooseGame2.getPlayers().get(playerName), equalTo(61));
    }

    @Test
    public void testMovePlayerOnGooseSpaceUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame2.addPlayer(playerName);

        gooseGame2.movePlayer(playerName, Arrays.asList(4, 1));

        verify(gameListener).onPlayerMoved(playerName, board2.getSpace(0), board2.getSpace(5));
        assertThat(gooseGame2.getPlayers().get(playerName), equalTo(10));
    }

    @Test
    public void testMovePlayerOnGooseSpaceMoveTwiceUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame2.addPlayer(playerName);

        gooseGame2.movePlayer(playerName, Arrays.asList(4, 6));
        gooseGame2.movePlayer(playerName, Arrays.asList(2, 2));

        verify(gameListener).onPlayerMoved(playerName, board2.getSpace(0), board2.getSpace(10));
        verify(gameListener).onPlayerJump(playerName, board2.getSpace(22));
        assertThat(gooseGame2.getPlayers().get(playerName), equalTo(22));
    }

    @Test
    public void testMovePlayerOnAlreadyOccupiedSpaceUpdateTwoPlayer() throws Exception {
        String playerName1 = "Pippo";
        gooseGame2.addPlayer(playerName1);
        gooseGame2.movePlayer(playerName1, Collections.singletonList(15));

        String playerName2 = "Pluto";
        gooseGame2.addPlayer(playerName2);
        gooseGame2.movePlayer(playerName2, Collections.singletonList(17));

        gooseGame2.movePlayer(playerName1, Arrays.asList(1, 1));

        verify(gameListener).onPlayerMoved(playerName1, board2.getSpace(15), board2.getSpace(17));
        verify(gameListener).onPlayerPrank(playerName2, board2.getSpace(17), board2.getSpace(15));
        assertThat(gooseGame2.getPlayers().get(playerName1), equalTo(17));
        assertThat(gooseGame2.getPlayers().get(playerName2), equalTo(15));
    }
}
