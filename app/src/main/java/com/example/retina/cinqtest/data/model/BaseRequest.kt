package com.example.retina.cinqtest.data.model

import org.json.JSONObject

/**
 * Created by squarebits on 30/05/18.
 */

class BaseRequest {
    internal var message: String? = ""


    companion object {
         fun formatJson(error:String):String{
            var message = ""
            val jsnobject = JSONObject(error)
            val jsonArray = jsnobject.getJSONArray("errors")
            for (i in 0.until(jsonArray.length() - 1)) {
                message = jsonArray.get(i).toString()
            }
            return message
        }
    }

}
