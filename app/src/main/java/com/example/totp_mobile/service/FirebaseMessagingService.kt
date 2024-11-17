package com.example.totp_mobile.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.totp_mobile.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FirebaseMessaging", "Mensagem recebida: ${remoteMessage.data}")

        val userId = remoteMessage.data["userID"]
        val sessionId = remoteMessage.data["sessionID"]
        val ipAddress = remoteMessage.data["ipAddress"]

        if (userId != null && sessionId != null && ipAddress != null) {
            showLoginValidationNotification(userId, sessionId, ipAddress)
        }
    }

    private fun showLoginValidationNotification(userId: String, sessionId: String, ipAddress: String) {
        val channelId = "login_validation_channel"
        val notificationId = 1

        // Cria o canal de notificação (necessário para Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Login Validation",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Validação de login"
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Intent para ação "Sim"
        val acceptIntent = Intent(this, NotificationActionReceiver::class.java).apply {
            action = "ACCEPT_LOGIN"
            putExtra("userID", userId)
            putExtra("sessionID", sessionId)
            putExtra("ipAddress", ipAddress)
        }
        val acceptPendingIntent = PendingIntent.getBroadcast(this, 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Intent para ação "Não"
        val rejectIntent = Intent(this, NotificationActionReceiver::class.java).apply {
            action = "REJECT_LOGIN"
            putExtra("userID", userId)
            putExtra("sessionID", sessionId)
            putExtra("ipAddress", ipAddress)
        }
        val rejectPendingIntent = PendingIntent.getBroadcast(this, 1, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Configura a notificação
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Validação de Login")
            .setContentText("IP: $ipAddress\nVocê deseja autorizar o login?")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark, "Sim", acceptPendingIntent)
            .addAction(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_focused, "Não", rejectPendingIntent)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(notificationId, notification)
    }
}
