package com.anytypeio.anytype.di.feature.relations

import com.anytypeio.anytype.analytics.base.Analytics
import com.anytypeio.anytype.core_models.Payload
import com.anytypeio.anytype.core_utils.di.scope.PerDialog
import com.anytypeio.anytype.domain.block.repo.BlockRepository
import com.anytypeio.anytype.domain.dataview.interactor.AddRelationToDataView
import com.anytypeio.anytype.domain.dataview.interactor.UpdateDataViewViewer
import com.anytypeio.anytype.domain.objects.StoreOfRelations
import com.anytypeio.anytype.domain.relations.AddRelationToObject
import com.anytypeio.anytype.domain.relations.ObjectRelationList
import com.anytypeio.anytype.presentation.relations.RelationAddToDataViewViewModel
import com.anytypeio.anytype.presentation.relations.RelationAddToObjectViewModel
import com.anytypeio.anytype.presentation.relations.providers.ObjectRelationProvider
import com.anytypeio.anytype.presentation.sets.ObjectSet
import com.anytypeio.anytype.presentation.sets.ObjectSetSession
import com.anytypeio.anytype.presentation.util.Dispatcher
import com.anytypeio.anytype.ui.relations.RelationAddToDataViewFragment
import com.anytypeio.anytype.ui.relations.RelationAddToObjectBlockFragment
import com.anytypeio.anytype.ui.relations.RelationAddToObjectFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.flow.StateFlow

@Subcomponent(modules = [RelationAddToObjectModule::class])
@PerDialog
interface RelationAddToObjectSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun module(module: RelationAddToObjectModule): Builder
        fun build(): RelationAddToObjectSubComponent
    }

    fun inject(fragment: RelationAddToObjectFragment)
    fun inject(fragment: RelationAddToObjectBlockFragment)
}

@Module
object RelationAddToObjectModule {
    @JvmStatic
    @Provides
    @PerDialog
    fun provideViewModelFactory(
        addRelationToObject: AddRelationToObject,
        storeOfRelations: StoreOfRelations,
        dispatcher: Dispatcher<Payload>,
        analytics: Analytics,
        relationsProvider: ObjectRelationProvider,
    ): RelationAddToObjectViewModel.Factory = RelationAddToObjectViewModel.Factory(
        storeOfRelations = storeOfRelations,
        addRelationToObject = addRelationToObject,
        dispatcher = dispatcher,
        analytics = analytics,
        relationsProvider = relationsProvider,
    )

    @JvmStatic
    @Provides
    @PerDialog
    fun provideObjectRelationListUseCase(
        repo: BlockRepository
    ): ObjectRelationList = ObjectRelationList(repo)
}

@Subcomponent(modules = [RelationAddToDataViewModule::class])
@PerDialog
interface RelationAddToDataViewSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun module(module: RelationAddToDataViewModule): Builder
        fun build(): RelationAddToDataViewSubComponent
    }

    fun inject(fragment: RelationAddToDataViewFragment)
}

@Module
object RelationAddToDataViewModule {
    @JvmStatic
    @Provides
    @PerDialog
    fun provideViewModelFactory(
        addRelationToDataView: AddRelationToDataView,
        storeOfRelations: StoreOfRelations,
        dispatcher: Dispatcher<Payload>,
        state: StateFlow<ObjectSet>,
        session: ObjectSetSession,
        updateDataViewViewer: UpdateDataViewViewer,
        analytics: Analytics,
        relationsProvider: ObjectRelationProvider,
    ): RelationAddToDataViewViewModel.Factory = RelationAddToDataViewViewModel.Factory(
        storeOfRelations = storeOfRelations,
        addRelationToDataView = addRelationToDataView,
        dispatcher = dispatcher,
        state = state,
        session = session,
        updateDataViewViewer = updateDataViewViewer,
        analytics = analytics,
        relationsProvider = relationsProvider
    )

    @JvmStatic
    @Provides
    @PerDialog
    fun provideObjectRelationListUseCase(
        repo: BlockRepository
    ): ObjectRelationList = ObjectRelationList(repo)

    @JvmStatic
    @Provides
    @PerDialog
    fun provideAddRelationToDataViewUseCase(
        repo: BlockRepository
    ): AddRelationToDataView = AddRelationToDataView(repo)
}