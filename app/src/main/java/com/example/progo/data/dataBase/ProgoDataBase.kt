package com.example.progo.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.progo.data.JsonConverters
import com.example.progo.data.dao.ExerciseRoutineDao
import com.example.progo.data.dao.ExerciseRoutineRecordDao
import com.example.progo.data.entities.Exercise
import com.example.progo.data.entities.ExerciseRecord
import com.example.progo.data.entities.ExerciseRoutine.ExerciseRoutineCrossRef
import com.example.progo.data.entities.RoutineRecord
import com.example.progo.data.entities.Routine

@Database(
    entities = [
        Exercise::class,
        Routine::class,
        ExerciseRoutineCrossRef::class,
        RoutineRecord::class,
        ExerciseRecord::class,
    ],
    version = 3
)
@TypeConverters(JsonConverters::class)
abstract class ProgoDataBase: RoomDatabase() {
    abstract fun exerciseRoutineDao(): ExerciseRoutineDao
    abstract fun exerciseRoutineRecordDao(): ExerciseRoutineRecordDao

    companion object{
        @Volatile
        private var INSTANCE: ProgoDataBase? = null

        fun getProgoDataBase(context: Context): ProgoDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProgoDataBase::class.java,
                    "progo_dataBase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}