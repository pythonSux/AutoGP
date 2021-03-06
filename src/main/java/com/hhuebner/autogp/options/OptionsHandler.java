package com.hhuebner.autogp.options;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OptionsHandler {

    public static final OptionsHandler INSTANCE = new OptionsHandler();
    public final boolean DEBUG = false;
    public final boolean DEBUG_ROOM_GEN = true;

    private final List<Option<?>> options = new ArrayList<>();

    //CANVAS UI
    public Option<Boolean> showDoorHitBoxes = register(new BoolOption("UI", "showDoorHitBoxes", false));
    public Option<Boolean> showGrid = register(new BoolOption("UI", "showGrid", true));
    public Option<Boolean> showNumbers = register(new BoolOption("UI", "showNumbers", true));
    public Option<Boolean> colorRooms = register(new BoolOption("UI", "colorRooms", true));
    public Option<Double> graphSizeLimitFactor = register(new DoubleOption("", "graphSizeLimitFactor", 1.4));
    public Option<Integer> furnitureLineWidth = register(new IntOption("", "furnitureLineWidth", 2));

    //WALLS, DOORS, WINDOWS
    public Option<Double> innerWallWidth = register(new DoubleOption("", "innerWallWidth", 0.1));
    public Option<Double> outerWallWidth = register(new DoubleOption("", "outerWallWidth", 0.2));
    public Option<Double> doorSize = register(new DoubleOption("", "doorSize", 0.75));
    public Option<Double> doorPrefWallDistance = register(new DoubleOption("", "doorPrefWallDistance", 0.25));
    public Option<Double> doorClearanceFactor = register(new DoubleOption("", "doorClearanceFactor", 1.5));

    //GENERATION ALGORITHM
    public Option<Integer> generationTryLimit = register(new IntOption("", "generationTryLimit", 5000));
    public Option<Boolean> generateFurniture = register(new BoolOption("Grundriss Generierung", "generateFurniture", true));
    public Option<Boolean> generateDoors = register(new BoolOption("Grundriss Generierung", "generateDoors", true));
    public Option<Boolean> generateWindows = register(new BoolOption("Grundriss Generierung", "generateWindows", true));
    public Option<Double> minimumRoomWidth = register(new DoubleOption("", "minimumRoomWidth", 1.4));
    public Option<Double> roomSizeRoundingThreshold = register(new DoubleOption("", "roomSizeRoundingThreshold", 0.75));
    public Option<Integer> furnitureSpawnTries = register(new IntOption("", "furnitureSpawnTries", 10));
    public Option<Double> windowHeight = register(new DoubleOption("", "windowHeight", 1.0));
    public Option<Double> bathroomWindowWidth =  register(new DoubleOption("", "bathroomWindowWidth", 0.3));
    //ROOM PROPORTIONS

    //DEFAULT PARAMETERS
    public Option<Double> defaultGPSize = register(new DoubleOption("", "defaultGPSize", 100.0));

    private OptionsHandler() {
        loadConfig();
    }

    private void loadConfig() {
        Properties prop = new Properties();

        for(Option<?> o : this.options) {
            String value = prop.getProperty(o.getKey());
            o.parse(value);
        }

        try (InputStream inputStream = new FileInputStream("config.properties")) {
            prop.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> Option<T> register(Option<T> o) {
        this.options.add(o);
        return o;
    }

    public List<Option<?>> getOptions() {
        return this.options;
    }
}
