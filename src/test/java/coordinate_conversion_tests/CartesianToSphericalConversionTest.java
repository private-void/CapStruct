package coordinate_conversion_tests;

import com.private_void.core.geometry.coordinates.CartesianPoint;
import com.private_void.core.geometry.coordinates.SphericalPoint;
import com.private_void.utils.Utils;
import org.junit.Test;

import static com.private_void.utils.Constants.PI;
import static org.junit.Assert.assertTrue;

public class CartesianToSphericalConversionTest {

    // Theta = 0, varing Phi
    @Test
    public void testTheta0Phi0() {
        SphericalPoint s = new SphericalPoint(100.0, 0.0, 0.0);
        SphericalPoint c = new CartesianPoint(0.0, 100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testTheta0PhiPI2() {
        SphericalPoint s = new SphericalPoint(100.0, 0.0, PI / 2.0);
        SphericalPoint c = new CartesianPoint(0.0, 100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testTheta0PhiPI() {
        SphericalPoint s = new SphericalPoint(100.0, 0.0, PI);
        SphericalPoint c = new CartesianPoint(0.0, 100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testTheta0Phi3PI2() {
        SphericalPoint s = new SphericalPoint(100.0, 0.0, 3.0 * PI / 2.0);
        SphericalPoint c = new CartesianPoint(0.0, 100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testTheta0Phi2PI() {
        SphericalPoint s = new SphericalPoint(100.0, 0.0, 2.0 * PI);
        SphericalPoint c = new CartesianPoint(0.0, 100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }
    //---

    // Theta = PI / 2, variing Phi
    @Test
    public void testThetaPI2Phi0() {
        SphericalPoint s = new SphericalPoint(100.0, PI / 2.0, 0.0);
        SphericalPoint c = new CartesianPoint(100.0, 0.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPI2PhiPI2() {
        SphericalPoint s = new SphericalPoint(100.0, PI / 2.0, PI / 2.0);
        SphericalPoint c = new CartesianPoint(0.0, 0.0, 100.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPI2PhiPI() {
        SphericalPoint s = new SphericalPoint(100.0, PI / 2.0, PI);
        SphericalPoint c = new CartesianPoint(-100.0, 0.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPI2Phi3PI2() {
        SphericalPoint s = new SphericalPoint(100.0, PI / 2.0, 3.0 * PI / 2.0);
        SphericalPoint c = new CartesianPoint(0.0, 0.0, -100.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPI2Phi2PI() {
        SphericalPoint s = new SphericalPoint(100.0, PI / 2.0, 2.0 * PI);
        SphericalPoint c = new CartesianPoint(100.0, 0.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }
    //---

    // Theta = PI, varing Phi
    @Test
    public void testThetaPIPhi0() {
        SphericalPoint s = new SphericalPoint(100.0, PI, 0.0);
        SphericalPoint c = new CartesianPoint(0.0, -100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPIPhiPI2() {
        SphericalPoint s = new SphericalPoint(100.0, PI, PI / 2.0);
        SphericalPoint c = new CartesianPoint(0.0, -100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPIPhiPI() {
        SphericalPoint s = new SphericalPoint(100.0, PI, PI);
        SphericalPoint c = new CartesianPoint(0.0, -100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPIPhi3PI2() {
        SphericalPoint s = new SphericalPoint(100.0, PI, 3.0 * PI / 2.0);
        SphericalPoint c = new CartesianPoint(0.0, -100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }

    @Test
    public void testThetaPIPhi2PI() {
        SphericalPoint s = new SphericalPoint(100.0, PI, 2.0 * PI);
        SphericalPoint c = new CartesianPoint(0.0, -100.0, 0.0).convertToSpherical();

        assertTrue(Utils.compareToZero(s.getRadius() - c.getRadius()) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() + 2.0 * PI) ||
                Utils.compareToZero(s.getRadius() - c.getRadius() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getTheta() - c.getTheta()) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() + 2.0 * PI) ||
                Utils.compareToZero(s.getTheta() - c.getTheta() - 2.0 * PI));

        assertTrue(Utils.compareToZero(s.getPhi() - c.getPhi()) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() + 2.0 * PI) ||
                Utils.compareToZero(s.getPhi() - c.getPhi() - 2.0 * PI));
    }
    //---
}