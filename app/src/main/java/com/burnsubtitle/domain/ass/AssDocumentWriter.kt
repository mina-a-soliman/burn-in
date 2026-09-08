package com.burnsubtitle.domain.ass

import com.burnsubtitle.domain.model.SubtitleCue
import com.burnsubtitle.domain.model.SubtitleDocument
import com.burnsubtitle.domain.model.SubtitleStyle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssDocumentWriter @Inject constructor(
    private val styleGenerator: AssStyleGenerator,
) {
    fun write(
        document: SubtitleDocument,
        style: SubtitleStyle,
        playResX: Int,
        playResY: Int,
    ): String {
        val width = playResX.coerceAtLeast(1)
        val height = playResY.coerceAtLeast(1)
        val styleLine = styleGenerator.toStyleLine(style, width, height)
        val dialogues = document.cues.joinToString("\n") { cue -> dialogueLine(cue) }
        val body = """
            [Script Info]
            Title: Burn Subtitle
            ScriptType: v4.00+
            WrapStyle: 0
            ScaledBorderAndShadow: yes
            Kerning: yes
            YCbCr Matrix: TV.709
            PlayResX: $width
            PlayResY: $height
            LayoutResX: $width
            LayoutResY: $height

            [V4+ Styles]
            Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding
            $styleLine

            [Events]
            Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
            $dialogues
        """.trimIndent()
        return "\uFEFF$body\n"
    }

    private fun dialogueLine(cue: SubtitleCue): String {
        return "Dialogue: 0," +
            AssTextEncoder.formatTime(cue.startMs) + "," +
            AssTextEncoder.formatTime(cue.endMs) +
            ",Default,,0,0,0,," +
            AssTextEncoder.encode(cue.text)
    }
}
