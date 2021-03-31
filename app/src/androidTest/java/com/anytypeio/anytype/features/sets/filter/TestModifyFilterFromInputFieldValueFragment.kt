package com.anytypeio.anytype.features.sets.filter

import com.anytypeio.anytype.presentation.sets.filter.FilterViewModel
import com.anytypeio.anytype.ui.sets.modals.filter.ModifyFilterFromInputFieldValueFragment

class TestModifyFilterFromInputFieldValueFragment : ModifyFilterFromInputFieldValueFragment() {

    init {
        factory = testVmFactory
    }

    override fun injectDependencies() {}
    override fun releaseDependencies() {}

    companion object {
        lateinit var testVmFactory: FilterViewModel.Factory
    }
}