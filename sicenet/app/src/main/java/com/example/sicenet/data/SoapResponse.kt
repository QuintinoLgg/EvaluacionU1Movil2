package com.example.sicenet.data

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

data class SoapResponse(
    val resultado: String
)

fun parsearPerfilAcademico(responseBody: String?): String {
    val xmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
    xmlPullParser.setInput(StringReader(responseBody))

    var eventType = xmlPullParser.eventType
    var resultado = ""

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                val tagName = xmlPullParser.name
                if (tagName == "getAlumnoAcademicoWithLineamientoResult") {
                    resultado = xmlPullParser.nextText()
                }
            }
        }
        eventType = xmlPullParser.next()
    }

    return resultado
}
