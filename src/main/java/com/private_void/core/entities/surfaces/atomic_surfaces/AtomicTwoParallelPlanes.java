package com.private_void.core.entities.surfaces.atomic_surfaces;

import com.private_void.app.notifiers.Logger;
import com.private_void.app.notifiers.MessagePool;
import com.private_void.app.notifiers.ProgressProvider;
import com.private_void.core.entities.detectors.Detector;
import com.private_void.core.entities.detectors.Distribution;
import com.private_void.core.entities.fluxes.Flux;
import com.private_void.core.entities.particles.ChargedParticle;
import com.private_void.core.entities.particles.Particle;
import com.private_void.core.entities.surfaces.CapillarSystem;
import com.private_void.core.math.geometry.space_3D.coordinates.CartesianPoint;
import com.private_void.core.math.geometry.space_3D.vectors.Vector;

import java.util.Iterator;

import static com.private_void.core.constants.Constants.*;

public class AtomicTwoParallelPlanes extends AtomicSurface implements CapillarSystem {
    private final double size;
    private final double chargePlanarDensity;
    private final double width;
    private final Detector detector;

    public AtomicTwoParallelPlanes(final CartesianPoint front, double period, double width, double chargeNumber, double size) {
        super(front, period, chargeNumber);
        this.size = size;
        this.chargePlanarDensity = 1 / (period * period);
        this.width = width;
        this.detector = new Detector(new CartesianPoint(front.getX() + size, front.getY(), front.getZ()), size);
    }

    @Override
    public Distribution interact(Flux flux) {
        Logger.info(MessagePool.interactionStart());

        ChargedParticle particle;
        Particle.State state;
        double angleWithSurface;
        Vector normal = Vector.E_Y;

        int particlesCounter = 0;
        int tenPercentOfParticlesAmount = flux.getParticles().size() / 10;

//        setShieldingDistance(((ChargedParticle) flux.getParticles().get(0)).getChargeNumber());
        setCriticalAngle((ChargedParticle) flux.getParticles().get(0));

        for (Iterator<? extends Particle> iterator = flux.getParticles().iterator(); iterator.hasNext(); particlesCounter++) {
            if (particlesCounter % tenPercentOfParticlesAmount == 0.0) {
                ProgressProvider.getInstance().setProgress(particlesCounter * 10 / tenPercentOfParticlesAmount);
            }

            particle = (ChargedParticle) iterator.next();
            angleWithSurface = particle.getSpeed().getAngle(normal) - PI / 2.0;

            if (angleWithSurface <= criticalAngle) {
                state = new Particle.State(particle.getCoordinate(), particle.getSpeed());

                while (isPointInside(state.getCoordinate())) {
                    particle.setState(state);
                    state = getParticlesNewState(state, particle.getChargeNumber(), particle.getMass());
                }
            } else {
                particle.absorb();
                break;
            }

            particle.setChanneled();
        }

        Logger.info(MessagePool.interactionFinish());

        return detector.detect(flux);
    }

    @Override
    protected Vector getAxis(CartesianPoint point) {
        return Vector.E_X;
    }

    @Override
    protected CartesianPoint getAcceleration(final CartesianPoint coordinate, double particleChargeNumber, double mass) {
        double y1 = coordinate.getY() - front.getY();
        double y2 = front.getY() + width - coordinate.getY();

        double Fy1 = 2.0 * PI * particleChargeNumber * chargeNumber * (ELECTRON_CHARGE * ELECTRON_CHARGE)
                * chargePlanarDensity * (1.0 - y1 / Math.sqrt((y1 / shieldingDistance) * (y1 / shieldingDistance) + C_SQUARE));

        double Fy2 = 2.0 * PI * particleChargeNumber * chargeNumber * (ELECTRON_CHARGE * ELECTRON_CHARGE)
                * chargePlanarDensity * (1.0 - y2 / Math.sqrt((y2 / shieldingDistance) * (y2 / shieldingDistance) + C_SQUARE));

        return new CartesianPoint(0.0, ((Fy1 - Fy2) / mass) * TIME_STEP, 0.0 );
    }

    @Override
    protected Particle.State getParticlesNewState(Particle.State prevState, double chargeNumber, double mass) {
        CartesianPoint acceleration = getAcceleration(prevState.getCoordinate(), chargeNumber, mass);

        Vector nextSpeed = Vector.set(
                prevState.getSpeed().getX(),
                prevState.getSpeed().getY() + acceleration.getY(),
                prevState.getSpeed().getZ());

        return new Particle.State(prevState.getCoordinate().shift(nextSpeed), nextSpeed);
    }

    private boolean isPointInside(final CartesianPoint point) {
        return point.getX() <= front.getX() + size;
    }
}