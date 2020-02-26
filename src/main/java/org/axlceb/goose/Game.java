package org.axlceb.goose;

interface Game extends Runnable {
    void addPlayer(String name);
    void movePlayer(String name);
}
