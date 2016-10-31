package com.example.administrator.day1028.app;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * @author Administrator
 * @version $Rev$
 * @time 2016/10/29 10:35
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class GlobalApp extends Application {
    private static GlobalApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
    public static GlobalApp getapp(){
        
        return app;
    }
}
