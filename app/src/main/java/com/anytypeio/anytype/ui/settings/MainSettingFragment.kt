package com.anytypeio.anytype.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import com.anytypeio.anytype.R
import com.anytypeio.anytype.core_utils.ui.BaseBottomSheetComposeFragment
import com.anytypeio.anytype.ui_settings.main.MainSettingScreen

class MainSettingFragment : BaseBottomSheetComposeFragment() {

    private val onAccountAndDataClicked = {
        findNavController().navigate(R.id.actionOpenAccountAndDataScreen)
    }

    private val onAboutAppClicked = {
        findNavController().navigate(R.id.actionOpenAboutAppScreen)
    }

    private val onPersonalizationClicked = {
        findNavController().navigate(R.id.actionOpenPersonalizationScreen)
    }

    private val onAppearanceClicked = {
        findNavController().navigate(R.id.actionOpenAppearanceScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme(typography = typography) {
                    MainSettingScreen(
                        onAccountAndDataClicked = onAccountAndDataClicked,
                        onAboutAppClicked = onAboutAppClicked,
                        onAppearanceClicked = onAppearanceClicked,
                        onPersonalizationClicked = onPersonalizationClicked
                    )
                }
            }
        }
    }

    override fun injectDependencies() {}
    override fun releaseDependencies() {}
}