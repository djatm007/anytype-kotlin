package com.anytypeio.anytype.core_ui.features.dataview.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anytypeio.anytype.presentation.sets.model.CellView
import kotlinx.android.synthetic.main.item_viewer_grid_cell_description.view.*

class DVGridCellDescriptionHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(cell: CellView.Description) {
        itemView.tvText.text = cell.text
    }
}