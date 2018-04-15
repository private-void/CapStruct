package com.private_void.core.plates;

import com.private_void.app.Logger;
import com.private_void.core.detectors.Detector;
import com.private_void.core.geometry.coordinates.CartesianPoint;
import com.private_void.core.geometry.coordinates.CylindricalPoint;
import com.private_void.core.geometry.coordinates.Point3D;
import com.private_void.core.geometry.reference_frames.ReferenceFrame;
import com.private_void.core.surfaces.Capillar;
import com.private_void.core.surfaces.capillar_factories.RotatedTorusFactory;
import com.private_void.utils.Utils;

import static com.private_void.utils.Generator.generator;

public class TorusPlate extends Plate {
    private static final double DETECTORS_DISTANCE_COEFFICIENT = 5.0;

    private final RotatedTorusFactory capillarFactory;
    private final double sideLength;
    private final double width;
    private final double maxAngleR;
    private final CartesianPoint focus;

    public TorusPlate(final RotatedTorusFactory capillarFactory, final CartesianPoint center, double capillarsDensity,
                      double maxAngleR) {
        super(center, capillarFactory.getRadius(), capillarsDensity);
        this.capillarFactory = capillarFactory;
        this.maxAngleR = maxAngleR;
        this.width = capillarFactory.getLength();
        this.focus = center.shift(DETECTORS_DISTANCE_COEFFICIENT * width, 0.0, 0.0);
        this.sideLength = (focus.getX() - width) * Math.tan(maxAngleR);

        this.detector = new Detector(getDetectorsCoordinate(), sideLength);
        createCapillars();
    }

    @Override
    protected void createCapillars() {
        Logger.creatingCapillarsStart();

        double frontSquare = Math.PI * sideLength * sideLength / 4.0;
        double minCapillarSquare = (2.0 * capillarRadius) * (2.0 * capillarRadius);
        double maxCapillarDensity = 1.0 / minCapillarSquare;

        if (capillarsDensity > 0.67 * maxCapillarDensity) {
            double capillarsCellSideLength;

            if (capillarsDensity >= maxCapillarDensity) {
                Logger.capillarsDensityTooBig(maxCapillarDensity);
                capillarsAmount = (int) (frontSquare / minCapillarSquare);
                capillarsCellSideLength = 2.0 * capillarRadius;
            } else {
                capillarsAmount = (int) (capillarsDensity * frontSquare);
                capillarsCellSideLength = Math.sqrt(frontSquare / capillarsAmount);
            }

            int capillarsCounter = 0;
            int pool = (int) (sideLength / capillarsCellSideLength);
            double plateRadius = sideLength / 2.0;

            CartesianPoint initialPoint = center.shift(width, -plateRadius + capillarRadius, -plateRadius + capillarRadius);

            for (int i = 0; i < pool + 1; i++) {
                for (int j = 0; j < pool; j++) {

                    CartesianPoint capillarsEndCenter = initialPoint.shift(
                            0.0, i * capillarsCellSideLength, j * capillarsCellSideLength);

                    if (      (capillarsEndCenter.getY() - center.getY()) * (capillarsEndCenter.getY() - center.getY())
                            + (capillarsEndCenter.getZ() - center.getZ()) * (capillarsEndCenter.getZ() - center.getZ())
                            < (plateRadius - capillarRadius) * (plateRadius - capillarRadius)) {

                        capillars.add(createCapillarAtPoint(capillarsEndCenter, capillarsEndCenter.convertToCylindrical()));

                        if (++capillarsCounter % (capillarsAmount / 10) == 0.0) {
                            Logger.createdCapillarsPercent(i * 100 / capillarsAmount);
                        }
                    }
                }
            }
        } else {
            capillarsAmount = (int) (capillarsDensity * frontSquare);

            CylindricalPoint.Factory coordinateFactory = generator().getRadialUniformDistribution(
                    new CylindricalPoint(0.0, 0.0, width),
                    sideLength / 2.0 - capillarRadius);

            CartesianPoint[] capillarsEndCenters = new CartesianPoint[capillarsAmount];
            CartesianPoint capillarsEndCenter;
            CylindricalPoint cylindricalRandomPoint;

            for (int i = 0; i < capillarsAmount; i++) {
                do {
                    cylindricalRandomPoint = coordinateFactory.getCoordinate();
                    capillarsEndCenter = cylindricalRandomPoint.convertToCartesian();
                } while (!isCapillarCoordinateValid(capillarsEndCenters, capillarsEndCenter));

                capillarsEndCenters[i] = capillarsEndCenter;

                capillars.add(createCapillarAtPoint(capillarsEndCenter, cylindricalRandomPoint));

                if (i % (capillarsAmount / 10) == 0.0) {
                    Logger.createdCapillarsPercent(i * 100 / capillarsAmount);
                }
            }
        }

        Logger.creatingCapillarsFinish();
    }

    @Override
    protected CartesianPoint getDetectorsCoordinate() {
        return focus;
    }

    @Override
    protected boolean isCapillarCoordinateValid(Point3D[] coordinates, Point3D coordinate) {
        int i = 0;
        while (i < coordinates.length && coordinates[i] != null) {
            if ((coordinate.getQ2() - coordinates[i].getQ2()) * (coordinate.getQ2() - coordinates[i].getQ2())
                    + (coordinate.getQ3() - coordinates[i].getQ3()) * (coordinate.getQ3() - coordinates[i].getQ3())
                    < 4.0 * capillarRadius * capillarRadius) {
                return false;
            }
            i++;
        }
        return true;
    }

    private Capillar createCapillarAtPoint(final CartesianPoint capillarsEndCenter, final CylindricalPoint cylindricalPoint) {
        double curvAngleR = Math.atan(cylindricalPoint.getR() / (focus.getX() - width));
        double curvRadius = Utils.getTorusCurvRadius(width, curvAngleR);

        ReferenceFrame capillarsEndRefFrame = ReferenceFrame.builder()
                .atPoint(capillarsEndCenter)
                .setAngleAroundOX(cylindricalPoint.getPhi())
                .build();

        ReferenceFrame.Converter converter = new ReferenceFrame.Converter(capillarsEndRefFrame);

        CartesianPoint capillarsFrontCenter = converter.convertBack(
                converter.convert(capillarsEndCenter)
                        .shift(-curvRadius * Math.sin(curvAngleR),
                                0.0,
                                curvRadius * (1.0 - Math.cos(curvAngleR))));

        ReferenceFrame capillarsFrontRefFrame = ReferenceFrame.builder()
                .atPoint(capillarsFrontCenter)
                .setAngleAroundOX(cylindricalPoint.getPhi())
                .build();

        return capillarFactory.getNewCapillar(capillarsFrontCenter, curvAngleR, capillarsFrontRefFrame);
    }
}