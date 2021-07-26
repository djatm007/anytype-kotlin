package com.anytypeio.anytype.presentation.sets.main

import MockDataFactory
import com.anytypeio.anytype.analytics.base.Analytics
import com.anytypeio.anytype.core_models.*
import com.anytypeio.anytype.core_models.restrictions.DataViewRestrictions
import com.anytypeio.anytype.domain.base.Either
import com.anytypeio.anytype.domain.block.interactor.UpdateText
import com.anytypeio.anytype.domain.config.Gateway
import com.anytypeio.anytype.domain.dataview.interactor.*
import com.anytypeio.anytype.domain.event.interactor.InterceptEvents
import com.anytypeio.anytype.domain.misc.UrlBuilder
import com.anytypeio.anytype.domain.page.CloseBlock
import com.anytypeio.anytype.domain.sets.OpenObjectSet
import com.anytypeio.anytype.domain.status.InterceptThreadStatus
import com.anytypeio.anytype.presentation.sets.ObjectSetRecordCache
import com.anytypeio.anytype.presentation.sets.ObjectSetReducer
import com.anytypeio.anytype.presentation.sets.ObjectSetSession
import com.anytypeio.anytype.presentation.sets.ObjectSetViewModel
import com.anytypeio.anytype.presentation.util.Dispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

open class ObjectSetViewModelTestSetup {

    val root: Id = MockDataFactory.randomString()

    @Mock
    lateinit var openObjectSet: OpenObjectSet

    @Mock
    lateinit var closeBlock: CloseBlock

    @Mock
    lateinit var addDataViewRelation: AddNewRelationToDataView

    @Mock
    lateinit var updateDataViewViewer: UpdateDataViewViewer

    @Mock
    lateinit var updateDataViewRecord: UpdateDataViewRecord

    @Mock
    lateinit var updateText: UpdateText

    @Mock
    lateinit var createDataViewRecord: CreateDataViewRecord

    @Mock
    lateinit var interceptEvents: InterceptEvents

    @Mock
    lateinit var setActiveViewer: SetActiveViewer

    @Mock
    lateinit var interceptThreadStatus: InterceptThreadStatus

    @Mock
    lateinit var gateway: Gateway

    @Mock
    lateinit var analytics: Analytics

    val dispatcher = Dispatcher.Default<Payload>()
    val reducer = ObjectSetReducer()
    val cache = ObjectSetRecordCache()
    val session = ObjectSetSession()

    val urlBuilder: UrlBuilder
        get() = UrlBuilder(gateway)

    fun buildViewModel(): ObjectSetViewModel = ObjectSetViewModel(
        openObjectSet = openObjectSet,
        closeBlock = closeBlock,
        addDataViewRelation = addDataViewRelation,
        updateDataViewRecord = updateDataViewRecord,
        updateDataViewViewer = updateDataViewViewer,
        updateText = updateText,
        interceptEvents = interceptEvents,
        interceptThreadStatus = interceptThreadStatus,
        createDataViewRecord = createDataViewRecord,
        setActiveViewer = setActiveViewer,
        dispatcher = dispatcher,
        reducer = reducer,
        objectSetRecordCache = cache,
        urlBuilder = urlBuilder,
        session = session,
        analytics = analytics
    )

    fun stubInterceptEvents(
        params: InterceptEvents.Params = InterceptEvents.Params(context = root),
        flow: Flow<List<Event>> = flowOf()
    ) {
        interceptEvents.stub {
            onBlocking { build(params) } doReturn flow
        }
    }

    fun stubOpenObjectSet(
        doc: List<Block> = emptyList(),
        details: Block.Details = Block.Details(),
        objectTypes: List<ObjectType> = emptyList(),
        relations: List<Relation> = emptyList(),
        additionalEvents: List<Event> = emptyList(),
        dataViewRestrictions: List<DataViewRestrictions> = emptyList()
    ) {
        openObjectSet.stub {
            onBlocking {
                invoke(any())
            } doReturn Either.Right(
                Payload(
                    context = root,
                    events = listOf(
                        Event.Command.ShowObject(
                            context = root,
                            root = root,
                            blocks = doc,
                            details = details,
                            relations = relations,
                            objectTypes = objectTypes,
                            dataViewRestrictions = dataViewRestrictions
                        )
                    ) + additionalEvents
                )
            )
        }
    }

    fun stubSetActiveViewer(
        events: List<Event> = emptyList()
    ) {
        setActiveViewer.stub {
            onBlocking { invoke(any()) } doReturn Either.Right(
                Payload(
                    context = root,
                    events = events
                )
            )
        }
    }

    fun stubUpdateDataViewViewer(
        events: List<Event> = emptyList()
    ) {
        updateDataViewViewer.stub {
            onBlocking { invoke(any()) } doReturn Either.Right(
                Payload(
                    context = root,
                    events = events
                )
            )
        }
    }
}