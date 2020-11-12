package com.getling.gwframe.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数值常用.
 */
public class NumberUtil {


    /**
     * Instantiates a new Number util.
     */
    public NumberUtil() {
        super();
    }

    private static final int DEF_DIV_SCALE = 10;

    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();

    }

    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();

    }

    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();

    }

    public static double div(double d1, double d2) {

        return div(d1, d2, DEF_DIV_SCALE);

    }

    public static double div(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * <p>Convert a <code>String</code> to a <code>double</code>, returning
     * <code>0.0d</code> if the conversion fails.</p>
     * <p>If the string <code>str</code> is <code>null</code>,
     * <code>0.0d</code> is returned.</p>
     * <pre>
     *   NumberUtils.toDouble(null)   = 0.0d
     *   NumberUtils.toDouble("")     = 0.0d
     *   NumberUtils.toDouble("1.5")  = 1.5d
     * </pre>
     *
     * @param str the string to convert, may be <code>null</code>
     * @return the double represented by the string, or <code>0.0d</code>  if conversion fails
     * @since 2.1
     */
    public static double toDouble(String str) {
        return toDouble(str, 0.00d);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>double</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string <code>str</code> is <code>null</code>, the default
     * value is returned.</p>
     * <pre>
     *   NumberUtils.toDouble(null, 1.1d)   = 1.1d
     *   NumberUtils.toDouble("", 1.1d)     = 1.1d
     *   NumberUtils.toDouble("1.5", 0.0d)  = 1.5d
     * </pre>
     *
     * @param str          the string to convert, may be <code>null</code>
     * @param defaultValue the default value
     * @return the double represented by the string, or defaultValue  if conversion fails
     * @since 2.1
     */
    public static double toDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.00f);
    }

    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * <p>Convert a <code>String</code> to a <code>int</code>, returning
     * <code>0</code> if the conversion fails.</p>
     * <p>If the string <code>str</code> is <code>null</code>,
     * <code>0</code> is returned.</p>
     * <pre>
     *   NumberUtils.toInt(null)   = 0
     *   NumberUtils.toInt("")     = 0
     *   NumberUtils.toInt("1")  = 1
     * </pre>
     *
     * @param str the string to convert, may be <code>null</code>
     * @return the int represented by the string, or <code>0</code>  if conversion fails
     * @since 2.1
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * <p>Convert a <code>String</code> to a <code>int</code>, returning a
     * default value if the conversion fails.</p>
     * <p>If the string <code>str</code> is <code>null</code>, the default
     * value is returned.</p>
     * <pre>
     *   NumberUtils.toInt(null, 0)   = 0
     *   NumberUtils.toInt("", 0)     = 0
     *   NumberUtils.toInt("1", 0)  = 1
     * </pre>
     *
     * @param str          the string to convert, may be <code>null</code>
     * @param defaultValue the default value
     * @return the int represented by the string, or defaultValue  if conversion fails
     * @since 2.1
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static int toSafeInt(Integer num) {
        if (num == null) {
            return 0;
        }
        return num;
    }

    public static long toLong(String string) {
        return toLong(string, 0);
    }

    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static double toSafeDouble(Double num) {
        if (num == null) {
            return 0;
        }
        return num;
    }

    //-------------------------Max in array-------------------------------------------

    /**
     * <p>Returns the maximum value in an array.</p>
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static long max(long[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        long max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    /**
     * <p>Returns the maximum value in an array.</p>
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static int max(int[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        int max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    /**
     * <p>Returns the maximum value in an array.</p>
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static short max(short[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        short max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    /**
     * <p>Returns the maximum value in an array.</p>
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static byte max(byte[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        byte max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    /**
     * <p>Returns the maximum value in an array.</p>
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static double max(double[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        double max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Double.isNaN(array[j])) {
                return Double.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    /**
     * <p>Returns the maximum value in an array.</p>
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if <code>array</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>array</code> is empty
     */
    public static float max(float[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        float max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Float.isNaN(array[j])) {
                return Float.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }
    // 3 param max
    //-----------------------------------------------------------------------

    /**
     * <p>Gets the maximum of three <code>long</code> values.</p>
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    public static long max(long a, long b, long c) {
        if (b > a) {
            a = b;
        }
        if (c > a) {
            a = c;
        }
        return a;
    }

    /**
     * <p>Gets the maximum of three <code>int</code> values.</p>
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    public static int max(int a, int b, int c) {
        if (b > a) {
            a = b;
        }
        if (c > a) {
            a = c;
        }
        return a;
    }

    /**
     * <p>Gets the maximum of three <code>short</code> values.</p>
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    public static short max(short a, short b, short c) {
        if (b > a) {
            a = b;
        }
        if (c > a) {
            a = c;
        }
        return a;
    }

    /**
     * <p>Gets the maximum of three <code>byte</code> values.</p>
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    public static byte max(byte a, byte b, byte c) {
        if (b > a) {
            a = b;
        }
        if (c > a) {
            a = c;
        }
        return a;
    }

    /**
     * <p>Gets the maximum of three <code>double</code> values.</p>
     * <p>If any value is <code>NaN</code>, <code>NaN</code> is
     * returned. Infinity is handled.</p>
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    public static double max(double a, double b, double c) {
        return Math.max(Math.max(a, b), c);
    }

    /**
     * <p>Gets the maximum of three <code>float</code> values.</p>
     * <p>If any value is <code>NaN</code>, <code>NaN</code> is
     * returned. Infinity is handled.</p>
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    public static float max(float a, float b, float c) {
        return Math.max(Math.max(a, b), c);
    }

    /**
     * check float equals
     *
     * @param a the a
     * @param b the b
     * @return boolean
     */
    public static boolean floatEquals(float a, float b) {
        return (Float.compare(a, b) == 0);
    }

    /**
     * check double equals
     *
     * @param a the a
     * @param b the b
     * @return boolean
     */
    public static boolean doubleEquals(double a, double b) {
        return (Double.compare(a, b) == 0);
    }

    /**
     * 如果是整数,不显示小数点,如果小数点有一位,则保留成两位
     *
     * @param d the d
     * @return the string
     */
    public static String doubleFormat(double d) {
        return doubleEquals(d, (double) (int) d) ? String.valueOf((int) d) :
                new DecimalFormat("0.00").format(d);
    }

    /**
     * 1.00 ->1
     * 1.0 ->1
     * 1.10 ->1.1
     *
     * @param d the d
     * @return string
     */
    public static String doubleFormatDouble(double d) {

        String s = new DecimalFormat("0.00").format(d) + "";
        if (s.indexOf(".") > 0) {
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s;
    }
}
