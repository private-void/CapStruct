package com.private_void.core.plates;

import com.private_void.app.Logger;
import com.private_void.core.detectors.Detector;
import com.private_void.core.detectors.Distribution;
import com.private_void.core.fluxes.Flux;
import com.private_void.core.geometry.space_3D.coordinates.CartesianPoint;
import com.private_void.core.geometry.space_3D.coordinates.Point3D;
import com.private_void.core.geometry.space_3D.reference_frames.ReferenceFrame;
import com.private_void.core.geometry.space_3D.vectors.Vector;
import com.private_void.core.particles.Particle;
import com.private_void.core.surfaces.Capillar;
import com.private_void.core.surfaces.CapillarSystem;

import java.util.ArrayList;
import java.util.List;

public abstract class Plate implements CapillarSystem {
    protected static final int CAPILLARS_PER_DOMAIN_AMOUNT = 4;

    protected final CartesianPoint center;
    protected final double capillarRadius;
    protected final double capillarsDensity;

    protected int capillarsAmount;
    protected List<Capillar> capillars;
    protected Detector detector;

    public Plate(final CartesianPoint center, double radius, double capillarsDensity) {
        this.center = center;
        this.capillarRadius = radius;
        this.capillarsDensity = capillarsDensity;
        this.capillars = new ArrayList<>();
    }

    @Override
    public Distribution interact(Flux flux) {
        Logger.interactionStart();

        ReferenceFrame.Converter converter;

        int capillarsCounter = 0;
        int tenPercentOfCapillarsAmount = capillarsAmount / 10;

        for (Capillar capillar : capillars) {
            converter = new ReferenceFrame.Converter(capillar.getReferenceFrame());

            if (++capillarsCounter % tenPercentOfCapillarsAmount == 0.0) {
                Logger.processedCapillarsPercent(capillarsCounter * 10 / tenPercentOfCapillarsAmount);
            }

            for (Particle particle : flux.getParticles()) {
                if (particle.isInteracted()) {
                    continue;
                }

                CartesianPoint coordinateInGlobalRefFrame = new CartesianPoint(particle.getCoordinate());
                Vector speedInGlobalRefFrame = Vector.set(particle.getSpeed());

                converter.convert(particle);

                if (capillar.willParticleGetInside(particle)) {
                    capillar.interact(particle);
                    converter.convertBack(particle);
                    particle.setInteracted();
                    continue;
                }

                particle.setCoordinate(coordinateInGlobalRefFrame);
                particle.setSpeed(speedInGlobalRefFrame);
            }
        }

        Logger.interactionFinish();

        return detector.detect(flux);
    }

    @Override
    public Detector getDetector() {
        return detector;
    }

    protected abstract CartesianPoint getDetectorsCoordinate();

    protected abstract void createCapillars();

    protected abstract boolean isCapillarCoordinateValid(final Point3D[] coordinates, Point3D coordinate);
}