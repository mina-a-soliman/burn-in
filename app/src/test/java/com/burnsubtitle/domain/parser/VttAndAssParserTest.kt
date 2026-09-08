package com.burnsubtitle.domain.parser

import com.burnsubtitle.domain.model.SubtitleFormat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VttParserTest {
    @Test
    fun parsesMixedArabicAndEnglish() {
        val document = VttParser().parse(
            """
            WEBVTT

            00:00:01.000 --> 00:00:02.500
            Hello <b>مرحبا</b>
            """.trimIndent(),
        )
        assertEquals(SubtitleFormat.VTT, document.sourceFormat)
        assertEquals("Hello مرحبا", document.cues.single().text)
    }
}

class AssParserTest {
    @Test
    fun parsesDialogueWithArabic() {
        val document = AssParser().parse(
            """
            [Script Info]
            Title: Test

            [Events]
            Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
            Dialogue: 0,0:00:01.00,0:00:02.00,Default,,0,0,0,,مرحبا{\b1} world
            """.trimIndent(),
        )
        assertEquals(1, document.cues.size)
        assertTrue(document.cues.single().text.contains("مرحبا"))
        assertTrue(document.cues.single().text.contains("world"))
        assertEquals(1000L, document.cues.single().startMs)
    }
}
