package org.axlceb.goose;

public interface Game extends Runnable {
    void addPlayer(String name);
    void movePlayer(String name);
    void performExit();
}
