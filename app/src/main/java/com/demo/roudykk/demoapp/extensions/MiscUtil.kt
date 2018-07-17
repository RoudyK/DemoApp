package com.demo.roudykk.demoapp.extensions

import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


fun Bundle.toJSONObject(): JSONObject {
    val json = JSONObject()
    val keys = keySet()
    for (key in keys) {
        try {
            json.put(key, wrap(get(key)))
        } catch (e: JSONException) {
            Log.d("Bundle Extension", "Failed to add " + key + " with value: " + get(key))
        }
    }
    return json
}

fun wrap(o: Any?): Any? {
    if (o == null) {
        return JSONObject.NULL
    }
    if (o is JSONArray || o is JSONObject) {
        return o
    }
    if (o == JSONObject.NULL) {
        return o
    }
    try {
        if (o is Collection<*>) {
            return JSONArray(o as Collection<*>?)
        }
        if (o is Map<*, *>) {
            return JSONObject(o as Map<*, *>?)
        }
        if (o is Boolean ||
                o is Byte ||
                o is Char ||
                o is Double ||
                o is Float ||
                o is Int ||
                o is Long ||
                o is Short ||
                o is String) {
            return o
        }
        if (o.javaClass.getPackage().name.startsWith("java.")) {
            return o.toString()
        }
    } catch (ignored: Exception) {
    }

    return null
}