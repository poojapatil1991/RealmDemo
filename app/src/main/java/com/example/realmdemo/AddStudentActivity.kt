package com.example.realmdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*


class AddStudentActivity : AppCompatActivity() {
    val realm: Realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_add_student.setOnClickListener(View.OnClickListener { v: View -> saveData() })

        btnAdd.setOnClickListener(View.OnClickListener { v: View -> showHideAddSearch(0) })
        btnSearch.setOnClickListener(View.OnClickListener { v: View -> showHideAddSearch(1) })
        btnDelete.setOnClickListener(View.OnClickListener { v: View -> showHideAddSearch(2) })
        btnUpdate.setOnClickListener(View.OnClickListener { v: View -> showHideAddSearch(3) })
        btnReadAll.setOnClickListener(View.OnClickListener { v: View -> showHideAddSearch(4) })
        btn_search_student.setOnClickListener(View.OnClickListener { v: View ->
            getSingleStudent(
                ed_rollno_search.text.toString()
            )
        })
        btn_delete_student.setOnClickListener(View.OnClickListener { v: View ->
            deleteSingleStudent(
                ed_rollno_delete.text.toString()
            )
        })
        btn_update_student.setOnClickListener(View.OnClickListener { v: View ->
            updateSingleStudent(
                ed_rollno_update.text.toString()
            )
        })
    }

    private fun saveData() {
        realm.executeTransactionAsync({
            val student = it.createObject(StudentModel::class.java)
            student.rollNo = ed_rollno.text.toString()
            student.name = ed_student_name.text.toString()
            student.schoolName = ed_school_name.text.toString()
            student.standard = ed_standard.text.toString()
        }, {
            Log.d("Realm Demo", "On Success: Data Written Successfully!")
            //Toast.makeText(this,"Data Added successfully",Toast.LENGTH_SHORT).show()
            readData()
        }, {
            Log.d("Realm Demo", "On Error: Error in saving Data!")
            Toast.makeText(this, "Error while adding data", Toast.LENGTH_SHORT).show()
        })
    }

    private fun readData() {

        val students = realm.where(StudentModel::class.java).findAll()
        var response = ""
        students.forEach {
            response =
                response + "Roll No: ${it.rollNo},Name: ${it.name}, Age: ${it.schoolName},Age: ${it.standard}" + "\n"
        }
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()

    }

    private fun deleteAllData() {
        realm.beginTransaction() // Call this method before starting transaction
        realm.deleteAll() //  delete all data in database
        realm.commitTransaction() // Call this method after transaction
    }

    private fun getSingleStudent(rollno: String) {

        val students =
            realm.where(StudentModel::class.java).equalTo("rollNo", rollno).findFirst();
        if (students != null) {
            var response =
                "Roll No: ${students?.rollNo},Name: ${students?.name}, Age: ${students?.schoolName},Age: ${students?.standard}" + "\n"
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
        }

    }
    private fun deleteSingleStudent(rollno: String) {
        val students =
            realm.where(StudentModel::class.java).equalTo("rollNo", rollno).findFirst();
        if (students != null) {
            realm.beginTransaction()
            students?.deleteFromRealm()
            realm.commitTransaction()
            Toast.makeText(this, "deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSingleStudent(rollno: String) {
        val students =
            realm.where(StudentModel::class.java).equalTo("rollNo", rollno).findFirst();
        if (students != null) {
            realm.beginTransaction()
            students?.name = ed_student_name_update.text.toString()
            students?.schoolName = ed_school_name_update.text.toString()
            students?.standard = ed_standard_update.text.toString()
            realm.commitTransaction()
            getSingleStudent(rollno)
        } else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showHideAddSearch(flag: Int) {
        when (flag) {
            0 -> {
                addLL.visibility = View.VISIBLE
                searchLL.visibility = View.GONE
                deleteLL.visibility = View.GONE
                updateLL.visibility = View.GONE
            }
            1 -> {
                addLL.visibility = View.GONE
                searchLL.visibility = View.VISIBLE
                deleteLL.visibility = View.GONE
                updateLL.visibility = View.GONE
            }
            2 -> {
                addLL.visibility = View.GONE
                searchLL.visibility = View.GONE
                deleteLL.visibility = View.VISIBLE
                updateLL.visibility = View.GONE
            }
            3 -> {
                addLL.visibility = View.GONE
                searchLL.visibility = View.GONE
                deleteLL.visibility = View.GONE
                updateLL.visibility = View.VISIBLE
            }
            4 -> {
                addLL.visibility = View.GONE
                searchLL.visibility = View.GONE
                deleteLL.visibility = View.GONE
                updateLL.visibility = View.GONE
                readData()
            }
        }
    }

}
