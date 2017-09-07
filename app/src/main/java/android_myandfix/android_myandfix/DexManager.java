package android_myandfix.android_myandfix;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 * Created by molu_ on 2017/9/5.
 */

public class DexManager {
    private Context context;
    private static final DexManager ourInstance = new DexManager();

    public static DexManager getInstance() {
        return ourInstance;
    }

    private DexManager() {
    }

    public void  loadFile(File file){

        try {
            DexFile dexFile=DexFile.loadDex(file.getAbsolutePath(),
                    new File(context.getCacheDir(),"opt").getAbsolutePath(),Context.MODE_PRIVATE);

            Enumeration<String> entries = dexFile.entries();

            while (entries.hasMoreElements()){

                String className = entries.nextElement();


             Class clazz=dexFile.loadClass(className,context.getClassLoader());

                if (clazz!=null){

                    fixClass(clazz);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fixClass(Class clazz) {

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {

            Replace replace = method.getAnnotation(Replace.class);

            if (replace==null) continue;

            String wrongClazzName= replace.clazz();


            String wrongMethodName = replace.method();

            try {
                Class wrongClass=  Class.forName(wrongClazzName);


              Method wrongMethod=  wrongClass.getDeclaredMethod(wrongMethodName,method.getParameterTypes());


                replace(method,wrongMethod);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }

    public native void replace(Method RightMethod, Method wrongMethod);
}
