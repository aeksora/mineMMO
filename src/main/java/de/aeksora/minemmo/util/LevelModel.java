package de.aeksora.minemmo.util;

public class LevelModel {
    public int getLevel(int xp) {
        return (int) (Math.sqrt((xp - 1000.0) / 2.0));
    }
}
