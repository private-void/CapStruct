//package coordinate_conversion_tests;
//
//import org.junit.Test;
//
//import static junit.framework.TestCase.assertTrue;
//
//public class CapillarsPositioningTest {
//
//    @Test
//    public void capillarsPositionTest() {
//
//    }
//    @Test
//    public void capillarPositionTest() {
//        double sqrt2 = sqrt(2.0);
//
//        CartesianPoint s = new SphericalPoint(1_000.0, toRadians(0.0) + PI / 2.0, toRadians(0.0) + PI)
//                .convertToCartesian();
//        CartesianPoint c = new SphericalPoint(1_000.0, toRadians(0.0), toRadians(0.0))
//                .shift(0.0, PI / 2.0, PI)
//                .convertToCartesian();
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//
//        // Theta = 0, Phi = 0
//        s = new SphericalPoint(1_000.0, toRadians(0.0) + PI / 2.0, toRadians(0.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-1_000.0, 0.0, 0.0);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = 0, Phi = 1
//        s = new SphericalPoint(1_000.0, Math.toRadians(0.0) + PI / 2.0, Math.toRadians(45.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-1_000.0 / sqrt2, -1_000.0 / sqrt2, 0.0);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = 0, Phi = -1
//        s = new SphericalPoint(1_000.0, Math.toRadians(0.0) + PI / 2.0, Math.toRadians(-45.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-1_000.0 / sqrt2, 1_000.0 / sqrt2, 0.0);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = 1, Phi = 0
//        s = new SphericalPoint(1_000.0, Math.toRadians(45.0) + PI / 2.0, Math.toRadians(0.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-1_000.0 / sqrt2, 0.0, -1_000.0 / sqrt2);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = -1, Phi = 0
//        s = new SphericalPoint(1_000.0, Math.toRadians(-45.0) + PI / 2.0, Math.toRadians(0.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-1_000.0 / sqrt2, 0.0, 1_000.0 / sqrt2);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = 1, Phi = 1
//        s = new SphericalPoint(1_000.0, Math.toRadians(45.0) + PI / 2.0, Math.toRadians(45.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-500.0, -500.0, -707.106781186547);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = -1, Phi = -1
//        s = new SphericalPoint(1_000.0, Math.toRadians(-45.0) + PI / 2.0, Math.toRadians(-45.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-500.0, 500.0, 707.106781186547);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = 1, Phi = -1
//        s = new SphericalPoint(1_000.0, Math.toRadians(45.0) + PI / 2.0, Math.toRadians(-45.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-500.0, 500.0, -707.106781186547);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//
//        // Theta = -1, Phi = 1
//        s = new SphericalPoint(1_000.0, Math.toRadians(-45.0) + PI / 2.0, Math.toRadians(45.0) + PI)
//                .convertToCartesian();
//        c = new CartesianPoint(-500.0, -500.0, 707.106781186547);
//
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        Assert.assertTrue(Utils.compareToZero(s.getY() - c.getY()));
//        Assert.assertTrue(Utils.compareToZero(s.getX() - c.getX()));
//        //---
//    }
//
//    @Test
//    public void willParticleGetInsideTest() {
//        // ...
//    }
//
//    @Test
//    public void isCapillarCoordinateValidTest() {
//        // ...
//    }
//
//    @Test
//    public void sphericalPositioningTest() {
//
//        RotatedCapillar.Factory rotatedSmoothCylinderFactory = RotatedSmoothCylinder.getFactory(
//                7.0,
//                500.0,
//                0.0,
//                0.0,
//                1.0,
//                90.0);
//
//        CurvedPlate curvedPlate = new CurvedPlate(
//                rotatedSmoothCylinderFactory,
//                CartesianPoint.ORIGIN,
//                0.0034,
//                toRadians(3.65),
//                2000.0);
//
//        List<Capillar> capillars = curvedPlate.getCapillars();
//
//        for (Capillar capillar : capillars) {
//            RotatedSmoothCylinder cylinder = (RotatedSmoothCylinder) capillar;
//
//            CartesianPoint c = cylinder.getPosition().convertToCartesian();
//            assertTrue(Utils.compareToZero(
//                    c.getX() * c.getX() +
//                            c.getY() * c.getY() +
//                            c.getX() * c.getX() -
//                            2000.0 * 2000.0));
//
//            assertTrue(Utils.compareToZero(
//                    (cylinder.getFront().getX() - 2000.0) * (cylinder.getFront().getX() - 2000.0) +
//                            cylinder.getFront().getY() * cylinder.getFront().getY() +
//                            cylinder.getFront().getX() * cylinder.getFront().getX() -
//                            2000.0 * 2000.0));
//        }
//    }
//}