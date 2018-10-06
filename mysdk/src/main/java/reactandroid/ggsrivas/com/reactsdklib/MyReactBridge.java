package reactandroid.ggsrivas.com.reactsdklib;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.lang.ref.WeakReference;

class MyReactBridge extends ReactContextBaseJavaModule {

    private final static String TAG = "MyReactBridge";
    private static WeakReference<ReactApplicationContext> reactApplicationContextWeakReference;

    public MyReactBridge(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContextWeakReference = new WeakReference<ReactApplicationContext>(reactApplicationContext);
    }

    /**
     * sendEvent from Java to React
     * */
    private static void sendEvent(String eventName,
                                  @Nullable WritableMap params) {
        if(reactApplicationContextWeakReference == null || reactApplicationContextWeakReference.get() == null) {
            Log.d(TAG, "Sending event to react failed because reactContext is null. eventName: " + eventName);
            return;
        }
        reactApplicationContextWeakReference.get()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    /**
     *  Triggers an event to inform React Native to enable back button
     * */
    public static void sendEnableBackButton() {
        String eventName = "myAppEnableBackButton";
        if(reactApplicationContextWeakReference == null || reactApplicationContextWeakReference.get() == null) {
            Log.d(TAG, "ReactContext is null. aborting" + eventName + "event to RN.");
            return;
        }
        try {
            sendEvent(eventName, null);
        }
        catch (Exception e) {
            Log.e(TAG, "sendEnableBackButton to RN failed with error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void handleBackButton() {
        MySDK.getInstance().dismissReactActivity();
    }

    @Override
    public String getName() {
        return "MyReactBridge";
    }
}
