package com.company;

import java.util.HashSet;
import java.util.Set;

public class HintsChecker {
    private boolean wasHintUsed = false;
    private final Set<Hint> usedHints = new HashSet<>();

    public void clearUsedHints() {
        usedHints.clear();
    }

    public void clearWasUsed() {
        wasHintUsed = false;
    }

    public boolean canUseHint(Hint hint) {
        if(!wasHintUsed && !usedHints.contains(hint)) {
            wasHintUsed = true;
            usedHints.add(hint);

            return true;
        }

        return false;
    }
}
