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

    @Test
    fun parsesVttWithWhitespaceBlankLines() {
        val raw = "WEBVTT\n\n00:00:01.000 --> 00:00:02.000\nFirst\n   \n00:00:03.000 --> 00:00:04.000\nSecond\n"
        val document = VttParser().parse(raw)
        assertEquals(2, document.cues.size)
        assertEquals("First", document.cues[0].text)
        assertEquals("Second", document.cues[1].text)
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

    @Test
    fun convertsAssHardSpaceToRegularSpace() {
        val document = AssParser().parse(
            """
            [Script Info]
            Title: Test

            [Events]
            Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
            Dialogue: 0,0:00:01.00,0:00:02.00,Default,,0,0,0,,Word1\hWord2
            """.trimIndent(),
        )
        assertEquals("Word1 Word2", document.cues.single().text)
    }
}
