package sample;

import java.math.BigDecimal;

/**
 * Created by Martin on 5.3.2017
 * Repository branch: Git/SphereRep
 * Branch initiated: 5.3.2017, {master} branch
 */

class SystemMethods
{
    /*
     * Cast to 3D method
     */
        private static byte[][][] castTo3D(int r, int border)
        {
            double dynamicR,dynamic;
            int size = (int) Math.floor(r/border);

            int[][] quarter = new int[size][size];

            //get quarter 2D
            int pos1 = 0;
            int pos2;

            for (int i = border; i < r; i+=border)
            {
                pos2= 0 ;
                dynamicR =  r * (Math.sin(Math.PI/2 + i*(Math.PI/r)));
                for (int j = border; j < dynamicR; j+=border)
                {
                    dynamic = Math.floor(2*dynamicR*(Math.sin(Math.PI/2 + i*(Math.PI/r))));

                    if (Math.floor(dynamic/border) % 2 == 0)
                        quarter[pos1][pos2] = (int) Math.floor(dynamic/border);
                    else
                        quarter[pos1][pos2] = (int) Math.floor(dynamic/border)-1;
                    pos2++;
                }
                pos1++;

            }

            int[][] full = new int[size*2][size*2];

            //get full 2D
            for (int i = 0; i < quarter.length; i++)
            {
                for (int j = 0; j < quarter[i].length; j++)
                {
                    full[i][j] = quarter[size-i-1][size-j-1];
                    full[i][j+size] = quarter[size-i-1][j];
                    full[size+i][j] = quarter[i][size-j-1];
                    full[size+i][j+size] = quarter[i][j];
                }
            }

        //convert to 3D
        byte[][][] planet = new byte[size*2][size*2][size*2];

        int split;

        for (int i = 0; i <planet.length; i++)
        {
            for (int j = 0; j < planet[i].length; j++)
            {
                if (full[i][j] != 0)
                {
                    split = full[i][j] / 2;

                    for (int k = 0; k < planet[i][j].length; k++)
                        planet[i][j][k] = 0;

                    for (int k = size; k < size + split; k++)
                    {
                        planet[i][j][k] = 1;
                        planet[i][j][(2*size)-k] = 1;
                    }
                }

            }
        }

        return planet;
    }

    /*
     * Print method
     */
    static void print(byte[][][] array)
    {
        for (byte[][] a : array)
        {
            System.out.println();
            for (byte[] b : a)
            {
                System.out.println();
                for (byte c : b)
                {
                    System.out.print(c+" ");
                }
            }
        }
    }

    /*
     * getCubes method
     */
    private static int getCubes(byte[][][] sphere)
    {
        int cubes = 0;

        for (byte[][] a : sphere)
        {
            for (byte[] b : a)
            {
                for (byte c : b)
                {
                    if (c==1)
                        cubes++;
                }
            }
        }

        return cubes;
    }

    /*
     * equation method
     */
    static BigDecimal equation(int r1, int r2, int border)
    {
        //spheres
        byte[][][] sphere = castTo3D(r1,border);
        byte[][][] sphere2 = castTo3D(r2,border);

        //cubes
        double cubes1 = getCubes(sphere);
        double cubes2 = getCubes(sphere2);

        //weights
        double m1 = 5972; //kg (*10^21)
        double m2 = 7347; //kg (*10^19)

        if (border == 1)
        {
            m1 /= cubes1;
            m2 /= cubes2;
        }
        else
        {
            byte[][][] weightTest1 = castTo3D(r1,1);
            byte[][][] weightTest2 = castTo3D(r2,1);

            double mCubes1 = getCubes(weightTest1);
            double mCubes2 = getCubes(weightTest2);

            m1 /= mCubes1; //kg (*10^21)
            m2 /= mCubes2; //kg (*10^19)

            m1 *= Math.pow(border,3);
            m2 *= Math.pow(border,3);
        }

        //constants
        double kappa = 6.67 * Math.pow(10, -11);
        int distance = 376292; //km

        //numbers
        double z,y,x,length,eq,total0;
        total0 = 0;

        //percentage
        int percentage,counter;
        percentage = 0;
        counter = 0;

        System.out.println("Combinations: " + cubes1*cubes2);
        System.out.println("0%");

        //cycle
        for (int i = 0; i < sphere.length; i++)
        {
            for (int j = 0; j < sphere[i].length; j++)
            {
                for (int k = 0; k < sphere[i][j].length; k++)
                {
                    //check
                    if (sphere[i][j][k] == 1)
                    {
                        for (int i2 = 0; i2 < sphere2.length; i2++)
                        {
                            for (int j2 = 0; j2 < sphere2[i2].length; j2++)
                            {
                                for (int k2 = 0; k2 < sphere2[i2][j2].length; k2++)
                                {
                                    //check
                                    if (sphere2[i2][j2][k2] == 1)
                                    {
                                        //assign length
                                        z = 1000*(distance + border*(i+i2) + border);
                                        y = 1000*(Math.abs((r1-border*j)-(r2-border*j2)));
                                        x = 1000*(Math.abs((r1-border*k)-(r2-border*k2)));

                                        //calculations and operations
                                        length = (x*x + z*z) + y*y; //^2
                                        eq = kappa*((m1*m2)/length);
                                        total0 += eq;

                                        //percentage
                                        counter++;

                                        if (counter >= (cubes1*cubes2)/100)
                                        {
                                            counter = 0;
                                            percentage++;
                                            System.out.println(percentage + "%");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //end of cycle

        //percentage
        if (percentage != 100)
            System.out.println("100%");

        //assign total
        return new BigDecimal(Double.toString(total0*Math.pow(10,40)));
    }
}
