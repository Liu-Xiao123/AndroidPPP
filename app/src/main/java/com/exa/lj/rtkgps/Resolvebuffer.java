package com.exa.lj.rtkgps;

/**
 * Created by Administrator on 2017-5-11.
 * 从JNI获取结果字符串用于显示
 */

public class Resolvebuffer {

    public int resolvebuffer(String buffer,String[] strings){
        String tempbuffer=buffer;
        if(tempbuffer.length()>141||tempbuffer.length()<=0)return 0;
        strings[0]=tempbuffer.substring(23,38);
        strings[1]=tempbuffer.substring(38,53);
        strings[2]=tempbuffer.substring(53,64);
        strings[3]=tempbuffer.substring(72,81);
        strings[4]=tempbuffer.substring(81,90);
        strings[5]=tempbuffer.substring(90,99);
        strings[6]=tempbuffer.substring(64,68);
        strings[7]=tempbuffer.substring(68,72);
        int positiontype=Integer.parseInt(strings[6].trim());
    switch(positiontype)
    {
        case 0:strings[6]="no"                ;break;
        case 1:strings[6]="fix"               ;break;
        case 2:strings[6]="float"             ;break;
        case 3:strings[6]="SBAS"              ;break;
        case 4:strings[6]="DGPS"              ;break;
        case 5:strings[6]="SINGLE"            ;break;
        case 6:strings[6]="PPP"               ;break;

    }

        return 1;
    }
}
