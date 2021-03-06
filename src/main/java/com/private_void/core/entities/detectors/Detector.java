package com.private_void.core.entities.detectors;

import com.private_void.app.notifiers.Logger;
import com.private_void.app.notifiers.MessagePool;
import com.private_void.app.notifiers.ProgressProvider;
import com.private_void.core.entities.fluxes.Flux;
import com.private_void.core.entities.particles.Particle;
import com.private_void.core.math.geometry.space_2D.CartesianPoint2D;
import com.private_void.core.math.geometry.space_3D.coordinates.CartesianPoint;

import java.util.ArrayList;
import java.util.List;

public class Detector {
    private static final double CELL_WIDTH = 10;
    private static final int CELLS_AMOUNT = 150;

    private final CartesianPoint leftBottomPoint;
    private final double width;

    private final ArrayList<CartesianPoint2D> channeledImage = new ArrayList<>();
    private final ArrayList<CartesianPoint2D> piercedImage = new ArrayList<>();

    private ArrayList<ArrayList<Cell>> channeledCells;
    private ArrayList<ArrayList<Cell>> piercedCells;

    private ArrayList<Cell> channeledCellsZ;
    private ArrayList<Cell> piercedCellsZ;

    private ArrayList<Cell> channeledCellsY;
    private ArrayList<Cell> piercedCellsY;

    private final double cellWidth;
    private final int cellsAmount;

    public Detector(final CartesianPoint center, double width) {
        this.leftBottomPoint = center.shift(0.0, -width / 2.0, -width / 2.0);
        this.width = width;

        this.cellsAmount = CELLS_AMOUNT;
        this.cellWidth = width / cellsAmount;

        createCells();
    }

    private void createCells() {
        this.channeledCells = new ArrayList<>(cellsAmount);
        this.piercedCells = new ArrayList<>(cellsAmount);

        this.channeledCellsZ = new ArrayList<>(cellsAmount);
        this.piercedCellsZ = new ArrayList<>(cellsAmount);

        this.channeledCellsY = new ArrayList<>(cellsAmount);
        this.piercedCellsY = new ArrayList<>(cellsAmount);

        for (int i = 0; i < cellsAmount; i++) {
            CartesianPoint z = leftBottomPoint.shift(0.0, 0.0, i * cellWidth);
            CartesianPoint y = leftBottomPoint.shift(0.0, i * cellWidth, 0.0);

            channeledCellsZ.add(new Cell(z));
            piercedCellsZ.add(new Cell(z));

            channeledCellsY.add(new Cell(y));
            piercedCellsY.add(new Cell(y));

            ArrayList<Cell> yStripe = new ArrayList<>(cellsAmount);

            for (int j = 0; j < cellsAmount; j++) {
                CartesianPoint coordinate = z.shift(0.0, j * cellWidth, 0.0);
                yStripe.add(new Cell(coordinate));
            }

            channeledCells.add(yStripe);
            piercedCells.add(yStripe);
        }
    }

    public Distribution detect(Flux flux) {
        ProgressProvider.getInstance().setProgress(-1.0);
        Logger.info(MessagePool.detectingParticlesStart());

        CartesianPoint point;

        int channeledAmount = 0;
        int piercedAmount = 0;
        int outOfDetectorAmount = 0;
        int absorbedAmount = 0;
        int deletedAmount = 0;

        double expansionAngleSum = 0;
        double averageExpansionAngle;
        double standardAngleDeviation;

        for (Particle particle : flux.getParticles()) {

            if (particle.isDeleted()) {
                deletedAmount++;
                continue;
            }

            if (particle.isAbsorbed()) {
                absorbedAmount++;
                continue;
            }

            point = getCoordinateOnDetector(particle);
            particle.setCoordinate(point);

            if (!isParticleWithinBorders(particle)) {
                outOfDetectorAmount++;
                continue;
            }

            int zCellNumber = (int) ((point.getZ() - leftBottomPoint.getZ()) / cellWidth);
            int yCellNumber = (int) ((point.getY() - leftBottomPoint.getY()) / cellWidth);

            if (particle.isChanneled()) {
                channeledImage.add(new CartesianPoint2D(point.getZ(), point.getY()));
                channeledCells.get(zCellNumber).get(yCellNumber).register();
                channeledCellsZ.get(zCellNumber).register();
                channeledCellsY.get(yCellNumber).register();
                expansionAngleSum += particle.getExpansionAngle();
                channeledAmount++;
            } else {
                piercedImage.add(new CartesianPoint2D(point.getZ(), point.getY()));
                piercedCells.get(zCellNumber).get(yCellNumber).register();
                piercedCellsZ.get(zCellNumber).register();
                piercedCellsY.get(yCellNumber).register();
                piercedAmount++;
            }
        }

        averageExpansionAngle = expansionAngleSum / channeledAmount;
        standardAngleDeviation = getStandardAngleDeviation(flux.getParticles(), averageExpansionAngle, channeledAmount);

        Logger.info(MessagePool.detectingParticlesFinish());

        return Distribution.builder()
                .setChanneledImage(channeledImage)
                .setPiercedImage(piercedImage)
                .setChanneledCells(channeledCells)
                .setPiercedCells(piercedCells)
                .setChanneledCellsX(channeledCellsZ)
                .setPiercedCellsX(piercedCellsZ)
                .setChanneledCellsY(channeledCellsY)
                .setPiercedCellsY(piercedCellsY)
                .setChanneledAmount(channeledAmount)
                .setPiercedAmount(piercedAmount)
                .setOutOfDetectorAmount(outOfDetectorAmount)
                .setAbsorbedAmount(absorbedAmount)
                .setDeletedAmount(deletedAmount)
                .setAverageExpansionAngle(averageExpansionAngle)
                .setStandardAngleDeviation(standardAngleDeviation)
                .setWidth(width)
                .build();
    }

    private CartesianPoint getCoordinateOnDetector(final Particle p) {
        double x = p.getCoordinate().getX();
        double y = p.getCoordinate().getY();
        double z = p.getCoordinate().getZ();

        double Vx = p.getSpeed().getX();
        double Vy = p.getSpeed().getY();
        double Vz = p.getSpeed().getZ();

        double L = leftBottomPoint.getX();

        return new CartesianPoint(L, (Vy / Vx) * (L - x) + y, (Vz / Vx) * (L - x) + z);
    }

    private boolean isParticleWithinBorders(final Particle p) {
        return  p.getCoordinate().getY() > leftBottomPoint.getY() &&
                p.getCoordinate().getY() < leftBottomPoint.getY() + width &&
                p.getCoordinate().getZ() > leftBottomPoint.getZ() &&
                p.getCoordinate().getZ() < leftBottomPoint.getZ() + width;
    }

    private double getStandardAngleDeviation(final List<? extends Particle> particles, double averageExpansionAngle,
                                             int channeledAmount) {
        double tempSum = 0;

        for (Particle particle : particles) {
            if (particle.isChanneled()) {
                tempSum += (particle.getExpansionAngle() - averageExpansionAngle) *
                        (particle.getExpansionAngle() - averageExpansionAngle);
            }
        }

        return Math.sqrt(tempSum / (channeledAmount - 1));
    }
}