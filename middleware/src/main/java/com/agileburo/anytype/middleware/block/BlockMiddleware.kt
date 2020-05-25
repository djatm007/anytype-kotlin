package com.agileburo.anytype.middleware.block

import com.agileburo.anytype.data.auth.model.CommandEntity
import com.agileburo.anytype.data.auth.model.ConfigEntity
import com.agileburo.anytype.data.auth.model.PayloadEntity
import com.agileburo.anytype.data.auth.model.Response
import com.agileburo.anytype.data.auth.repo.block.BlockRemote
import com.agileburo.anytype.middleware.interactor.Middleware
import com.agileburo.anytype.middleware.toMiddleware

class BlockMiddleware(
    private val middleware: Middleware
) : BlockRemote {

    override suspend fun getConfig(): ConfigEntity = middleware.config

    override suspend fun openDashboard(
        contextId: String,
        id: String
    ): PayloadEntity = middleware.openDashboard(contextId, id)

    override suspend fun closeDashboard(id: String) {
        middleware.closeDashboard(id)
    }

    override suspend fun createPage(parentId: String): String = middleware.createPage(parentId)

    override suspend fun openPage(id: String): PayloadEntity = middleware.openBlock(id)
    override suspend fun openProfile(id: String): PayloadEntity = middleware.openBlock(id)

    override suspend fun closePage(id: String) {
        middleware.closePage(id)
    }

    override suspend fun updateDocumentTitle(command: CommandEntity.UpdateTitle) {
        middleware.updateDocumentTitle(command)
    }

    override suspend fun updateText(command: CommandEntity.UpdateText) {
        middleware.updateText(
            command.contextId,
            command.blockId,
            command.text,
            command.marks.map { it.toMiddleware() }
        )
    }

    override suspend fun uploadUrl(command: CommandEntity.UploadBlock) {
        middleware.uploadMediaBlockContent(command)
    }

    override suspend fun updateTextStyle(command: CommandEntity.UpdateStyle) {
        middleware.updateTextStyle(command)
    }

    override suspend fun updateTextColor(
        command: CommandEntity.UpdateTextColor
    ): PayloadEntity = middleware.updateTextColor(command)

    override suspend fun updateBackgroundColor(
        command: CommandEntity.UpdateBackgroundColor
    ): PayloadEntity = middleware.updateBackgroundColor(command)

    override suspend fun updateAlignment(
        command: CommandEntity.UpdateAlignment
    ) : PayloadEntity = middleware.updateAlignment(command)

    override suspend fun updateCheckbox(command: CommandEntity.UpdateCheckbox) {
        middleware.updateCheckbox(
            command.context,
            command.target,
            command.isChecked
        )
    }

    override suspend fun create(
        command: CommandEntity.Create
    ): Pair<String, PayloadEntity> = middleware.createBlock(
        command.context,
        command.target,
        command.position,
        command.prototype
    )

    override suspend fun createDocument(
        command: CommandEntity.CreateDocument
    ): Pair<String, String> = middleware.createDocument(command)

    override suspend fun duplicate(
        command: CommandEntity.Duplicate
    ): Pair<String, PayloadEntity> = middleware.duplicate(command)

    override suspend fun dnd(command: CommandEntity.Dnd) {
        middleware.dnd(command)
    }

    override suspend fun unlink(
        command: CommandEntity.Unlink
    ): PayloadEntity = middleware.unlink(command)

    override suspend fun merge(
        command: CommandEntity.Merge
    ): PayloadEntity = middleware.merge(command)

    override suspend fun split(
        command: CommandEntity.Split
    ): Pair<String, PayloadEntity> = middleware.split(command)

    override suspend fun setIconName(
        command: CommandEntity.SetIconName
    ) = middleware.setIconName(command)

    override suspend fun setupBookmark(
        command: CommandEntity.SetupBookmark
    ): PayloadEntity = middleware.setupBookmark(command)

    override suspend fun undo(command: CommandEntity.Undo) = middleware.undo(command)

    override suspend fun redo(command: CommandEntity.Redo) = middleware.redo(command)

    override suspend fun archiveDocument(
        command: CommandEntity.ArchiveDocument
    ) = middleware.archiveDocument(command)

    override suspend fun replace(
        command: CommandEntity.Replace
    ): Pair<String, PayloadEntity> = middleware.replace(command)

    override suspend fun paste(
        command: CommandEntity.Paste
    ): Response.Clipboard.Paste = middleware.paste(command)
}