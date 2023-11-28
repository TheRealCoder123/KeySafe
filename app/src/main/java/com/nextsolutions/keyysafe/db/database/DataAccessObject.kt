package com.nextsolutions.keyysafe.db.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nextsolutions.keyysafe.db.entities.Entry
import com.nextsolutions.keyysafe.db.entities.Label
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField
import com.nextsolutions.keyysafe.entry.domain.model.EntryFiles
import kotlinx.coroutines.flow.Flow

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Query("Select * from entry where id=:id")
    fun getEntry(id: String) : Flow<Entry>

    @Query("Select * from entry where id=:id")
    suspend fun getEntryWithoutFlow(id: String) : Entry

    @Query("Select * from entry WHERE archived = 0 AND trashed = 0 ORDER BY timeStamp")
    fun getEntries() : Flow<List<Entry>>

    @Query("Select * from entry")
    fun getAllEntries() : Flow<List<Entry>>

    @Query("Select * from entry")
    suspend fun getAllEntriesWithoutFlow() : List<Entry>

    @Query("Select * from label")
    suspend fun getAllLabelsWithoutFlow() : List<Label>

    @Query("Select * from entry WHERE trashed = 0")
    fun getAllEntriesNotInTrash() : Flow<List<Entry>>

    @Query("Select * from entry WHERE archived = 0")
    fun getAllEntriesNotArchived() : Flow<List<Entry>>

    @Query("Select * from entry where labelId=:labelId")
    fun getAllEntriesByLabel(labelId: Int) : Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE archived = 0 AND trashed = 0 ORDER BY timeStamp DESC LIMIT :limit")
    fun getRecentEntries(limit: Int): Flow<List<Entry>>

    @Query("SELECT * FROM entry ORDER BY timeStamp DESC")
    fun getAllOrderedEntries(): Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE archived = 1 ORDER BY timeStamp DESC LIMIT :limit")
    fun getArchivedEntriesWithLimit(limit: Int = 10) : Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE archived = 1 AND labelId=:labelId ORDER BY timeStamp DESC LIMIT :limit")
    fun getArchivedEntriesWithLimitByLabel(limit: Int = 10, labelId: Int) : Flow<List<Entry>>
    @Query("Update entry set trashed=:trashed where id=:entryId")
    suspend fun moveEntryToTrash(trashed: Boolean, entryId: String)

    @Query("SELECT * FROM entry WHERE archived = 1")
    fun getArchivedEntries() : Flow<List<Entry>>

    @Query("SELECT * FROM entry WHERE trashed = 1")
    fun getTrashedEntries() : Flow<List<Entry>>

    @Query("Update entry set archived=:archived where id=:entryId")
    suspend fun archiveEntry(archived: Boolean, entryId: String)

    @Query("Update entry set askForAuth=:askForAuth where id=:entryId")
    suspend fun changeAskForAuth(askForAuth: Boolean, entryId: String)
    @Query("SELECT * FROM entry WHERE labelId = :labelId AND archived = 0 AND trashed = 0 ORDER BY timeStamp DESC LIMIT :limit")
    fun getRecentEntriesByLabel(limit: Int, labelId: Int): Flow<List<Entry>>
    @Query("Select * from entry where labelId=:labelId AND archived = 0 AND trashed = 0")
    fun getEntriesByLabel(labelId: Int) : Flow<List<Entry>>

    @Query("Update entry set labelId = 0 where labelId=:deleteLabelId")
    suspend fun deleteConnectionsToLabel(deleteLabelId: Int)


    @Query("SELECT * FROM entry WHERE trashed = 1 ORDER BY timeStamp DESC LIMIT :limit")

    fun getTrashedEntriesWithLimit(limit: Int = 10) : Flow<List<Entry>>
    @Query("SELECT * FROM entry WHERE trashed = 1 AND labelId=:labelId ORDER BY timeStamp DESC LIMIT :limit")
    fun getTrashedEntriesWithLimitByLabel(limit: Int = 10, labelId: Int) : Flow<List<Entry>>



    @Query("Update entry set title=:title, fields=:fields, files=:files, labelId=:labelId, color=:color, trashed=:trashed, archived=:archived, timeStamp=:timeStamp where id=:entryId")
    suspend fun saveEntry(
        entryId: String,
        title: String,
        fields: List<EntryDataField>,
        files: List<EntryFiles>,
        labelId: Int,
        color: Int,
        trashed: Boolean,
        archived: Boolean,
        timeStamp: Long
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: Label)

    @Query("Select * from label")
    fun getLabels() : Flow<List<Label>>

    @Query("Update label set title=:newTitle where id=:labelId")
    suspend fun updateLabelTitle(newTitle: String, labelId: Int)

    @Query("delete from label where id=:labelId")
    suspend fun deleteLabel(labelId: Int)


    @Query("delete from entry where id=:entryId")
    suspend fun deleteEntry(entryId: String)

    @Query("Update entry set trashed = 0 where id=:entryId")
    suspend fun removeFromTrash(entryId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntries(entries: List<Entry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabels(entries: List<Label>)

    @Transaction
    suspend fun restoreBackUp(entries: List<Entry>, labels: List<Label>) {
        insertEntries(entries)
        insertLabels(labels)
    }





}