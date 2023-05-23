package com.anytypeio.anytype.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anytypeio.anytype.core_utils.ui.BaseComposeFragment
import com.anytypeio.anytype.di.common.componentManager
import com.anytypeio.anytype.ext.daggerViewModel
import com.anytypeio.anytype.ui.onboarding.screens.AuthScreenWrapper
import com.anytypeio.anytype.ui.onboarding.screens.InviteCodeScreenWrapper
import com.anytypeio.anytype.ui.onboarding.screens.RecoveryScreenWrapper
import com.anytypeio.anytype.ui.onboarding.screens.VoidScreenWrapper

class OnBoardingFragment : BaseComposeFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        val currentPage = remember { mutableStateOf(Page.AUTH) }
                        PagerIndicator(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                            pageCount = Page.values().size,
                            page = currentPage
                        )
                        Onboarding(currentPage)
                    }
                }
            }
        }
    }

    @Composable
    private fun Onboarding(currentPage: MutableState<Page>) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = OnboardingNavigation.auth) {
            composable(OnboardingNavigation.auth) {
                currentPage.value = Page.AUTH
                val component = componentManager().onboardingAuthComponent.get()
                val viewModel: OnboardingAuthViewModel = daggerViewModel {
                    component.getViewModel()
                }
                AuthScreenWrapper(
                    viewModel = viewModel,
                    navigateToInviteCode = {
                        navController.navigate(OnboardingNavigation.inviteCode)
                    },
                    navigateToLogin = {
                        navController.navigate(OnboardingNavigation.recovery)
                    }
                )
            }
            composable(OnboardingNavigation.inviteCode) {
                currentPage.value = Page.INVITE_CODE
                val component = componentManager().onboardingInviteCodeComponent.get()
                val viewModel: OnboardingInviteCodeViewModel = daggerViewModel {
                    component.getViewModel()
                }
                InviteCodeScreenWrapper(viewModel = viewModel)
                val navigationCommands =
                    viewModel.navigationFlow.collectAsState(
                        initial = OnboardingInviteCodeViewModel.InviteCodeNavigation.Idle
                    )
                LaunchedEffect(key1 = navigationCommands.value) {
                    when (navigationCommands.value) {
                        is OnboardingInviteCodeViewModel.InviteCodeNavigation.Void -> {
                            navController.navigate(OnboardingNavigation.void)
                        }
                        else -> {

                        }
                    }
                }
            }
            composable(OnboardingNavigation.recovery) {
                RecoveryScreenWrapper()
            }
            composable(OnboardingNavigation.void) {
                currentPage.value = Page.VOID
                VoidScreenWrapper()
            }
        }
    }

    override fun injectDependencies() {}

    override fun releaseDependencies() {}

}

fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: (Modifier.() -> Modifier)? = null
): Modifier {
    return if (condition) {
        then(ifTrue(Modifier))
    } else if (ifFalse != null) {
        then(ifFalse(Modifier))
    } else {
        this
    }
}