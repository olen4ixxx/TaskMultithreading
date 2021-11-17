package io.olen4ixxx.ship.reader;

import io.olen4ixxx.ship.exception.ShipException;

public interface CustomReader {
    String readLines(String stringPath) throws ShipException;
}
