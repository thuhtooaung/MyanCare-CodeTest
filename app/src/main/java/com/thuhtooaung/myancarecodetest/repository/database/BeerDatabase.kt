package com.thuhtooaung.myancarecodetest.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thuhtooaung.myancarecodetest.data.local.BeerEntity

@Database(
    entities = [BeerEntity::class],
    version = 1
)
abstract class BeerDatabase: RoomDatabase() {

    abstract val dao: BeerDao

}