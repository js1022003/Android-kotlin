package com.example.a7minutesworkout

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)
    @Delete
    suspend fun delete(historyEntity: HistoryEntity)
    @Update
    suspend fun update(historyEntity: HistoryEntity)

    @Query("SELECT * FROM 'history-table'")
    suspend fun fetchAllDates(): Flow<List<HistoryEntity>>
}