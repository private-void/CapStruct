package com.private_void.core.particles;

import com.private_void.core.geometry.Point3D;
import com.private_void.core.geometry.Vector3D;

import static com.private_void.utils.Constants.RECURSIVE_ITERATIONS_MAX;

public class Particle {
    private Point3D coordinate;
    private Vector3D speed;
    private float intensity;
    private float trace;
    private boolean absorbed;
    private int recursiveIterationCount;

    public Particle(final Point3D coordinate, final Vector3D speed) {
        this.coordinate = coordinate;
        this.speed = speed;
        this.intensity = 1.0f;
        this.trace = 0.0f;
        this.absorbed = false;
        this.recursiveIterationCount = 1;
    }

    public Particle(final Point3D point3D, final Vector3D vector3D, float intensity) {
        this.coordinate = point3D;
        this.speed = vector3D;
        this.intensity = intensity;
        this.trace = 0.0f;
        this.absorbed = false;
        this.recursiveIterationCount = 1;
    }

    public void decreaseIntensity(float reflectivity) {
        this.intensity *= reflectivity;
    }

    public void increaseTrace(final Point3D point3D) {
        trace = (float) Math.sqrt((point3D.getX() - coordinate.getX()) * (point3D.getX() - coordinate.getX())
                + (point3D.getY() - coordinate.getY()) * (point3D.getY() - coordinate.getY())
                + (point3D.getZ() - coordinate.getZ()) * (point3D.getZ() - coordinate.getZ()));
    }

    public boolean isRecursiveIterationsLimitReached() {
        return recursiveIterationCount == RECURSIVE_ITERATIONS_MAX;
    }

    public void recursiveIteration() {
        this.recursiveIterationCount++;
    }

    public int getRecursiveIterationCount() {
        return recursiveIterationCount;
    }

    public void stopRecursiveIterations() {
        recursiveIterationCount = 1;
    }

    public Point3D getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(final Point3D newCoordinate) {
        increaseTrace(newCoordinate);
        this.coordinate = newCoordinate;
    }

    public void setCoordinate(float x, float y, float z) {
        increaseTrace(new Point3D(x, y, z));
        this.coordinate.setX(x);
        this.coordinate.setY(y);
        this.coordinate.setZ(z);
    }

    public Vector3D getSpeed() {
        return speed;
    }

    public void setSpeed(final Vector3D speed) {
        this.speed = speed;
    }

    public void setSpeed(float x, float y, float z) {
        this.speed.setX(x);
        this.speed.setY(y);
        this.speed.setZ(z);
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getTrace() {
        return trace;
    }

    public void setTrace(float trace) {
        this.trace = trace;
    }

    public boolean isAbsorbed() {
        return absorbed;
    }

    public void setAbsorbed(boolean absorbed) {
        this.absorbed = absorbed;
    }
}