package com.example.Phuc_29.ui.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Phuc_29.R
import com.example.Phuc_29.data.model.Student
import com.example.Phuc_29.databinding.DialogAddStudentBinding
import com.example.Phuc_29.databinding.FragmentFirstBinding
import com.example.Phuc_29.ui.adapter.StudentAdapter
import com.example.Phuc_29.ui.viewmodel.ShareViewModel
import com.example.Phuc_29.ui.viewmodel.StudentViewModel
import com.example.Phuc_29.ui.viewmodel.StudentViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var myAdapter: StudentAdapter
    private val shareViewModel: ShareViewModel by activityViewModels()
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdapter = StudentAdapter()
        binding.rvStudent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudent.adapter = myAdapter

        myAdapter.onClick = { position -> updateStudentDialog(position) }
        myAdapter.onLongClick = { position -> holdStudentDialog(position) }
        myAdapter.onDetaiClick = { position -> detailStudent(position) }
        myAdapter.onDeleteClick = { position -> holdStudentDialog(position) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                studentViewModel.students.collectLatest { students ->
                    myAdapter.submitStudents(students)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                studentViewModel.messages.collectLatest { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.floatingActionButton.setOnClickListener { showAddStudentDialog() }
    }

    private fun detailStudent(position: Int) {
        val student = myAdapter.getStudentAt(position)
        shareViewModel.student = student
        findNavController().navigate(R.id.lineGotoDetail)
    }

    private fun updateStudentDialog(position: Int) {
        val dialogBinding = DialogAddStudentBinding.inflate(layoutInflater)
        val student = myAdapter.getStudentAt(position)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        dialog.setView(dialogBinding.root)
        dialog.setTitle("Chỉnh sửa sinh viên")

        dialogBinding.apply {
            edtId.setText(student.Id)
            edtName.setText(student.Name)
            edtAge.setText(student.Age)
            edtHomeTown.setText(student.HomeTown)
            edtId.isEnabled = false

            if (student.Sex == "Nam") dialogRbMale.isChecked = true else dialogRbFemale.isChecked = true
            dialogCbGame.isChecked = student.Hobbies.contains("Dạo bộ")
            dialogCbMusic.isChecked = student.Hobbies.contains("Nghe nhạc")
        }

        dialog.setPositiveButton("Cập nhật") { _, _ ->
            val name = dialogBinding.edtName.text.toString()
            val age = dialogBinding.edtAge.text.toString()
            val hometown = dialogBinding.edtHomeTown.text.toString()

            val sex = if (dialogBinding.dialogRbMale.isChecked) "Nam" else "Nữ"
            val hobbyList = mutableListOf<String>()
            if (dialogBinding.dialogCbGame.isChecked) hobbyList.add("Dạo bộ")
            if (dialogBinding.dialogCbMusic.isChecked) hobbyList.add("Nghe nhạc")
            val hobbies = hobbyList.joinToString(", ")

            if (name.isNotEmpty() && age.isNotEmpty()) {
                studentViewModel.updateStudent(Student(student.Id, name, age, sex, hobbies,hometown))
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setNegativeButton("Hủy", null)
        dialog.show()
    }

    private fun holdStudentDialog(position: Int) {
        val student = myAdapter.getStudentAt(position)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        dialog.setTitle("Xác nhận xóa")
        dialog.setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.Name}?")

        dialog.setPositiveButton("Có") { _, _ ->
            studentViewModel.deleteStudent(student)
        }
        dialog.setNegativeButton("Không", null)
        dialog.show()
    }

    private fun showAddStudentDialog() {
        val dialogBinding = DialogAddStudentBinding.inflate(layoutInflater)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        dialog.setView(dialogBinding.root)
        dialog.setTitle("Thêm sinh viên")

        dialog.setPositiveButton("Lưu") { _, _ ->
            val id = dialogBinding.edtId.text.toString()
            val name = dialogBinding.edtName.text.toString()
            val age = dialogBinding.edtAge.text.toString()
            val hometown = dialogBinding.edtHomeTown.text.toString()

            val sex = if (dialogBinding.dialogRbMale.isChecked) "Nam" else "Nữ"

            val hobbyList = mutableListOf<String>()
            if (dialogBinding.dialogCbGame.isChecked) hobbyList.add("Dạo bộ")
            if (dialogBinding.dialogCbMusic.isChecked) hobbyList.add("Nghe nhạc")
            val hobbies = hobbyList.joinToString(", ")

            if (id.isNotEmpty() && name.isNotEmpty() && age.isNotEmpty()) {
                studentViewModel.addStudent(Student(id, name, age, sex, hobbies,hometown))
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setNegativeButton("Hủy", null)
        dialog.show()
    }
}