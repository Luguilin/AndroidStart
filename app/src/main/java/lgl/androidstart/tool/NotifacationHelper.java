package lgl.androidstart.tool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.HashMap;
import java.util.Map;

import lgl.androidstart.MainActivity;
import lgl.androidstart.R;

/**
 * Created by LGL on 2016/10/17.
 */

public class NotifacationHelper {
    NotificationManager mNotificationManager=null;
    Map<Integer,Notification> mNotifications;
    Context mContext;
    public NotifacationHelper(Context context){
        mNotificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifications=new HashMap<>();
        this.mContext=context;
    }
    public void showNotification(String tickerText){
        Notification notification=new Notification();
        RemoteViews remoteViews=new RemoteViews(mContext.getPackageName(),R.layout.notification_layout);
        notification.tickerText=tickerText;//设置可滚动的文字
        notification.contentView=remoteViews;
        notification.when=System.currentTimeMillis();
        notification.icon= R.mipmap.ic_launcher;//图标
        notification.flags=Notification.FLAG_AUTO_CANCEL;//特性
        Intent intent=new Intent(mContext, MainActivity.class);
//        @param context The Context in which this PendingIntent should start
//                * the activity.
//                * @param requestCode Private request code for the sender
//        * @param intent Intent of the activity to be launched.
//        * @param flags May be {@link #FLAG_ONE_SHOT}, {@link #FLAG_NO_CREATE},
//        * {@link #FLAG_CANCEL_CURRENT}, {@link #FLAG_UPDATE_CURRENT},
//        * or any of the flags as supported by
        PendingIntent pendingIntent=PendingIntent.getActivity(mContext, 0,intent,0);
        mNotificationManager.notify(99,notification);
        mNotifications.put(99,notification);
        //设置点击通知栏的操作
//        notification.contentIntent

    }
}
