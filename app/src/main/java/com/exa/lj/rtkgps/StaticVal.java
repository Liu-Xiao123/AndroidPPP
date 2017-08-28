package com.exa.lj.rtkgps;

/**
 * Created by Administrator on 2017-4-28.
 */

public class StaticVal implements Cloneable{

    public static String strbuffer;
    public static String strB;
    public static String strL;
    public static String strH;
    public static String strN;
    public static String strE;
    public static String strU;
    public static String strSATNUM;
    public static String strPOSITIONTYPE;

    public static String strobspath;
    public static String strnavpath;
    public static String strssrpath;

    public static boolean threadStatus;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
