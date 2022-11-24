package com.anytypeio.anytype.features.relations

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.anytypeio.anytype.R
import com.anytypeio.anytype.analytics.base.Analytics
import com.anytypeio.anytype.core_models.Block
import com.anytypeio.anytype.core_models.Id
import com.anytypeio.anytype.core_models.Payload
import com.anytypeio.anytype.core_models.Relation
import com.anytypeio.anytype.core_models.ThemeColor
import com.anytypeio.anytype.core_ui.extensions.dark
import com.anytypeio.anytype.domain.`object`.UpdateDetail
import com.anytypeio.anytype.domain.block.repo.BlockRepository
import com.anytypeio.anytype.domain.config.Gateway
import com.anytypeio.anytype.domain.misc.UrlBuilder
import com.anytypeio.anytype.domain.objects.DefaultObjectStore
import com.anytypeio.anytype.domain.objects.DefaultStoreOfObjectTypes
import com.anytypeio.anytype.domain.objects.DefaultStoreOfRelations
import com.anytypeio.anytype.domain.objects.ObjectStore
import com.anytypeio.anytype.domain.objects.StoreOfObjectTypes
import com.anytypeio.anytype.domain.objects.StoreOfRelations
import com.anytypeio.anytype.domain.relations.AddFileToObject
import com.anytypeio.anytype.presentation.relations.ObjectSetConfig
import com.anytypeio.anytype.presentation.relations.providers.DataViewObjectRelationProvider
import com.anytypeio.anytype.presentation.relations.providers.DataViewObjectValueProvider
import com.anytypeio.anytype.presentation.relations.providers.ObjectDetailProvider
import com.anytypeio.anytype.presentation.sets.ObjectSet
import com.anytypeio.anytype.presentation.sets.ObjectSetDatabase
import com.anytypeio.anytype.presentation.sets.RelationValueDVViewModel
import com.anytypeio.anytype.presentation.util.CopyFileToCacheDirectory
import com.anytypeio.anytype.presentation.util.Dispatcher
import com.anytypeio.anytype.test_utils.MockDataFactory
import com.anytypeio.anytype.test_utils.utils.TestUtils.withRecyclerView
import com.anytypeio.anytype.test_utils.utils.checkHasText
import com.anytypeio.anytype.test_utils.utils.checkHasTextColor
import com.anytypeio.anytype.test_utils.utils.espresso.WithTextColorRes
import com.anytypeio.anytype.test_utils.utils.resources
import com.anytypeio.anytype.ui.relations.RelationValueBaseFragment
import com.anytypeio.anytype.utils.CoroutinesTestRule
import com.bartoszlipinski.disableanimationsrule.DisableAnimationsRule
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
@LargeTest
class DisplayRelationStatusValueTest {

    @Mock
    lateinit var repo: BlockRepository

    @Mock
    lateinit var dispatcher: Dispatcher<Payload>

    @Mock
    lateinit var gateway: Gateway

    @Mock
    lateinit var analytics: Analytics

    @Mock
    lateinit var copyFileToCacheDirectory: CopyFileToCacheDirectory

    private lateinit var updateDetail: UpdateDetail
    private lateinit var addFileToObject: AddFileToObject
    private lateinit var urlBuilder: UrlBuilder

    @get:Rule
    val animationsRule = DisableAnimationsRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val root = MockDataFactory.randomUuid()
    private val state = MutableStateFlow(ObjectSet.init())
    private val store: ObjectStore = DefaultObjectStore()
    private val storeOfRelations: StoreOfRelations = DefaultStoreOfRelations()
    private val storeOfObjectTypes: StoreOfObjectTypes = DefaultStoreOfObjectTypes()
    private val db = ObjectSetDatabase(store)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        updateDetail = UpdateDetail(repo)
        addFileToObject = AddFileToObject(repo)
        urlBuilder = UrlBuilder(gateway)
        TestRelationValueDVFragment.testVmFactory = RelationValueDVViewModel.Factory(
            relations = DataViewObjectRelationProvider(
                objectSetState = state,
                storeOfRelations = storeOfRelations
            ),
            values = DataViewObjectValueProvider(
                db = db
            ),
            details = object : ObjectDetailProvider {
                override fun provide(): Map<Id, Block.Fields> = state.value.details
            },
            urlBuilder = urlBuilder,
            copyFileToCache = copyFileToCacheDirectory,
            dispatcher = dispatcher,
            analytics = analytics,
            setObjectDetails = updateDetail,
            addFileToObject = addFileToObject,
            storeOfObjectTypes = storeOfObjectTypes
        )
    }

    @Test
    fun shouldDisplayEditButtonAndPlusButton() {
        // SETUP

        val relation = MockDataFactory.randomUuid()
        val target = MockDataFactory.randomUuid()

        val record: Map<String, Any?> = mapOf(
            ObjectSetConfig.ID_KEY to target
        )

        val viewer = Block.Content.DataView.Viewer(
            id = MockDataFactory.randomUuid(),
            name = MockDataFactory.randomString(),
            filters = emptyList(),
            sorts = emptyList(),
            viewerRelations = emptyList(),
            type = Block.Content.DataView.Viewer.Type.GRID
        )

        state.value = ObjectSet(
            blocks = listOf(
                Block(
                    id = MockDataFactory.randomUuid(),
                    children = emptyList(),
                    fields = Block.Fields.empty(),
                    content = Block.Content.DataView(
                        relations = listOf(
                            Relation(
                                key = relation,
                                isMulti = true,
                                name = MockDataFactory.randomString(),
                                format = Relation.Format.STATUS,
                                source = Relation.Source.values().random()
                            )
                        ),
                        viewers = listOf(viewer),
                        sources = listOf(
                            MockDataFactory.randomUuid()
                        )
                    )
                )
            ),
//            viewerDb = mapOf(
//                viewer.id to ObjectSet.ViewerData(
//                    records = listOf(record),
//                    total = 1
//                )
//            )
        )

        // TESTING

        launchFragment(
            bundleOf(
                RelationValueBaseFragment.CTX_KEY to root,
                RelationValueBaseFragment.RELATION_KEY to relation,
                RelationValueBaseFragment.TARGET_KEY to target
            )
        )

        // Checking that the buttons are invisible

        onView(withId(R.id.btnEditOrDone)).apply {
            check(matches(isDisplayed()))
        }

        onView(withId(R.id.btnAddValue)).apply {
            check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldSetRelationName() {

        // SETUP

        val name = "Status"

        val relation = MockDataFactory.randomUuid()
        val target = MockDataFactory.randomUuid()

        val record: Map<String, Any?> = mapOf(
            ObjectSetConfig.ID_KEY to target
        )

        val viewer = Block.Content.DataView.Viewer(
            id = MockDataFactory.randomUuid(),
            name = MockDataFactory.randomString(),
            filters = emptyList(),
            sorts = emptyList(),
            viewerRelations = emptyList(),
            type = Block.Content.DataView.Viewer.Type.GRID
        )

        state.value = ObjectSet(
            blocks = listOf(
                Block(
                    id = MockDataFactory.randomUuid(),
                    children = emptyList(),
                    fields = Block.Fields.empty(),
                    content = Block.Content.DataView(
                        relations = listOf(
                            Relation(
                                key = relation,
                                isMulti = true,
                                name = name,
                                format = Relation.Format.STATUS,
                                source = Relation.Source.values().random()
                            )
                        ),
                        viewers = listOf(viewer),
                        sources = listOf(
                            MockDataFactory.randomUuid()
                        )
                    )
                )
            ),
//            viewerDb = mapOf(
//                viewer.id to ObjectSet.ViewerData(
//                    records = listOf(record),
//                    total = 1
//                )
//            )
        )

        // TESTING

        launchFragment(
            bundleOf(
                RelationValueBaseFragment.CTX_KEY to root,
                RelationValueBaseFragment.RELATION_KEY to relation,
                RelationValueBaseFragment.TARGET_KEY to target
            )
        )

        // Checking that the name is set

        onView(withId(R.id.tvTagOrStatusRelationHeader)).apply {
            check(matches(withText(name)))
        }
    }

    @Test
    fun shouldRenderStatusFromDV() {

        // SETUP

        val option1Color = ThemeColor.values().random()
        val option2Color = ThemeColor.values().random()

        val option1 = Relation.Option(
            id = MockDataFactory.randomUuid(),
            text = "In progress",
            color = option1Color.code
        )

        val option2 = Relation.Option(
            id = MockDataFactory.randomUuid(),
            text = "Done",
            color = option2Color.code
        )

        val option3 = Relation.Option(
            id = MockDataFactory.randomUuid(),
            text = "Todo",
            color = ""
        )

        val relationKey = MockDataFactory.randomUuid()
        val target = MockDataFactory.randomUuid()

        val record: Map<String, Any?> = mapOf(
            ObjectSetConfig.ID_KEY to target,
            relationKey to listOf(option2.id)
        )

        val viewer = Block.Content.DataView.Viewer(
            id = MockDataFactory.randomUuid(),
            name = MockDataFactory.randomString(),
            filters = emptyList(),
            sorts = emptyList(),
            viewerRelations = emptyList(),
            type = Block.Content.DataView.Viewer.Type.GRID
        )

        state.value = ObjectSet(
            blocks = listOf(
                Block(
                    id = MockDataFactory.randomUuid(),
                    children = emptyList(),
                    fields = Block.Fields.empty(),
                    content = Block.Content.DataView(
                        relations = listOf(
                            Relation(
                                key = relationKey,
                                defaultValue = null,
                                isHidden = false,
                                isReadOnly = false,
                                isMulti = true,
                                name = MockDataFactory.randomString(),
                                source = Relation.Source.values().random(),
                                format = Relation.Format.STATUS,
                                selections = listOf(option1, option2, option3)
                            )
                        ),
                        viewers = listOf(viewer),
                        sources = listOf(
                            MockDataFactory.randomUuid()
                        )
                    )
                )
            ),
//            viewerDb = mapOf(
//                viewer.id to ObjectSet.ViewerData(
//                    records = listOf(record),
//                    total = 1
//                )
//            )
        )

        // TESTING

        launchFragment(
            bundleOf(
                RelationValueBaseFragment.CTX_KEY to root,
                RelationValueBaseFragment.RELATION_KEY to relationKey,
                RelationValueBaseFragment.TARGET_KEY to target
            )
        )

        val rvMatcher = withRecyclerView(R.id.recycler)

        onView(rvMatcher.atPositionOnView(0, R.id.tvStatusName)).apply {
            checkHasText(option2.text)
            checkHasTextColor(resources.dark(option2Color))
        }
    }

    @Test
    fun shouldRenderStatusWithDefaultColor() {

        // SETUP

        val option1 = Relation.Option(
            id = MockDataFactory.randomUuid(),
            text = "In progress",
            color = ""
        )

        val relationKey = MockDataFactory.randomUuid()
        val target = MockDataFactory.randomUuid()

        val record: Map<String, Any?> = mapOf(
            ObjectSetConfig.ID_KEY to target,
            relationKey to listOf(option1.id)
        )

        val viewer = Block.Content.DataView.Viewer(
            id = MockDataFactory.randomUuid(),
            name = MockDataFactory.randomString(),
            filters = emptyList(),
            sorts = emptyList(),
            viewerRelations = emptyList(),
            type = Block.Content.DataView.Viewer.Type.GRID
        )

        state.value = ObjectSet(
            blocks = listOf(
                Block(
                    id = MockDataFactory.randomUuid(),
                    children = emptyList(),
                    fields = Block.Fields.empty(),
                    content = Block.Content.DataView(
                        relations = listOf(
                            Relation(
                                key = relationKey,
                                defaultValue = null,
                                isHidden = false,
                                isReadOnly = false,
                                isMulti = true,
                                name = MockDataFactory.randomString(),
                                source = Relation.Source.values().random(),
                                format = Relation.Format.STATUS,
                                selections = listOf(option1)
                            )
                        ),
                        viewers = listOf(viewer),
                        sources = listOf(
                            MockDataFactory.randomUuid()
                        )
                    )
                )
            ),
//            viewerDb = mapOf(
//                viewer.id to ObjectSet.ViewerData(
//                    records = listOf(record),
//                    total = 1
//                )
//            )
        )

        // TESTING

        launchFragment(
            bundleOf(
                RelationValueBaseFragment.CTX_KEY to root,
                RelationValueBaseFragment.RELATION_KEY to relationKey,
                RelationValueBaseFragment.TARGET_KEY to target
            )
        )

        val rvMatcher = withRecyclerView(R.id.recycler)

        onView(rvMatcher.atPositionOnView(0, R.id.tvStatusName)).apply {
            check(matches(WithTextColorRes(com.anytypeio.anytype.core_ui.R.color.text_primary)))
        }
    }

    @Test
    fun shouldRenderEmptyState() {

        // SETUP

        val option1 = Relation.Option(
            id = MockDataFactory.randomUuid(),
            text = "In progress",
            color = MockDataFactory.randomString()
        )

        val option2 = Relation.Option(
            id = MockDataFactory.randomUuid(),
            text = "Done",
            color = MockDataFactory.randomString()
        )

        val option3 = Relation.Option(
            id = MockDataFactory.randomUuid(),
            text = "Todo",
            color = MockDataFactory.randomString()
        )

        val relationKey = MockDataFactory.randomUuid()
        val target = MockDataFactory.randomUuid()

        val record: Map<String, Any?> = mapOf(
            ObjectSetConfig.ID_KEY to target
        )

        val viewer = Block.Content.DataView.Viewer(
            id = MockDataFactory.randomUuid(),
            name = MockDataFactory.randomString(),
            filters = emptyList(),
            sorts = emptyList(),
            viewerRelations = emptyList(),
            type = Block.Content.DataView.Viewer.Type.GRID
        )

        state.value = ObjectSet(
            blocks = listOf(
                Block(
                    id = MockDataFactory.randomUuid(),
                    children = emptyList(),
                    fields = Block.Fields.empty(),
                    content = Block.Content.DataView(
                        relations = listOf(
                            Relation(
                                key = relationKey,
                                defaultValue = null,
                                isHidden = false,
                                isReadOnly = false,
                                isMulti = true,
                                name = MockDataFactory.randomString(),
                                source = Relation.Source.values().random(),
                                format = Relation.Format.STATUS,
                                selections = listOf(option1, option2, option3)
                            )
                        ),
                        viewers = listOf(viewer),
                        sources = listOf(
                            MockDataFactory.randomUuid()
                        )
                    )
                )
            ),
//            viewerDb = mapOf(
//                viewer.id to ObjectSet.ViewerData(
//                    records = listOf(record),
//                    total = 1
//                )
//            )
        )

        // TESTING

        launchFragment(
            bundleOf(
                RelationValueBaseFragment.CTX_KEY to root,
                RelationValueBaseFragment.RELATION_KEY to relationKey,
                RelationValueBaseFragment.TARGET_KEY to target
            )
        )

        val rvMatcher = withRecyclerView(R.id.recycler)

        onView(rvMatcher.atPositionOnView(0, R.id.tvEmptyMessage)).apply {
            check(matches(isDisplayed()))
        }
    }

    private fun launchFragment(args: Bundle): FragmentScenario<TestRelationValueDVFragment> {
        return launchFragmentInContainer(
            fragmentArgs = args,
            themeResId = R.style.AppTheme
        )
    }
}