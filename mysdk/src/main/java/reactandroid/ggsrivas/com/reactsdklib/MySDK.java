package reactandroid.ggsrivas.com.reactsdklib;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class MySDK {

    private static MySDK instance;

    private MySDK(){}

    public static MySDK getInstance() {
        if(instance == null) {
            instance = new MySDK();
        }
        return  instance;
    }

    public void startReactApp(Activity activity) {
        if(activity == null) {
            Log.e("MySDK", "activity can't be null. returning.");
            return;
        }
        Intent intent = new Intent(activity, MySDKActivity.class);
        activity.startActivity(intent);
    }
}
