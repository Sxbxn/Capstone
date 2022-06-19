package com.kyonggi.cellification.data.local.dao

import androidx.room.*
import com.kyonggi.cellification.data.model.cell.Cell

@Dao
interface CellDao {
    //전체 cell 가져오기
    @Query("SELECT * FROM cell_list")
    suspend fun getAll(): MutableList<Cell>

    // 해당 Email Cell 을 가져온다
    @Query("SELECT * FROM cell_list WHERE userId = :userid")
    suspend fun getCellsQueryEmail(userid: String): MutableList<Cell>

    // 특정 user의 cell 전체 삭제
    @Query("DELETE FROM cell_list WHERE userId = :userid")
    suspend fun deleteAllLocalCell(userid: String)

    // 해당 Cell 을 삭제한다
    @Delete
    suspend fun deleteCell(cell: Cell)

    @Insert(onConflict = OnConflictStrategy.REPLACE)   // 중복되는것이있으면 (출돌이 나면) 자동 update
    suspend fun insertCell(cell: Cell)

    // viability 검색
    @Query("SELECT * FROM cell_list WHERE viability >= :viability")
    suspend fun getCellFromViability(viability:Double): MutableList<Cell>
}