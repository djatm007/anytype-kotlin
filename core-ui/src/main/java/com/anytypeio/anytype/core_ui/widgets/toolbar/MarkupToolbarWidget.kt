package com.anytypeio.anytype.core_ui.widgets.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.anytypeio.anytype.core_ui.R
import com.anytypeio.anytype.core_ui.reactive.clicks
import com.anytypeio.anytype.presentation.page.editor.Markup
import kotlinx.android.synthetic.main.widget_markup_toolbar.view.*
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MarkupToolbarWidget : ConstraintLayout {

    constructor(
        context: Context
    ) : this(context, null)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        inflate()
    }

    private fun inflate() {
        LayoutInflater.from(context).inflate(R.layout.widget_markup_toolbar, this)
    }

    fun markupClicks() = flowOf(bold(), italic(), strike(), link(), code()).flattenMerge()
    private fun bold() = bold.clicks().map { Markup.Type.BOLD }
    private fun italic() = italic.clicks().map { Markup.Type.ITALIC }
    private fun strike() = strike.clicks().map { Markup.Type.STRIKETHROUGH }
    private fun link() = link.clicks().map { Markup.Type.LINK }
    private fun code() = code.clicks().map { Markup.Type.KEYBOARD }

    fun colorClicks() = color.clicks()
    fun hideKeyboardClicks() = keyboard.clicks()

    sealed class Action

    sealed class Event {
        data class OnMarkupClicked(val type: Markup.Type) : Event()
        object OnHideKeyboardClicked : Event()
        object OnColorClicked : Event()
    }
}