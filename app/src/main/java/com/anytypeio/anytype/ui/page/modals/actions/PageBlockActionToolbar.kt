package com.anytypeio.anytype.ui.page.modals.actions

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.anytypeio.anytype.R
import com.anytypeio.anytype.core_utils.ext.visible
import com.anytypeio.anytype.emojifier.Emojifier
import com.anytypeio.anytype.presentation.page.editor.model.BlockView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import timber.log.Timber

class PageBlockActionToolbar : BlockActionToolbar() {

    lateinit var block: BlockView.Page
    lateinit var emoji: ImageView
    lateinit var image: ImageView
    lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        block = arguments?.getParcelable(ARG_BLOCK)!!
    }

    override fun blockLayout() = R.layout.item_block_page_preview
    override fun getBlock(): BlockView = block

    override fun initUi(view: View, colorView: ImageView?, backgroundView: ImageView?) {

        image = view.findViewById(R.id.linkImage)
        emoji = view.findViewById(R.id.linkEmoji)
        title = view.findViewById(R.id.pageTitle)

        title.text = if (block.text.isNullOrEmpty()) getString(R.string.untitled) else block.text

        when {
            block.emoji != null -> {
                image.setImageDrawable(null)
                try {
                    Glide
                        .with(emoji)
                        .load(Emojifier.uri(block.emoji!!))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(emoji)
                } catch (e: Throwable) {
                    Timber.e(e, "Error while setting emoji icon for: ${block.emoji}")
                }
            }
            block.image != null -> {
                image.visible()
                Glide
                    .with(image)
                    .load(block.image)
                    .centerInside()
                    .circleCrop()
                    .into(image)
            }
            block.isEmpty -> {
                image.setImageResource(com.anytypeio.anytype.core_ui.R.drawable.ic_block_empty_page)
            }
            else -> {
                image.setImageResource(com.anytypeio.anytype.core_ui.R.drawable.ic_block_page_without_emoji)
            }
        }

        setConstraints()
    }
}