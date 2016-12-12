package com.example.nicolas.shootemup;

import android.graphics.Point;

/**
 * Created by Nicolas on 11/12/2016.
 */

public class Util {
    public static double dist(Point point1, Point point2) {
        return Math.sqrt(dist2(point1, point2));
    }

    public static double dist2(Point point1, Point point2) {
        return (point1.x-point2.x)*(point1.x-point2.x)+(point1.y-point2.y)*(point1.y-point2.y);
    }
}
