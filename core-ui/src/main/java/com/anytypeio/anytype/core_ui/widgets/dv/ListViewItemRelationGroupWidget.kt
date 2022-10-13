package com.anytypeio.anytype.core_ui.widgets.dv

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import com.anytypeio.anytype.core_models.ThemeColor
import com.anytypeio.anytype.core_ui.R
import com.anytypeio.anytype.core_ui.extensions.dark
import com.anytypeio.anytype.core_ui.widgets.ListViewRelationObjectValueView
import com.anytypeio.anytype.core_ui.widgets.ListViewRelationTagValueView
import com.anytypeio.anytype.core_utils.ext.dimen
import com.anytypeio.anytype.core_utils.ext.formatTimestamp
import com.anytypeio.anytype.presentation.objects.ObjectIcon
import com.anytypeio.anytype.presentation.relations.model.DefaultObjectRelationValueView
import com.anytypeio.anytype.presentation.sets.model.ObjectView

class ListViewItemRelationGroupWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val defaultTextSize: Float = context.dimen(R.dimen.sp_12)
    private val dividerSize: Int = context.dimen(R.dimen.dp_2).toInt()

    fun set(relations: List<DefaultObjectRelationValueView>) {
        clear()

        val flow = Flow(context).apply {
            id = View.generateViewId()
            setWrapMode(Flow.WRAP_CHAIN)
            setHorizontalStyle(Flow.CHAIN_PACKED)
            setHorizontalBias(0f)
            setHorizontalAlign(Flow.HORIZONTAL_ALIGN_START)
            setHorizontalGap(15)
            setVerticalGap(15)
        }

        addView(flow)

        val ids = mutableListOf<Int>()

        relations.forEachIndexed { index, relation ->
            var isAdded = false
            when (relation) {
                is DefaultObjectRelationValueView.Checkbox -> {
                    val view = View(context).apply {
                        id = generateViewId()
                        val size = context.dimen(R.dimen.dp_16).toInt()
                        layoutParams = LayoutParams(size, size)
                        setBackgroundResource(R.drawable.ic_relation_checkbox_selector)
                        isSelected = relation.isChecked
                    }
                    addView(view)
                    ids.add(view.id)
                    isAdded = true
                }
                is DefaultObjectRelationValueView.Date -> {
                    val value = relation.timeInMillis?.formatTimestamp(
                        isMillis = true,
                        format = relation.dateFormat
                    )
                    if (value != null) {
                        val view = createView(value = value)
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.File -> {
                    if (relation.files.isNotEmpty()) {
                        val file = relation.files[0]
                        val view = ListViewRelationObjectValueView(context).apply {
                            id = generateViewId()
                            setup(
                                name = "${file.name}.${file.ext}",
                                icon = ObjectIcon.None,
                                size = relation.files.size
                            )
                        }
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Number -> {
                    val value = relation.number
                    if (!value.isNullOrBlank()) {
                        val view = createView(value = value)
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Object -> {
                    if (relation.objects.isNotEmpty()) {
                        val view = ListViewRelationObjectValueView(context).apply {
                            id = generateViewId()
                            when (val obj = relation.objects[0]) {
                                is ObjectView.Default -> {
                                    setup(
                                        name = obj.name,
                                        icon = obj.icon,
                                        size = relation.objects.size
                                    )
                                }
                                is ObjectView.Deleted -> {
                                    setupAsNonExistent(size = relation.objects.size)
                                }
                            }
                        }
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Phone -> {
                    val value = relation.phone
                    if (!value.isNullOrBlank()) {
                        val view = createView(value = value)
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Status -> {
                    val defaultTextColor = resources.getColor(R.color.text_primary, null)
                    if (relation.status.isNotEmpty()) {
                        val status = relation.status[0]
                        val color = ThemeColor.values().find { v -> v.code == status.color }
                        val view = TextView(context).apply {
                            id = generateViewId()
                            text = status.status
                            isSingleLine = true
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                            setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSize)
                            if (color != null) {
                                setTextColor(resources.dark(color, defaultTextColor))
                            } else {
                                setTextColor(defaultTextColor)
                            }
                        }
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Tag -> {
                    if (relation.tags.isNotEmpty()) {
                        val view = ListViewRelationTagValueView(context).apply {
                            id = generateViewId()
                            val tag = relation.tags[0]
                            setup(
                                name = tag.tag,
                                tagColor = tag.color,
                                size = relation.tags.size
                            )
                        }
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Text -> {
                    val value = relation.text
                    if (!value.isNullOrBlank()) {
                        val view = createView(value = value)
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Email -> {
                    val value = relation.email
                    if (!value.isNullOrBlank()) {
                        val view = createView(value = value)
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
                is DefaultObjectRelationValueView.Url -> {
                    val value = relation.url
                    if (!value.isNullOrBlank()) {
                        val view = createView(value = value)
                        addView(view)
                        ids.add(view.id)
                        isAdded = true
                    }
                }
            }

            if (index != relations.lastIndex && isAdded) {
                val div = View(context).apply {
                    id = View.generateViewId()
                    layoutParams = LayoutParams(dividerSize, dividerSize)
                    setBackgroundResource(R.drawable.divider_dv_viewer_list_item_relations)
                }
                addView(div)
                ids.add(div.id)
            }
        }

        flow.referencedIds = ids.toIntArray()
    }

    private fun createView(value: String): TextView {
        return TextView(context).apply {
            id = generateViewId()
            text = value
            isSingleLine = true
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSize)
        }
    }

    fun clear() {
        removeAllViews()
    }
}