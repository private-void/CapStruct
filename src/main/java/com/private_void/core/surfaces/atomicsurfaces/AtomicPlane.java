package com.private_void.core.surfaces.atomicsurfaces;

import com.private_void.core.detectors.Detector;
import com.private_void.core.fluxes.Flux;
import com.private_void.core.geometry.Point3D;
import com.private_void.core.geometry.Vector3D;
import com.private_void.core.particles.AtomFactory;
import com.private_void.core.particles.ChargedParticle;
import com.private_void.core.particles.Particle;

import java.util.ArrayList;
import java.util.Iterator;

import static com.private_void.utils.Constants.PI;

public class AtomicPlane extends AtomicSurface {
    private float size;

    public AtomicPlane(final AtomFactory atomFactory, final Point3D frontCoordinate, float period, float chargeNumber,
                       float size) {
        super(atomFactory, frontCoordinate, period, chargeNumber);
        this.size = size;
        this.detector = new Detector(new Point3D(frontCoordinate.getX() + size, frontCoordinate.getY(), frontCoordinate.getZ()), size);
        createAtoms();
    }

    @Override
    public void interact(Flux flux) {
        ChargedParticle p;
        Point3D newCoordinate;
        float angle;
        Iterator<? extends Particle> iterator = flux.getParticles().iterator();

        while (iterator.hasNext()) {
            try {
                p = (ChargedParticle) iterator.next();
                angle = PI / 2 - p.getSpeed().getAngle(getNormal(p.getCoordinate()).inverse());

                if (angle < getCriticalAngle(p)) {
                    newCoordinate = p.getCoordinate();

                    while (newCoordinate.getX() <= frontCoordinate.getX() + size) {
                        p.setCoordinate(newCoordinate);
                        p.setSpeed(getSpeedByPotential(getPotential(newCoordinate)));
                        newCoordinate = getNewCoordinate(p);
                    }
                } else {
                    iterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        detector.detect(flux);
    }

    @Override
    protected Vector3D getNormal(final Point3D point) {
        return new Vector3D(0.0f, 1.0f, 0.0f);
    }

    @Override
    protected Vector3D getAxis(final Point3D point) {
        return new Vector3D(0.0f, 0.0f, 1.0f);
    }

    @Override
    protected void createAtoms() {
        atoms = new ArrayList<>();

        float x = frontCoordinate.getX();
        float y = frontCoordinate.getY();
        float z = frontCoordinate.getZ() - size / 2;

        while (x <= frontCoordinate.getX() + size) {
            while (z <= frontCoordinate.getZ() + size / 2) {
                atoms.add(atomFactory.getNewAtom(new Point3D(x, y, z), chargeNumber));
                z += period;
            }
            x += period;
            z = frontCoordinate.getZ() - size / 2;
        }
    }

    @Override
    protected Point3D getNewCoordinate(final ChargedParticle p) {
        float x = p.getCoordinate().getX();
        float y = p.getCoordinate().getY();
        float z = p.getCoordinate().getZ();

        float Vx = p.getSpeed().getX();
        float Vy = p.getSpeed().getY();
        float Vz = p.getSpeed().getZ();

        return new Point3D(x + Vx, y + Vy, z + Vz);
    }

    @Override
    protected float getCriticalAngle(final ChargedParticle particle) {
        return 0;
    }

    @Override
    protected float getPotential(final Point3D coordinate) {
        return 0;
    }

    @Override
    protected Vector3D getSpeedByPotential(float potential) {
        return null;
    }
}