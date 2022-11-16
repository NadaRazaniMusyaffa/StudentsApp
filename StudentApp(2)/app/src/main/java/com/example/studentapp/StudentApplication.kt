package com.example.studentapp

import android.app.Application
import com.example.studentapp.data.StudentRoomDatabase

class StudentApplication : Application() {
    val database : StudentRoomDatabase by lazy {
        StudentRoomDatabase.getDatabase(this)
    }
}