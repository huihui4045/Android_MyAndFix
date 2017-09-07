package android_myandfix.android_myandfix.web;


import android_myandfix.android_myandfix.Replace;

/**
 * Created by david on 2017/8/25.
 * 模拟  java  -->class   ---dex
 * 服务端
 * 制定caculator  修复哪个类里面具体的方法
 *
 */

public class Caclutor {
    @Replace(clazz = "android_myandfix.android_myandfix.Caclutor",method = "caculator")
    public int caculator()
    {
        //10/0
        int i=1;
        int j=10;
//模拟异常产生adb
        return j/i;
    }


}
