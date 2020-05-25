package com.agileburo.anytype

import android.os.Build
import com.agileburo.anytype.core_ui.common.Markup
import com.agileburo.anytype.core_ui.common.ThemeColor
import com.agileburo.anytype.core_ui.common.toSpannable
import com.agileburo.anytype.domain.block.model.Block.Content.Text.Mark
import com.agileburo.anytype.ext.extractMarks
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class MarkupExtractTest {

    @Mock
    lateinit var markup : Markup

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should extract italic span`() {

        // SETUP

        val source = "Happy families are all alike; every unhappy family is unhappy in its own way"

        val mark = Markup.Mark(
            from = 0,
            to = 5,
            param = null,
            type = Markup.Type.ITALIC
        )

        stubMarkup(source, mark)

        val editable = markup.toSpannable()

        // TESTING

        val marks = editable.extractMarks()

        assertEquals(expected = 1, actual = marks.size)
        assertEquals(
            expected = Mark(
                range = mark.from..mark.to,
                param = null,
                type = Mark.Type.ITALIC
            ),
            actual = marks.first()
        )
    }

    @Test
    fun `should extract bold span`() {

        // SETUP

        val source = "Everything was in confusion in the Oblonskys’ house"

        val mark = Markup.Mark(
            from = 0,
            to = 5,
            param = null,
            type = Markup.Type.BOLD
        )

        stubMarkup(source, mark)

        val editable = markup.toSpannable()

        // TESTING

        val marks = editable.extractMarks()

        assertEquals(expected = 1, actual = marks.size)
        assertEquals(
            expected = Mark(
                range = mark.from..mark.to,
                param = null,
                type = Mark.Type.BOLD
            ),
            actual = marks.first()
        )
    }

    @Test
    fun `should extract strikethrough span`() {

        // SETUP

        val source = "Everything was in confusion in the Oblonskys’ house"

        val mark = Markup.Mark(
            from = 0,
            to = 5,
            param = null,
            type = Markup.Type.STRIKETHROUGH
        )

        stubMarkup(source, mark)

        val editable = markup.toSpannable()

        // TESTING

        val marks = editable.extractMarks()

        assertEquals(expected = 1, actual = marks.size)
        assertEquals(
            expected = Mark(
                range = mark.from..mark.to,
                param = null,
                type = Mark.Type.STRIKETHROUGH
            ),
            actual = marks.first()
        )
    }

    @Test
    fun `should extract keyboard span`() {

        // SETUP

        val source = "Everything was in confusion in the Oblonskys’ house"

        val mark = Markup.Mark(
            from = 0,
            to = 5,
            param = null,
            type = Markup.Type.KEYBOARD
        )

        stubMarkup(source, mark)

        val editable = markup.toSpannable()

        // TESTING

        val marks = editable.extractMarks()

        assertEquals(expected = 1, actual = marks.size)
        assertEquals(
            expected = Mark(
                range = mark.from..mark.to,
                param = null,
                type = Mark.Type.KEYBOARD
            ),
            actual = marks.first()
        )
    }

    @Test
    fun `should extract text color span`() {

        // SETUP

        val source = "Everything was in confusion in the Oblonskys’ house"

        val mark = Markup.Mark(
            from = 0,
            to = 5,
            param = ThemeColor.BLUE.title,
            type = Markup.Type.TEXT_COLOR
        )

        stubMarkup(source, mark)

        val editable = markup.toSpannable()

        // TESTING

        val marks = editable.extractMarks()

        assertEquals(expected = 1, actual = marks.size)
        assertEquals(
            expected = Mark(
                range = mark.from..mark.to,
                param = ThemeColor.BLUE.title,
                type = Mark.Type.TEXT_COLOR
            ),
            actual = marks.first()
        )
    }

    @Test
    fun `should extract background color span`() {

        // SETUP

        val source = "Everything was in confusion in the Oblonskys’ house"

        val mark = Markup.Mark(
            from = 0,
            to = 5,
            param = ThemeColor.BLUE.title,
            type = Markup.Type.BACKGROUND_COLOR
        )

        stubMarkup(source, mark)

        val editable = markup.toSpannable()

        // TESTING

        val marks = editable.extractMarks()

        assertEquals(expected = 1, actual = marks.size)
        assertEquals(
            expected = Mark(
                range = mark.from..mark.to,
                param = ThemeColor.BLUE.title,
                type = Mark.Type.BACKGROUND_COLOR
            ),
            actual = marks.first()
        )
    }

    @Test
    fun `should extract url span`() {

        // SETUP

        val source = "Everything was in confusion in the Oblonskys’ house"

        val mark = Markup.Mark(
            from = 0,
            to = 5,
            param = DataFactory.randomString(),
            type = Markup.Type.LINK
        )

        stubMarkup(source, mark)

        val editable = markup.toSpannable()

        // TESTING

        val marks = editable.extractMarks()

        assertEquals(expected = 1, actual = marks.size)
        assertEquals(
            expected = Mark(
                range = mark.from..mark.to,
                param = mark.param,
                type = Mark.Type.LINK
            ),
            actual = marks.first()
        )
    }

    private fun stubMarkup(
        source: String,
        mark: Markup.Mark
    ) {
        markup.stub {
            on { body } doReturn source
            on { marks } doReturn listOf(mark)
        }
    }
}