package com.hadestech.capacitor.get.apps.reading.sms;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.util.JSONUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@CapacitorPlugin(name = "Example")
public class ExamplePlugin extends Plugin {

    private Example implementation = new Example();

    @PluginMethod
    public void echo(PluginCall call) {
        // Get the Context object
        Context context = getContext();

        // Initialize the PackageManager
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        // Iterate through the list and print out the package names
        List<Object> appsWithSmsPermission = new ArrayList<>();
        for (ApplicationInfo packageInfo : packages) {
            // Check if the app has the SMS permission
            if (pm.checkPermission(Manifest.permission.READ_SMS, packageInfo.packageName) == PackageManager.PERMISSION_GRANTED) {
                // If the app has the permission, add it to the list
                if (packageInfo.sourceDir.startsWith("/data/app/")) {
                    appsWithSmsPermission.add(pm.getApplicationLabel(packageInfo));
                }
            }
        }

        //        // Print out the list of apps with the SMS permission
        //        for (Object app : appsWithSmsPermission) {
        //            Log.println("app", app);
        //        }

        //        String value = call.getString("value");
        JSObject retObj = new JSObject();
        JSArray retList = new JSArray();
        for (Object appObj : appsWithSmsPermission) {
            retList.put(appObj);
        }
        //        ret2.put(appsWithSmsPermission);
        //        ret.put("value", implementation.echo(value));
        retObj.put("value", retList);
        call.resolve(retObj);
    }
}
