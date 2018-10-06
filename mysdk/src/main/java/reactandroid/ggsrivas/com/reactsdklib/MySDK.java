package reactandroid.ggsrivas.com.reactsdklib;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class MySDK {

    private static MySDK instance;
    private WeakReference<Activity> myActivityReference; // do not leak memory

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

        // Send Events to React Native after 5 seconds of starting React app.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Send event to react native to enabled back button.
                        MyReactBridge.sendEnableBackButton();
                    }
                },
                5000);
    }

    void dismissReactActivity() {
        if(this.myActivityReference.get() != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    ((MySDKActivity)myActivityReference.get()).onBackPressed();
                }
            });
        }
    }

    void setMyActivityWeakReference(Activity myActivityReference) {
        this.myActivityReference = new WeakReference<Activity>(myActivityReference);
    }
}
