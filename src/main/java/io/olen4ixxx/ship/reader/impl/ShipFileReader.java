package io.olen4ixxx.ship.reader.impl;

import io.olen4ixxx.ship.exception.ShipException;
import io.olen4ixxx.ship.reader.ShipReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShipFileReader implements ShipReader {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String readLines(String stringPath) throws ShipException {
        logger.info("CompositeFileReader: readLines({})", stringPath);
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(stringPath);
        if (resource == null) {
            logger.error("File is not found ({})", stringPath);
            throw new ShipException("File is not found");
        }
        URI uri;
        try {
            uri = resource.toURI();
        } catch (URISyntaxException e) {
            logger.error("Wrong file path ({})", stringPath);
            throw new ShipException("Wrong file path", e);
        }
        Path path = Path.of(uri);
        String textFromFile;
        try (Stream<String> lines = Files.lines(path)) {
            textFromFile = lines.collect(Collectors.joining());
        } catch (IOException e) {
            logger.error("Check the file ({})", stringPath);
            throw new ShipException("Check the file", e);
        }
        logger.info("File is read");
        return textFromFile;
    }
}
