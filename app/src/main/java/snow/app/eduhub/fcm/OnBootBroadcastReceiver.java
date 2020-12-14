package snow.app.eduhub.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent("com.demo.FirebaseMessagingReceiveService");
        i.setClass(context, MyFirebaseMessagingService.class);
        context.startService(i);
    }
}