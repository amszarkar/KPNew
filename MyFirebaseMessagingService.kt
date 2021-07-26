package velociter.kumar.property

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private  val TAG = "FireBaseMessagingService"
    var Notification_CHANNEL_ID = "velociter.kumar.property"
    var NOTIFICATION_ID = 100
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.e("message","Message RECEIVED...")


        if (remoteMessage.data.size>0){
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            val click_action = remoteMessage.data["click_action"]
            showNotification(applicationContext,title,body,click_action)
        }else{
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            val click_action =remoteMessage.notification!!.clickAction
            showNotification(applicationContext,title,body,click_action)
        }
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)

        Log.e("token","New Token")


    }
    fun showNotification(
        context: Context,
        title: String?,
        message:String?,
        click_action : String?
    ){

        val ii:Intent
        if (click_action.equals("com.az.firebasemessaging.fcmwithserver_TARGET_NOTIFICATION")){
            ii= Intent(context,OfferActivity::class.java)
            ii.data=(Uri.parse("custom://" + System.currentTimeMillis()))
            ii.action = "actionstring" + System.currentTimeMillis()
            ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

        }else{
            ii= Intent(context,MainActivity::class.java)
            ii.data=(Uri.parse("custom://" + System.currentTimeMillis()))
            ii.action = "actionstring" + System.currentTimeMillis()
            ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }



            val  pi = PendingIntent.getActivity(context,0,ii,PendingIntent.FLAG_UPDATE_CURRENT)
            val notification:Notification
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                notification = NotificationCompat.Builder(context,Notification_CHANNEL_ID)
                    .setOngoing(true)
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setWhen(System.currentTimeMillis())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentTitle(title).build()
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

               val notificationChannel = NotificationChannel(Notification_CHANNEL_ID,
                   title,NotificationManager.IMPORTANCE_DEFAULT)

                notificationManager.createNotificationChannel(notificationChannel)
                notificationManager.notify(NOTIFICATION_ID,notification)
            }else{
                notification = NotificationCompat.Builder(context,Notification_CHANNEL_ID)
                    .setOngoing(true)
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentTitle(title).build()

              val  notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.notify(NOTIFICATION_ID,notification)

            }
    }
}