package com.data.network.model

import android.util.Log
import com.data.network.NetworkModuleDI.callCloudFunction
import com.parse.ParseObject

class ParseService {

    fun fetchHistoricalEvents(
        maxRetries: Int = 5, // Número máximo de intentos
        callback: (List<EventoHistorico>?, Exception?) -> Unit
    ) {
        val parametros = hashMapOf<String, Any>()

        // Función interna para manejar los reintentos
        fun intentarLlamada(reintentoActual: Int) {
            callCloudFunction<HashMap<String, Any>>("hello", parametros) { result, error ->
                Log.d("ResultService", result.toString())
                Log.d("ErrorService", error.toString())
                if (error == null && result != null) {
                    try {
                        // Obtén la lista de ParseObject desde la clave "data"
                        val data = result["data"] as? List<ParseObject>
                        if (data != null) {
                            // Mapea ParseObject a EventoHistorico
                            val eventos = data.map { parseObject ->
                                EventoHistorico(
                                    date = "Date: ${parseObject.getString("date") ?: "Unknown"}",
                                    description = parseObject.getString("description") ?: "No description",
                                    category1 = parseObject.getString("category1") ?: "Unknown",
                                    category2 = parseObject.getString("category2") ?: "Unknown"
                                )
                            }
                            callback(eventos, null) // Llamada exitosa
                        } else {
                            callback(emptyList(), null) // Lista vacía si no hay datos
                        }
                    } catch (e: Exception) {
                        callback(null, e) // Maneja errores en el mapeo
                    }
                } else {
                    // Si falla y no hemos alcanzado el número máximo de intentos, intenta de nuevo
                    if (reintentoActual < maxRetries) {
                        Log.w(
                            "RetryService",
                            "Error en intento ${reintentoActual + 1} de $maxRetries. Reintentando..."
                        )
                        intentarLlamada(reintentoActual + 1)
                    } else {
                        // Si se alcanzó el máximo de intentos, envía el error al callback
                        callback(
                            null,
                            error ?: Exception("Error desconocido después de $maxRetries intentos")
                        )
                    }
                }
            }
        }

        // Comienza con el primer intento
        intentarLlamada(0)
    }
}
