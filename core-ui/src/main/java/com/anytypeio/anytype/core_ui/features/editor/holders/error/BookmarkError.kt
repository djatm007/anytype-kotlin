package com.anytypeio.anytype.core_ui.features.editor.holders.error

import android.view.View
import android.widget.TextView
import com.anytypeio.anytype.core_ui.R
import com.anytypeio.anytype.core_utils.ext.dimen
import com.anytypeio.anytype.core_utils.ext.indentize
import com.anytypeio.anytype.presentation.page.editor.listener.ListenerType
import com.anytypeio.anytype.presentation.page.editor.model.BlockView
import kotlinx.android.synthetic.main.item_block_bookmark_error.view.*

class BookmarkError(view: View) : MediaError(view) {

    override val root: View = itemView.bookmarkErrorRoot
    private val urlView: TextView = itemView.errorBookmarkUrl

    fun setUrl(url: String) {
        urlView.text = url
    }

    override fun errorClick(item: BlockView.Error, clicked: (ListenerType) -> Unit) {
        if (item is BlockView.Error.Bookmark) {
            clicked(ListenerType.Bookmark.Error(item))
        }
    }

    override fun indentize(item: BlockView.Indentable) {
        root.indentize(
            indent = item.indent,
            defIndent = dimen(R.dimen.indent),
            margin = dimen(R.dimen.bookmark_default_margin_start)
        )
    }

    override fun select(isSelected: Boolean) {
        root.isSelected = isSelected
    }
}