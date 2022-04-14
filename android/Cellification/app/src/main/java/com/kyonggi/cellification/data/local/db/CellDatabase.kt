package com.kyonggi.cellification.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kyonggi.cellification.data.local.dao.CellDao
import com.kyonggi.cellification.data.local.dao.userDao
import com.kyonggi.cellification.data.model.cell.Cell
import com.kyonggi.cellification.data.model.user.User

@Database(
    entities = [Cell::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class CellDatabase : RoomDatabase() {
    abstract fun cellDao(): CellDao
    abstract fun userDao(): userDao

    companion object {
        private var cellRoomInstance: CellDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CellDatabase? {
            if (cellRoomInstance == null) {
                synchronized(CellDatabase::class) {
                    cellRoomInstance = Room.databaseBuilder(
                        context.applicationContext,
                        CellDatabase::class.java,
                        "cell_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return cellRoomInstance
        }
    }
}