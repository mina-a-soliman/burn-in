package com.burnsubtitle.domain.util

object MediaFileNames {
    val VIDEO_EXTENSIONS = setOf(
        "mp4", "mkv", "webm", "mov", "m4v", "avi", "3gp", "3gpp",
        "ts", "m2ts", "mpeg", "mpg", "flv", "wmv",
    )

    fun extension(name: String): String {
        return name.substringAfterLast('.', missingDelimiterValue = "").lowercase()
    }

    fun withExtension(baseName: String, extension: String): String {
        val clean = extension.trimStart('.').lowercase()
        return if (clean.isEmpty()) baseName else "$baseName.$clean"
    }

    fun isVideoFile(displayName: String, mimeType: String?): Boolean {
        val mime = mimeType?.lowercase()?.substringBefore(';')?.trim().orEmpty()
        if (mime.startsWith("video/")) return true
        if (mime.startsWith("image/") || mime.startsWith("audio/") || mime.startsWith("text/")) {
            return false
        }
        return extension(displayName) in VIDEO_EXTENSIONS
    }
}
