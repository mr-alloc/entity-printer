package io.taech;

import io.taech.print.EntityPrinter;
import io.taech.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(1, 2));
        coordinates.add(new Coordinate(2, 1));
        coordinates.add(new Coordinate(4, 2));
        coordinates.add(new Coordinate(4, 3));

        System.out.println(EntityPrinter.draw(coordinates, Coordinate.class));

    }

}
