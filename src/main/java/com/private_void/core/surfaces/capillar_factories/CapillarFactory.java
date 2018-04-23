package com.private_void.core.surfaces.capillar_factories;

import com.private_void.core.geometry.space_3D.coordinates.CartesianPoint;
import com.private_void.core.surfaces.Capillar;

public interface CapillarFactory {

    Capillar getNewCapillar(final CartesianPoint coordinate);

    double getRadius();

    double getLength();
}