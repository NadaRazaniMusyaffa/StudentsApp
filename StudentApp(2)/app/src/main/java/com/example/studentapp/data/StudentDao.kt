package com.example.studentapp.data

import android.content.ClipData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student : Student)
//
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(student : Student)
//
    @Query("SELECT * from student WHERE id = :id")
    fun getOneStudent( id: Int): Flow<Student>

    @Query("SELECT * from student WHERE uid = :uid")
    fun getStudentById(uid: Int): Flow<Student>

    @Query("SELECT * from student")
    fun getAllStudents(): Flow<List<Student>>
}