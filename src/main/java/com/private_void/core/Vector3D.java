package com.private_void.core;

//TODO: разобраться со спецификатором final. Уместно ли его юзать как const для параметров, и как передавать значения по ссылке и по значению, константным и нет
public class Vector3D extends Point3D{

    private float[][] rotationMatrixXYZ;
    private float[][] rotationMatrixX;
    private float[][] rotationMatrixY;

    public Vector3D(float x, float y, float z) {
        super(x, y, z);
        normalize();
    }

    public Vector3D(final Point3D point3D) {
        super(point3D);
        normalize();
    }

    private void normalize()
    {
        float norm = (float) Math.sqrt(x * x + y * y + z * z);

        try {
            x = x / norm;
            y = y / norm;
            z = z / norm;
        }
        catch(ArithmeticException e) {
            System.out.println("Деление на ноль.");
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        normalize();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        normalize();
    }

    @Override
    public void setZ(float z) {
        super.setZ(z);
        normalize();
    }

    public float getAngle(final Vector3D vector3D) {
        return 2 * (float) (Math.acos((x * vector3D.getX() + y * vector3D.getY() + z * vector3D.getZ()) / (Math.sqrt(x * x + y * y + z * z) * Math.sqrt(vector3D.getX() * vector3D.getX() + vector3D.getY() * vector3D.getY() + vector3D.getZ() * vector3D.getZ()))) - Math.PI / 2);
    }

    public void setRotationMatrixX(final float angle)
    {
        rotationMatrixX[0][0] = 1.0f;
        rotationMatrixX[1][0] = 0.0f;
        rotationMatrixX[2][0] = 0.0f;

        rotationMatrixX[0][1] = 0.0f;
        rotationMatrixX[1][1] = (float) Math.cos(angle);
        rotationMatrixX[2][1] = (float) -Math.sin(angle);

        rotationMatrixX[0][2] = 0.0f;
        rotationMatrixX[1][2] = (float) Math.sin(angle);
        rotationMatrixX[2][2] = (float) Math.cos(angle);
    }

    public void setRotationMatrixY(final float angle)
    {
        rotationMatrixY[0][0] = (float) Math.cos(angle);
        rotationMatrixY[1][0] = 0.0f;
        rotationMatrixY[2][0] = (float) Math.sin(angle);

        rotationMatrixY[0][1] = 0.0f;
        rotationMatrixY[1][1] = 1.0f;
        rotationMatrixY[2][1] = 0.0f;

        rotationMatrixY[0][2] = (float) -Math.sin(angle);
        rotationMatrixY[1][2] = 0.0f;
        rotationMatrixY[2][2] = (float) Math.cos(angle);
    }

    public void setRotationMatrixXYZ(final float angle, final Vector3D axis)
    {
        rotationMatrixXYZ[0][0] = (float) (Math.cos(angle) + (1.0f - Math.cos(angle)) * axis.getX() * axis.getX());
        rotationMatrixXYZ[1][0] = (float) ((1.0f - Math.cos(angle)) * axis.getY() * axis.getX() - Math.sin(angle) * axis.getZ());
        rotationMatrixXYZ[2][0] = (float) ((1.0f - Math.cos(angle)) * axis.getZ() * axis.getX() + Math.sin(angle) * axis.getY());

        rotationMatrixXYZ[0][1] = (float) ((1.0f - Math.cos(angle)) * axis.getX() * axis.getY() + Math.sin(angle) * axis.getZ());
        rotationMatrixXYZ[1][1] = (float) (Math.cos(angle) + (1.0f - Math.cos(angle)) * axis.getY() * axis.getY());
        rotationMatrixXYZ[2][1] = (float) ((1.0f - Math.cos(angle)) * axis.getZ() * axis.getY() - Math.sin(angle) * axis.getX());

        rotationMatrixXYZ[0][2] = (float) ((1.0f - Math.cos(angle)) * axis.getX() * axis.getZ() - Math.sin(angle) * axis.getY());
        rotationMatrixXYZ[1][2] = (float) ((1.0f - Math.cos(angle)) * axis.getY() * axis.getZ() + Math.sin(angle) * axis.getX());
        rotationMatrixXYZ[2][2] = (float) (Math.cos(angle) + (1.0f - Math.cos(angle)) * axis.getZ() * axis.getZ());
    }

    public void turnAroundOX(final float angle)
    {
        float[] temp = {0.0f, 0.0f, 0.0f};
        setRotationMatrixX(angle);

        for (int i = 0; i < 3; i++) {
            temp[i] = rotationMatrixX[0][i] * x + rotationMatrixX[1][i] * y + rotationMatrixX[2][i] * z;
        }

        x = temp[0];
        y = temp[1];
        z = temp[2];

        normalize();
    }

    public void turnAroundOY(final float angle)
    {
        float[] temp = {0.0f, 0.0f, 0.0f};
        setRotationMatrixY(angle);

        for (int i = 0; i < 3; i++) {
            temp[i] = rotationMatrixY[0][i] * x + rotationMatrixY[1][i] * y + rotationMatrixY[2][i] * z;
        }

        x = temp[0];
        y = temp[1];
        z = temp[2];

        normalize();
    }

    public void turnAroundVector(final float angle, final Vector3D vector3D)
    {
        float[] temp = {0.0f, 0.0f, 0.0f};
        setRotationMatrixXYZ(angle, vector3D);

        for (int i = 0; i < 3; i++)
        {
            temp[i] = rotationMatrixXYZ[0][i] * x + rotationMatrixXYZ[1][i] * y + rotationMatrixXYZ[2][i] * z;
        }

        x = temp[0];
        y = temp[1];
        z = temp[2];

        normalize();
    }

    public void turnAroundVector(final float angle, final Vector3D vector3D, Vector3D result)
    {
        float[] temp = {0.0f, 0.0f, 0.0f};
        setRotationMatrixXYZ(angle, vector3D);

        for (int i = 0; i < 3; i++)
        {
            temp[i] = rotationMatrixXYZ[0][i] * x + rotationMatrixXYZ[1][i] * y + rotationMatrixXYZ[2][i] * z;
        }

        result.setX(temp[0]);
        result.setY(temp[1]);
        result.setZ(temp[2]);

        result.normalize();
    }

}
