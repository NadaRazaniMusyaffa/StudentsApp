package com.example.studentapp.data

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class StudentViewModel(private val studentDao: StudentDao) : ViewModel() {

    val allStudents : LiveData<List<Student>> = studentDao.getAllStudents().asLiveData()

    fun addNewStudent(uid : String, name : String){
        val newStudent = Student(uid = uid, name = name)

        viewModelScope.launch {
            studentDao.insert(newStudent)
        }
    }

    fun getOneStudent(id : Int) : LiveData<Student> {
        return studentDao.getOneStudent(id).asLiveData()
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            studentDao.update(student)
        }
    }

}

class StudentViewModelFactory(private val studentDao: StudentDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(studentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}