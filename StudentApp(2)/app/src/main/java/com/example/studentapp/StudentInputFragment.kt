package com.example.studentapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.studentapp.data.Student
import com.example.studentapp.data.StudentViewModel
import com.example.studentapp.data.StudentViewModelFactory
import com.example.studentapp.databinding.FragmentStudentInputBinding
import com.example.studentapp.databinding.FragmentStudentListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.ViewUtils.hideKeyboard


private const val POSITION = "position"
private const val ID = "id"

class StudentInputFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var position : Int = -1
    private var _id : Int = -1

    private lateinit var student: Student

    private var _binding : FragmentStudentInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel : StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).database.studentDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            position = it.getInt(POSITION)
            _id = it.getInt(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentInputBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // add student
        if(position == -1) {
            binding.saveButton.text = "Add"

            binding.saveButton.setOnClickListener {
                hideKeyboard()
                if(isInputDataNull()){
                    showInputErrorDialog()
                }

                else {
                    viewModel.addNewStudent(
                        binding.idTextField.text.toString(),
                        binding.nameTextField.text.toString()
                    )
                    hideKeyboard()
                    showInputSuccessDialog()
                }
            }
        }

        else {
            binding.saveButton.text = "Edit"

            viewModel.getOneStudent(_id).observe(this.viewLifecycleOwner) {
                selected -> student = selected
                binding.idTextField.setText(student.uid)
                binding.nameTextField.setText(student.name)

                binding.saveButton.setOnClickListener {
                    if(isInputDataNull()){
                        showInputErrorDialog()
                    }

                    else {
                        val updatedStudent = student.copy(
                            uid = binding.idTextField.text.toString(),
                            name = binding.nameTextField.text.toString()
                        )
                        viewModel.updateStudent(updatedStudent)
                        hideKeyboard()
                        showInputSuccessDialog()
                    }

                }

            }

        }

    }

    fun isInputDataNull(): Boolean {
        val name : Boolean = binding.nameTextField.text.toString().isNullOrBlank()
        val id : Boolean = binding.idTextField.text.toString().isNullOrBlank()

        Log.d("Cond", "${id || name}")

        return id || name
    }

    fun showInputErrorDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage("Nama Atau Id Harus Terisi, Data Tidak Berhasil Disimpan")
            .setPositiveButton("OK", null)
            .setCancelable(true)
            .show()
    }

    fun showInputSuccessDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Berhasil")
            .setMessage("Data Berhasil Disimpan")
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val action = StudentInputFragmentDirections.actionStudentInputFragmentToStudentListFragment()
                requireView().findNavController().navigate(action)
            }
            .show()
    }

    fun hideKeyboard() {
        val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = requireActivity().currentFocus
        currentFocusedView?.let {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }





    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentInputFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}