package com.private_void.core.plates;

import com.private_void.core.detection.Detector;
import com.private_void.core.geometry.space_3D.coordinates.CartesianPoint;
import com.private_void.core.geometry.space_3D.coordinates.Point3D;
import com.private_void.core.geometry.space_3D.reference_frames.ReferenceFrame;
import com.private_void.core.surfaces.capillar_factories.RotatedCapillarFactory;
import com.private_void.utils.notifiers.Logger;
import com.private_void.utils.notifiers.MessagePool;
import com.private_void.utils.notifiers.ProgressProvider;

import static com.private_void.utils.Generator.generator;

public class InclinedPlate extends Plate {
    private final RotatedCapillarFactory capillarFactory;
    private final double sideLength;
    private final double width;

    public InclinedPlate(final RotatedCapillarFactory capillarFactory, final CartesianPoint center,
                         double capillarsDensity, double sideLength) {
        super(center, capillarFactory.getRadius(), capillarsDensity);
        this.capillarFactory = capillarFactory;
        this.sideLength = sideLength;
        this.width = capillarFactory.getLength();
        this.detector = new Detector(getDetectorsCoordinate(), sideLength);
        createCapillars();
    }

    @Override
    protected void createCapillars() {
        Logger.info(MessagePool.creatingCapillarsStart());

        double frontSquare = sideLength * sideLength;
        double minCapillarSquare = (2.0 * capillarRadius) * (2.0 * capillarRadius);
        double maxCapillarDensity = 1.0 / minCapillarSquare;

        if (capillarsDensity > 0.67 * maxCapillarDensity) {
            double capillarsCellSideLength;

            if (capillarsDensity >= maxCapillarDensity) {
                Logger.warning(MessagePool.capillarsDensityTooBig(maxCapillarDensity));
                capillarsAmount = (int) (frontSquare / minCapillarSquare);
                capillarsCellSideLength = 2.0 * capillarRadius;
            } else {
                capillarsAmount = (int) (capillarsDensity * frontSquare);
                capillarsCellSideLength = Math.sqrt(frontSquare / capillarsAmount);
            }

            int capillarsCounter = 0;
            int pool = (int) (sideLength / capillarsCellSideLength);
            double plateRadius = sideLength / 2.0;

            CartesianPoint initialPoint = center.shift(0.0, -plateRadius + capillarRadius, -plateRadius + capillarRadius);

            for (int i = 0; i < pool + 1; i++) {
                for (int j = 0; j < pool + 1; j++) {

                    CartesianPoint cellCenter = initialPoint.shift(
                            0.0, i * capillarsCellSideLength, j * capillarsCellSideLength);

                    CartesianPoint capillarsFrontCoordinate;
                    if (capillarsDensity >= maxCapillarDensity) {
                        capillarsFrontCoordinate = cellCenter;
                    } else {
                        capillarsFrontCoordinate = generator().getXFlatUniformDistribution(cellCenter,
                                capillarsCellSideLength / 2.0 - capillarRadius,
                                capillarsCellSideLength / 2.0 - capillarRadius)
                                .getCoordinate();
                    }

                    capillars.add(capillarFactory.getNewCapillar(
                            capillarsFrontCoordinate,
                            ReferenceFrame.builder()
                                    .atPoint(capillarsFrontCoordinate)
                                    .setAngleAroundOY(Math.toRadians(15.0))
                                    .setAngleAroundOZ(Math.toRadians(15.0))
                                    .build()));

                    if (++capillarsCounter % (capillarsAmount / 10) == 0.0) {
                        ProgressProvider.getInstance().setProgress(i * 100 / capillarsAmount);
                    }
                }
            }
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

                if (i % (capillarsAmount / 10) == 0.0) {
                    ProgressProvider.getInstance().setProgress(i * 100 / capillarsAmount);
                }
            }
        }

        Logger.info(MessagePool.creatingCapillarsFinish());
    }

    @Override
    protected CartesianPoint getDetectorsCoordinate() {
        return center.shift(width, 0.0, 0.0);
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
