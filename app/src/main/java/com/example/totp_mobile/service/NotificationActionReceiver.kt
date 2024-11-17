package com.example.totp_mobile.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return

        val action = intent.action
        val userId = intent.getStringExtra("userID")
        val sessionId = intent.getStringExtra("sessionID")
        val ipAddress = intent.getStringExtra("ipAddress")

        when (action) {
            "ACCEPT_LOGIN" -> {
                Log.d("NotificationAction", "Login autorizado pelo usuário")
                sendResponseToBackend(context, userId, sessionId, ipAddress, true)
            }
            "REJECT_LOGIN" -> {
                Log.d("NotificationAction", "Login rejeitado pelo usuário")
                sendResponseToBackend(context, userId, sessionId, ipAddress, false)
            }
            else -> Log.d("NotificationAction", "Ação desconhecida: $action")
        }
    }

    private fun sendResponseToBackend(
        context: Context?,
        userId: String?,
        sessionId: String?,
        ipAddress: String?,
        isAuthorized: Boolean
    ) {
        // Substitua por uma lógica real de comunicação com o backend
        Log.d(
            "NotificationAction",
            "Enviando resposta ao backend: userId=$userId, sessionId=$sessionId, ipAddress=$ipAddress, autorizado=$isAuthorized"
        )

        // Aqui você pode implementar uma chamada HTTP ou integração com outro serviço
    }
}
