package com.data.network.model

import android.util.Log
import com.data.network.NetworkModuleDI
import com.data.network.NetworkModuleDI.callCloudFunction
import com.parse.ParseObject

class ParseService {

    fun fetchHistoricalEvents(callback: (List<EventoHistorico>?, Exception?) -> Unit) {
        val parametros = hashMapOf<String, Any>()

        // Llama a la Cloud Function
        callCloudFunction<HashMap<String, Any>>(
            "hello",
            parametros
        ) { result, error ->
            Log.d("ResultService",result.toString())
            Log.d("ErrorService",error.toString())
            if (error == null && result != null) {
                try {
                    // Obtén la lista de ParseObject desde la clave "data"
                    val data = result["data"] as? List<ParseObject>
                    if (data != null) {
                        // Mapea ParseObject a HistoricalEvent
                        val events = data.map { parseObject ->
                            EventoHistorico(
                                date = ("Date:" + parseObject.getString("date")) ?: "Unknown",
                                description = parseObject.getString("description")
                                    ?: "No description",
                                category1 = parseObject.getString("category1") ?: "Unknown",
                                category2 = parseObject.getString("category2") ?: "Unknown"
                            )
                        }
                        callback(events, null)
                    } else {
                        callback(emptyList(), null) // Lista vacía si no hay datos
                    }
                } catch (e: Exception) {
                    callback(null, e) // Maneja errores durante el mapeo
                }
            } else {
                callback(null, error) // Maneja errores de la Cloud Function
            }
        }
    }

}

