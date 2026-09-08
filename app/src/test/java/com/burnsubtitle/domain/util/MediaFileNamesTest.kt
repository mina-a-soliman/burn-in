package com.burnsubtitle.domain.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MediaFileNamesTest {
    @Test
    fun extensionIsLowercase() {
        assertEquals("mkv", MediaFileNames.extension("Holiday.MKV"))
        assertEquals("", MediaFileNames.extension("no-extension"))
    }

    @Test
    fun detectsVideoFromMimeOrExtension() {
        assertTrue(MediaFileNames.isVideoFile("clip.bin", "video/mp4"))
        assertTrue(MediaFileNames.isVideoFile("clip.mkv", "application/octet-stream"))
        assertTrue(MediaFileNames.isVideoFile("clip.mp4", null))
        assertFalse(MediaFileNames.isVideoFile("photo.jpg", "image/jpeg"))
        assertFalse(MediaFileNames.isVideoFile("song.mp3", "audio/mpeg"))
        assertFalse(MediaFileNames.isVideoFile("subs.srt", "text/plain"))
    }
}
