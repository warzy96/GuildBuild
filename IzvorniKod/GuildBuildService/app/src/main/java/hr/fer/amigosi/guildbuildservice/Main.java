package hr.fer.amigosi.guildbuildservice;

import android.app.Activity;

/**
 * Created by ivan_varga on 29/12/2017.
 */

public class Main extends Activity {
    public static ApplicationDatabase adb;
    @Override
    protected void onStart() {
        super.onStart();
        adb = ApplicationDatabase.getApplicationDatabase(this.getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adb.close();
    }
}
