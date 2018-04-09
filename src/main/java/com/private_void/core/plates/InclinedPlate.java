package com.private_void.core.plates;

import com.private_void.core.detectors.Detector;
import com.private_void.core.geometry.coordinates.CartesianPoint;
import com.private_void.core.geometry.coordinates.Point3D;
import com.private_void.core.geometry.coordinates.ReferenceFrame;
import com.private_void.core.surfaces.capillar_factories.RotatedCapillarFactory;

import static com.private_void.utils.Generator.generator;

public class InclinedPlate extends Plate {
    private final RotatedCapillarFactory capillarFactory;
    private final double sideLength;

    public InclinedPlate(final RotatedCapillarFactory capillarFactory, final CartesianPoint center, double capillarsDensity,
                     double sideLength) {
        super(center, capillarFactory.getRadius(), capillarFactory.getLength(), capillarsDensity);
        this.capillarFactory = capillarFactory;
        this.sideLength = sideLength;
        createCapillars();
        this.detector = new Detector(getDetectorsCoordinate(), sideLength * 3.0);
    }

    @Override
    protected void createCapillars() {
        System.out.println("Creating capillars start ...");
        long start = System.nanoTime();

        double frontSquare = sideLength * sideLength;
        double minCapillarSquare = (2.0 * capillarRadius) * (2.0 * capillarRadius);
        double maxCapillarDensity = 1.0 / minCapillarSquare;

        if (capillarsDensity > maxCapillarDensity) {
            System.out.println("Capillars density is too big, it has been automatically set to " + maxCapillarDensity);
            capillarsAmount = (int) (frontSquare / minCapillarSquare);
            // todo заполняю сеткой впритирку
            capillars = null;

        } else if (capillarsDensity > 0.67 * maxCapillarDensity) {
            System.out.println("Capillars density is very big, so it has been automatically set to " + maxCapillarDensity);
            /*todo capillarsAmount = ...
            заполняю сеткой с каким-то шагом*/
            capillars = null;

        } else {
            capillarsAmount = (int) (capillarsDensity * frontSquare);

            CartesianPoint.Factory coordinateFactory = generator().getXFlatUniformDistribution(center,
                    sideLength / 2 - capillarRadius,
                    sideLength / 2 - capillarRadius);

            CartesianPoint[] capillarsCenters = new CartesianPoint[capillarsAmount];
            CartesianPoint coordinate;

            for (int i = 0; i < capillarsAmount; i++) {
                do {
                    coordinate = coordinateFactory.getCoordinate();
                } while (!isCapillarCoordinateValid(capillarsCenters, coordinate));

                capillarsCenters[i] = coordinate;
                capillars.add(capillarFactory.getNewCapillar(
                        coordinate,
                        ReferenceFrame.builder()
                                .atPoint(coordinate)
                                .setAngleAroundOY(Math.toRadians(15.0))
                                .setAngleAroundOZ(Math.toRadians(15.0))
                                .build()));

                if (i % (capillarsAmount / 10) == 0.0) System.out.println("    ... " + (i * 100 / capillarsAmount) + "% capillars created");
            }
        }

        long finish = System.nanoTime();
        System.out.println("Creating capillars fifnish. Total time = = " + (finish - start) / 1_000_000 + " ms");
        System.out.println();
    }

    @Override
    protected CartesianPoint getDetectorsCoordinate() {
        return new CartesianPoint(center.getX() + width, center.getY(), center.getZ());
    }

    @Override
    protected boolean isCapillarCoordinateValid(Point3D[] coordinates, Point3D coordinate) {
        int i = 0;
        while (coordinates[i] != null && i < coordinates.length) {
            if ((coordinate.getQ2() - coordinates[i].getQ2()) * (coordinate.getQ2() - coordinates[i].getQ2())
                    + (coordinate.getQ3() - coordinates[i].getQ3()) * (coordinate.getQ3() - coordinates[i].getQ3())
                    < 4.0 * capillarRadius * capillarRadius) {
                return false;
            }
            i++;
        }
        return true;
    }
}