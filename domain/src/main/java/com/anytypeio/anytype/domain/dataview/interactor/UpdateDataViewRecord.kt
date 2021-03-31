package com.anytypeio.anytype.domain.dataview.interactor

import com.anytypeio.anytype.domain.base.BaseUseCase
import com.anytypeio.anytype.domain.block.repo.BlockRepository
import com.anytypeio.anytype.core_models.DVRecord
import com.anytypeio.anytype.core_models.Id

/**
 * Use-case for updating data-view record.
 */
class UpdateDataViewRecord(
    private val repo: BlockRepository
) : BaseUseCase<Unit, UpdateDataViewRecord.Params>() {

    override suspend fun run(params: Params) = safe {
        repo.updateDataViewRecord(
            context = params.context,
            target = params.target,
            record = params.record,
            values = params.values
        )
    }

    /**
     * @property [context] operation's context
     * @property [target] DV's block id.
     * @property [record] id of the specific record, which we need to update
     * @property [values] values, or raw data of this specific [record].
     */
    class Params(
        val context: Id,
        val target: Id,
        val record: Id,
        val values: DVRecord
    )
}