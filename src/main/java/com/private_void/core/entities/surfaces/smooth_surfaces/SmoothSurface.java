package com.private_void.core.entities.surfaces.smooth_surfaces;

import com.private_void.core.entities.particles.NeutralParticle;
import com.private_void.core.exceptions.BadParticleException;
import com.private_void.core.math.geometry.space_3D.coordinates.CartesianPoint;
import com.private_void.core.math.geometry.space_3D.vectors.Vector;

public abstract class SmoothSurface {
    protected final CartesianPoint front;
    protected final double roughnessSize;
    protected final double roughnessAngleR;
    protected final double reflectivity;
    protected final double criticalAngleR;

    protected SmoothSurface(final CartesianPoint front, double roughnessSize, double roughnessAngleR, double reflectivity,
                            double criticalAngleR) {
        this.front = front;
        this.roughnessSize = roughnessSize;
        this.roughnessAngleR = roughnessAngleR;
        this.reflectivity = reflectivity;
        this.criticalAngleR = criticalAngleR;
    }

    protected abstract Vector getNormal(final CartesianPoint point);

    protected abstract Vector getParticleSpeedRotationAxis(final CartesianPoint point, final Vector normal);

    protected abstract CartesianPoint getHitPoint(final NeutralParticle p) throws BadParticleException;
}