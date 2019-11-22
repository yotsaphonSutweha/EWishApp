package nci.yo.ewishapp;

import android.app.Application;
import android.content.Context;

import nci.yo.ewishapp.Helper.LocalHelper;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalHelper.onAttach(base, "en"));
    }
}
