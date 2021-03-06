//package com.private_void.core.entities.fluxes;
//
//import com.private_void.core.entities.particles.NeutralParticle;
//import com.private_void.core.entities.particles.Particle;
//import com.private_void.core.entities.surfaces.smooth_surfaces.smooth_capillars.single_smooth_capillars.SingleSmoothCylinder;
//import com.private_void.core.math.geometry.space_3D.coordinates.CartesianPoint;
//import com.private_void.core.math.geometry.space_3D.vectors.Vector;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.private_void.core.math.generators.Generator.generator;
//import static org.junit.Assert.assertTrue;
//
//public class FluxTest {
//
//    @Test
//    public void fluxesCompartionTest() {
//        ParallelFlux particlesDonor;
//
//        Particle.Factory neutralParticleFactory = NeutralParticle.getFactory(1.0);
//        CartesianPoint.Factory gaussDistribution = generator().getGaussDistribution(0.0, 1.0);
//
//        particlesDonor = new ParallelFlux(
//                neutralParticleFactory,
//                gaussDistribution,
//                CartesianPoint.ORIGIN,
//                Vector.set(1.0, -0.1, 0.0),
//                10,
//                1000,
//                1.0,
//                0.5);
//
//        List<? extends Particle> particles = particlesDonor.getParticles();
//        List<? extends Particle> channeledParticles1 = interactionWithGetNewByTurningTest(particles);
//        List<? extends Particle> channeledParticles2 = interactionWithGetNewByTurningTest(particles);
//
//        for (int i = 0; i < channeledParticles1.size(); i++) {
//            if (!channeledParticles1.get(i).isAbsorbed() && !channeledParticles2.get(i).isAbsorbed()) {
//                CartesianPoint p1 = channeledParticles1.get(i).getCoordinate();
//                CartesianPoint p2 = channeledParticles2.get(i).getCoordinate();
//
////                System.out.println(p1.getX() + " " + p1.getY() + " " + p1.getX());
////                System.out.println(p2.getX() + " " + p2.getY() + " " + p2.getX());
////                System.out.println();
//
//                assertTrue(p1.getX() == p2.getX());
//                assertTrue(p1.getY() == p2.getY());
//                assertTrue(p1.getZ() == p2.getZ());
//            }
//        }
//    }
//
//    private List<? extends Particle> interactionWithGetNewByTurningTest(List<? extends Particle> particles) {
//        ParallelFlux flux = new ParallelFlux(
//                NeutralParticle.getFactory(1.0),
//                generator().getGaussDistribution(0.0, 1.0),
//                CartesianPoint.ORIGIN,
//                Vector.set(1.0, -0.1, 0.0),
//                10,
//                1000,
//                1.0,
//                0.5);
//        flux.setParticles(copyParticles(particles));
//
//        SingleSmoothCylinder capillar = new SingleSmoothCylinder(
//                CartesianPoint.ORIGIN,
//                20.0,
//                1000.0,
//                0.0,
//                0.0,
//                1.0,
//                90.0);
//
//        System.out.println("SingleSmoothCylinder interacton");
//        capillar.interact(flux);
////        capillar.getDetector().detect(flux);
//
//        return flux.getParticles();
//    }
//
//    private List<? extends Particle> copyParticles(List<? extends Particle> particles) {
//        List<Particle> copiedParticles = new ArrayList<>();
//        for (Particle p : particles) {
//            copiedParticles.add(NeutralParticle
//                    .getFactory(1.0)
//                    .getNewParticle(
//                            new CartesianPoint(p.getCoordinate()),
//                            Vector.set(p.getSpeed())));
//        }
//        return copiedParticles;
//    }
//}