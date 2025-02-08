package com.example.progo.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.progo.data.dao.ExerciseRoutineDao
import com.example.progo.data.dao.ExerciseRoutineRecordDao
import com.example.progo.data.entities.exercise
import com.example.progo.data.entities.ExerciseRoutine.exerciseRoutineCrossRef
import com.example.progo.data.entities.routine

@Database(
    entities = [exercise::class, routine::class, exerciseRoutineCrossRef::class],
    version = 3
)
abstract class progoDataBase: RoomDatabase() {
    abstract fun exerciseRoutineDao(): ExerciseRoutineDao
    abstract fun exerciseRoutineRecordDao(): ExerciseRoutineRecordDao

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