package com.anytypeio.anytype.presentation.page.editor

import android.os.Parcelable
import android.text.Spannable
import kotlinx.android.parcel.Parcelize

/**
 * Classes implementing this interface should support markup rendering.
 */
interface Markup {

    /**
     * A text body that this markup should be applied to.
     */
    val body: String

    /**
     * List of marks associated with the text body.
     */
    var marks: List<Mark>

    /**
     * @property from caracter index where this markup starts (inclusive)
     * @property to caracter index where this markup ends (inclusive)
     * @property type markup's type
     */
    @Parcelize
    data class Mark(
        val from: Int,
        val to: Int,
        val type: Type,
        val param: String? = null,
        val extras: Map<String, String?> = emptyMap()
    ) : Parcelable {

        val image: String? by extras
        val emoji: String? by extras

        fun color(): Int? = ThemeColor.values().find { color -> color.title == param }?.text
        fun background(): Int? =
            ThemeColor.values().find { color -> color.title == param }?.background

    }

    /**
     * Markup types.
     */
    enum class Type {
        ITALIC,
        BOLD,
        STRIKETHROUGH,
        TEXT_COLOR,
        BACKGROUND_COLOR,
        LINK,
        KEYBOARD,
        MENTION
    }

    companion object {
        const val DEFAULT_SPANNABLE_FLAG = Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        const val MENTION_SPANNABLE_FLAG = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        const val SPAN_MONOSPACE = "monospace"
    }
}