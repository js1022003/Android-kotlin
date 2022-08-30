package com.example.roomdemo

import android.app.ActionBar
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.databinding.DialogUpdateBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val employeeDao = (application as EmployeeApp).db.employeeDao()
        binding?.btnAdd?.setOnClickListener{
            addRecord(employeeDao)
        }

        lifecycleScope.launch{
            employeeDao.fetchAllEmployees().collect{
                val list = ArrayList(it)
                setupListDataIntRecyclerView(list, employeeDao)
            }
        }
    }

    fun addRecord(employeeDao: EmployeeDao){
        val name = binding?.etName?.text.toString()
        val email = binding?.etEmailId?.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty()){
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name=name,email=email))
                Toast.makeText(applicationContext,
                    "Record saved",
                    Toast.LENGTH_LONG)
                    .show()
                binding?.etName?.text?.clear()
                binding?.etEmailId?.text?.clear()
            }
        } else{
            Toast.makeText(applicationContext,
                "Insert name or email",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupListDataIntRecyclerView(employeeList: ArrayList<EmployeeEntity>,
                                             employeeDao: EmployeeDao){

        if(employeeList.isNotEmpty()){
            val itemAdapter = ItemAdapter(employeeList,
                {updateId ->
                    updateRecordDialog(updateId,employeeDao)
                },
                {deleteId->
                            deleteRecordAlertDialog(deleteId,employeeDao)
                }
            )

            binding?.rvItemsList?.layoutManager = LinearLayoutManager(this)
            binding?.rvItemsList?.adapter = itemAdapter
            binding?.rvItemsList?.visibility = View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility = View.GONE
        }else{
            binding?.rvItemsList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }

    }

    fun updateRecordDialog(id:Int,employeeDao: EmployeeDao)  {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            employeeDao.fetchEmployeeById(id).collect {
                binding.etUpdateName.setText(it.name)
                binding.etUpdateEmailId.setText(it.email)
            }
        }
        binding.tvUpdate.setOnClickListener {

            val name = binding.etUpdateName.text.toString()
            val email = binding.etUpdateEmailId.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                lifecycleScope.launch {
                    employeeDao.update(EmployeeEntity(id, name, email))
                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG)
                        .show()
                    updateDialog.dismiss() // Dialog will be dismissed
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name or Email cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.tvCancel.setOnClickListener{
            updateDialog.dismiss()
        }
        //Start the dialog and display it on screen.
        updateDialog.show()
    }


    /** Todo 6
     * Method is used to show the Alert Dialog and delete the selected employee.
     * We add an id to get the selected position and an employeeDao param to get the
     * methods from the dao interface then launch a coroutine block to call the methods
     */
    private fun deleteRecordAlertDialog(id:Int,employeeDao: EmployeeDao) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("삭제하시겠습니까?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        lifecycleScope.launch {
            employeeDao.fetchEmployeeById(id).collect {
                if(it != null){
                    builder.setMessage("Are you sure you wants to delete ${it.name}.")
                }
            }
        }

        //performing positive action
        builder.setPositiveButton("예") { dialogInterface, _ ->
            lifecycleScope.launch {
                employeeDao.delete(EmployeeEntity(id))
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()
            }
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        builder.setNegativeButton("아니오") { dialogInterface, _ ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }

}