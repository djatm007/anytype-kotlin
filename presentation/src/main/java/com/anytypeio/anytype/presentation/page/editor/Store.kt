package com.anytypeio.anytype.presentation.page.editor

import com.anytypeio.anytype.domain.block.model.Block
import com.anytypeio.anytype.domain.common.Id
import com.anytypeio.anytype.domain.editor.Editor
import com.anytypeio.anytype.presentation.page.editor.model.BlockView
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import timber.log.Timber

/**
 * Reactive store
 * @param T stored type
 */
interface Store<T> {

    /**
     * @return streams of values
     */
    fun stream(): Flow<T>

    /**
     * @return current/last value
     */
    fun current(): T

    /**
     * Updates current values
     */
    suspend fun update(t: T)

    fun cancel()

    open class State<T>(initial : T) : Store<T> {

        private val state = MutableStateFlow(initial)

        override fun stream(): Flow<T> = state
        override fun current(): T = state.value
        override suspend fun update(t: T) { state.value = t }
        override fun cancel() {}
    }

    open class Conflated<T>(default: T) : Store<T> {

        private val channel = ConflatedBroadcastChannel(default)
        private val stream = channel.asFlow()

        override fun stream(): Flow<T> = stream
        override fun current(): T = channel.value
        override suspend fun update(t: T) = channel.send(t)
        override fun cancel() = channel.cancel()
    }

    class Focus : Conflated<Editor.Focus>(Editor.Focus.empty()) {
        override suspend fun update(t: Editor.Focus) {
            Timber.d("Update focus in store: $t")
            super.update(t)
        }
    }
    class Context : Conflated<String>("")
    class Screen : State<List<BlockView>>(emptyList())

    class Details : Conflated<Block.Details>(Block.Details()) {
        suspend fun add(target: Id, fields: Block.Fields) {
            update(current().copy(details = current().details + mapOf(target to fields)))
        }
    }

    class TextSelection : Conflated<Editor.TextSelection>(Editor.TextSelection.empty())
}