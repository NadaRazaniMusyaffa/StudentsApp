package com.example.studentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.studentapp.Adapter.StudentListAdapter
import com.example.studentapp.data.StudentViewModel
import com.example.studentapp.data.StudentViewModelFactory
import com.example.studentapp.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding : FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel : StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).database.studentDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addButton.setOnClickListener{
            val action = StudentListFragmentDirections.actionStudentListFragmentToStudentInputFragment(position = -1, id = -1)
            view.findNavController().navigate(action)
        }

        val adapter = StudentListAdapter {
            val action = StudentListFragmentDirections.actionStudentListFragmentToStudentInputFragment(position = 0, id = it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.allStudents.observe(this.viewLifecycleOwner) {
           items -> items.let {
               adapter.submitList(it)
        }
        }
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}