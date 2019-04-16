package oop.lab1;

import java.util.ArrayList;
import java.util.Collection;

public class Game {

    private Collection<Session> sessions = new ArrayList<Session>();

    private Session currentSession;

    public Session startSession(int x, int y, Player player1, Player player2) {
        assert (currentSession == null);

        currentSession = new Session(x, y, player1, player2);
        currentSession.start();
        return currentSession;

    }

    public void finishSession() {
        currentSession.finish();
        sessions.add(currentSession);
        currentSession = null;
    }
}
