package com.private_void.core;

import java.util.ArrayList;
import java.util.Arrays;

//TODO: возможно, заменить двумерный массив на какой-то контейнер
//TODO: протестировать ceil, возможно он не будет давать то, что нужно, так как округляет не в меньшую сторону, а окружгляет к ближайшему инту
public class Detector {

    private Point3D centerCoordinate;
    private Point3D beginCoordinate;
    private float width;
    private float cellSize;
    private float angleDegrees;
    private float angleRadians;
    private int cellsAmount;
    private int detectedParticlesAmount;
    private int notDetectedParticlesAmount;
    private int outOfCapillarParticlesAmount;
    private float capillarRadiusBig;
    private float capillarRadiusSmall;

    private float x;
    private float y;
    private float z;

    private float Vx;
    private float Vy;
    private float Vz;

    private float[][] cells;

    public Detector(final Point3D centerCoordinate, float width, float cellSize, float angleDegrees, float capillarRadiusBig, float capillarRadiusSmall) {

        this.centerCoordinate = centerCoordinate;
        this.width = width;
        this.cellSize = cellSize;
        this.angleDegrees = angleDegrees;
        this.angleRadians = Utils.convertDegreesToRads(angleDegrees);
        this.capillarRadiusBig = capillarRadiusBig;
        this.capillarRadiusSmall = capillarRadiusSmall;
        this.detectedParticlesAmount = 0;

        this.beginCoordinate.setX(centerCoordinate.getX());
        this.beginCoordinate.setY(centerCoordinate.getY() - width);
        this.beginCoordinate.setZ(centerCoordinate.getZ() - width);
        this.cellsAmount = (int) (2.0f * width / cellSize);
        this.cells = new float[cellsAmount][cellsAmount];
        Arrays.fill(cells, 0.0f);

    }

    public Detector(final Point3D centerCoordinate, float width, float cellSize, float angleDegrees, float capillarRadiusSmall) {

        this.centerCoordinate = centerCoordinate;
        this.width = width;
        this.cellSize = cellSize;
        this.angleDegrees = angleDegrees;
        this.angleRadians = Utils.convertDegreesToRads(angleDegrees);
        this.capillarRadiusBig = 0.0f;
        this.capillarRadiusSmall = capillarRadiusSmall;
        this.detectedParticlesAmount = 0;

        this.beginCoordinate.setX(centerCoordinate.getX());
        this.beginCoordinate.setY(centerCoordinate.getY() - width);
        this.beginCoordinate.setZ(centerCoordinate.getZ() - width);
        this.cellsAmount = (int) (2.0f * width / cellSize);
        this.cells = new float[cellsAmount][cellsAmount];
        Arrays.fill(cells, 0.0f);

    }

    public void detect(Flux flux) {

        float sinR = (float) Math.sin(angleRadians);
        float tanR = (float) Math.tan(angleRadians);
        float rR = capillarRadiusBig + capillarRadiusSmall;

        try {

            for (Particle particle : flux.getParticles()) {

                x = particle.getCoordinate().getX();
                y = particle.getCoordinate().getY();
                z = particle.getCoordinate().getZ();

                Vx = particle.getSpeed().getX();
                Vy = particle.getSpeed().getY();
                Vz = particle.getSpeed().getZ();

                particle.setCoordinate((float) (tanR * (x * Vz - z * Vx - rR * sinR * Vz) / (tanR * Vz - Vx) + rR * sinR),
                                       (float) ((Vy / Vz) * ((x * Vz - z * Vx - rR * sinR * Vz) / (tanR * Vz - Vx) - z) + y),
                                       (float) ((x * Vz - z * Vx - rR * sinR * Vz) / (tanR * Vz - Vx)));

                cells[(int)(Math.ceil((particle.getCoordinate().getZ() - beginCoordinate.getZ()) / cellSize))][(int)(Math.ceil((particle.getCoordinate().getY() - beginCoordinate.getY()) / cellSize))] += particle.getIntensity();
                detectedParticlesAmount++;

            }

        }
        catch (Exception ex){

            System.out.println(ex.getMessage());

        }

    }

    public int getDetectedParticlesAmount() {
        return detectedParticlesAmount;
    }

    public void setDetectedParticlesAmount(int detectedParticlesAmount) {
        this.detectedParticlesAmount = detectedParticlesAmount;
    }

    public int getNotDetectedParticlesAmount() {
        return notDetectedParticlesAmount;
    }

    public void setNotDetectedParticlesAmount(int notDetectedParticlesAmount) {
        this.notDetectedParticlesAmount = notDetectedParticlesAmount;
    }

    public int getOutOfCapillarParticlesAmount() {
        return outOfCapillarParticlesAmount;
    }

    public void setOutOfCapillarParticlesAmount(int outOfCapillarParticlesAmount) {
        this.outOfCapillarParticlesAmount = outOfCapillarParticlesAmount;
    }

}
