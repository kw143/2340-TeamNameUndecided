package edu.gatech.cs2340.team.imperialtrader.model;

/**
 * Interface for our concrete interactors
 */
abstract class Interactor {

    private final Universe gameData;

    /**
     * Interactor constructor given a Universe
     * @param dat
     */
    protected Interactor(Universe dat) {
        gameData = dat;
    }

    /**
     * Getter for Universe's data
     * @return Universe's data
     */
    protected Universe getUniverse() {
        return gameData;
    }
}
