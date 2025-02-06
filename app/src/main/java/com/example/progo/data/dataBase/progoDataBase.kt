package com.example.progo.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.progo.data.dao.ExerciseRoutineDao
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.exerciseRoutineCrossRef
import com.example.progo.data.entities.routine

@Database(
    entities = [exercise::class, routine::class, exerciseRoutineCrossRef::class],
    version = 2
)
abstract class progoDataBase: RoomDatabase() {
    abstract fun exerciseRoutineDao(): ExerciseRoutineDao

    companion object{
        @Volatile
        private var INSTANCE: progoDataBase? = null

        fun getProgoDataBase(context: Context): progoDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    progoDataBase::class.java,
                    "progo_dataBase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}