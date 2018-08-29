package com.example.retina.cinqtest.extras

import android.text.Editable
import android.text.Html.TagHandler
import android.text.Spannable
import android.text.style.StrikethroughSpan
import org.xml.sax.XMLReader


/**
 * Created by squarebits on 13/06/18.
 */
open class MyHtmlTagHandler : TagHandler {

    override fun handleTag(opening: Boolean, tag: String, output: Editable,
                           xmlReader: XMLReader) {
        if (tag.equals("strike", ignoreCase = true) || tag == "s") {
            processStrike(opening, output)
        }
    }

    private fun processStrike(opening: Boolean, output: Editable) {
        val len = output.length
        if (opening) {
            output.setSpan(StrikethroughSpan(), len, len, Spannable.SPAN_MARK_MARK)
        } else {
            val obj = getLast(output, StrikethroughSpan::class.java)
            val where = output.getSpanStart(obj)

            output.removeSpan(obj)

            if (where != len) {
                output.setSpan(StrikethroughSpan(), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private fun getLast(text: Editable, kind: Class<*>): Any? {
        val objs = text.getSpans(0, text.length, kind)

        if (objs.isEmpty()) {
            return null
        } else {
            for (i in objs.size downTo 1) {
                if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1]
                }
            }
            return null
        }
    }


}