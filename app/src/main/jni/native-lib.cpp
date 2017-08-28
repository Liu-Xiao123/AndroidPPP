#include <jni.h>
#include <string>
#include "rtklib.h"
#include "naviMain.h"

char*   Jstring2CStr(JNIEnv * env,   jstring   jstr)// java中的jstring, 转化为c的一个字符数组
{
    char*   rtn   =   NULL;
    jclass   clsstring   =   env->FindClass("java/lang/String");
    jstring   strencode   =   env->NewStringUTF("GB2312");
    jmethodID   mid   =   env->GetMethodID(clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
    jbyteArray   barr=   (jbyteArray)env->CallObjectMethod(jstr,mid,strencode); // String .getByte("GB2312");
    jsize   alen   =   env->GetArrayLength(barr);
    jbyte*   ba   =   env->GetByteArrayElements(barr,JNI_FALSE);
    if(alen   >   0)
    {
        rtn   =   (char*)malloc(alen+1);         //new   char[alen+1]; "\0"
        memcpy(rtn,ba,alen);
        rtn[alen]=0;
    }
    env->ReleaseByteArrayElements(barr,ba,0);  //释放内存

    return rtn;
}
extern "C"
JNIEXPORT void JNICALL   //返回值类型
Java_com_exa_lj_rtkgps_MainActivity_accessField(JNIEnv *env, jobject instance) {

    jfieldID fid;
    jstring  jstr;
    const char *str;

    /* Get a reference to obj's class */
    jclass cls = env->GetObjectClass(instance);

    /* Look for the instance field s in cls */
    fid = env->GetFieldID(cls, "findcbuffer", "Ljava/lang/String;");

    if (fid == NULL) {
        return; /* failed to find the field */
    }

/* Read the instance field s */
    jstr = (jstring) env->GetObjectField(instance, fid);
    str  = env->GetStringUTFChars(jstr, NULL);
    if (str == NULL) {
        return; /* out of memory */
    }
    env->ReleaseStringUTFChars(jstr, str);

    /* Create a new string and overwrite the instance field */
    jstr = env->NewStringUTF((const char *) ctojava);
    if (jstr == NULL) {
        return; /* out of memory */
    }
    env->SetObjectField(instance, fid, jstr);

}

//extern "C"
//JNIEXPORT void JNICALL
//Java_com_exa_lj_rtkgps_Findc_accessField(JNIEnv *env, jobject instance) {
//// TODO
//    jfieldID fid;
//    jstring  jstr;
//    const char *str;
//
//    /* Get a reference to obj's class */
//    jclass cls = env->GetObjectClass(instance);
//
///* Look for the instance field s in cls */
//    fid = env->GetFieldID(cls, "findc", "Ljava/lang/String;");
//
//    if (fid == NULL) {
//        return; /* failed to find the field */
//    }
///* Read the instance field s */
//    jstr = (jstring) env->GetObjectField(instance, fid);
//    str  = env->GetStringUTFChars(jstr, NULL);
//    if (str == NULL) {
//        return; /* out of memory */
//    }
//    env->ReleaseStringUTFChars(jstr, str);
//    /* Create a new string and overwrite the instance field */
//    jstr = env->NewStringUTF((const char *) ctojava);
//    if (jstr == NULL) {
//        return; /* out of memory */
//    }
//    env->SetObjectField(instance, fid, jstr);
//
//
//}


extern "C"
jstring  //返回值类型
Java_com_exa_lj_rtkgps_MainActivity_stringFromJNI(
        JNIEnv *env, //
        jobject /* this */) {
        rtksvr_t svr;
        //执行rtklib主函数，实例化对象naviM
        naviMain naviM;
        naviM.InitSolBuff();
        naviM.LoadOpt();
        naviM.OpenMoniPort(naviM.MoniPort);
        naviM.SvrStart();

    std::string hello ="lll" ;
    return env->NewStringUTF(hello.c_str());// c_str()：生成一个const char*指针，指向以空字符终止的数组
}
