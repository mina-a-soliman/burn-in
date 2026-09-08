package com.burnsubtitle.domain.ass

import com.burnsubtitle.domain.model.SubtitlePosition
import org.junit.Assert.assertEquals
import org.junit.Test

class AssConversionTest {
    @Test
    fun colorRoundTripsArgbToAss() {
        val white = 0xFFFFFFFFL
        val boxed = 0x99000000L
        assertEquals("&H00FFFFFF", AssColor.fromArgb(white))
        assertEquals(white, AssColor.toArgb(AssColor.fromArgb(white)))
        assertEquals(boxed, AssColor.toArgb(AssColor.fromArgb(boxed)))
    }

    @Test
    fun fontSizeScalesFrom1080pReference() {
        assertEquals(42, AssMetrics.fontSize(42, 1080))
        assertEquals(28, AssMetrics.fontSize(42, 720))
        assertEquals(84, AssMetrics.fontSize(42, 2160))
    }

    @Test
    fun positionMapsToNumpadAlignment() {
        assertEquals(2, AssAlignment.fromPosition(SubtitlePosition.BOTTOM_CENTER))
        assertEquals(7, AssAlignment.fromPosition(SubtitlePosition.TOP_LEFT))
        val margins = AssAlignment.margins(SubtitlePosition.BOTTOM_CENTER, 1920, 1080, 10)
        assertEquals(192, margins.left)
        assertEquals(108, margins.vertical)
    }
}
