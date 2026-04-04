package com.example.midbyme

import kotlin.jvm.java


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midbyme.databinding.DialogAddStudentBinding
import com.example.midbyme.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private var studentList = ArrayList<Student>()
    private lateinit var myAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (studentList.isEmpty()){
            studentList.add(Student("24IT021", "Nguyễn Khiêm", "19", "Nam", "Nghe nhạc"))
            studentList.add(Student("24IT102", "Lê Văn Tuấn", "20", "Nam", "Dạo bộ"))
            studentList.add(Student("24IT103", "Trần Thị Bích Tuyền", "21", "Nữ", "Dạo bộ, Nghe nhạc"))
        }

        myAdapter = StudentAdapter(studentList)
        binding.rvStudent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStudent.adapter = myAdapter

        myAdapter.onClick = { position -> updateStudentDialog(position) }
        myAdapter.onLongClick = { position -> holdStudentDialog(position) }
        myAdapter.onDetaiClick = { position -> detailStudent(position)}

        myAdapter.onDeleteClick = {positon ->holdStudentDialog(positon)}

        binding.floatingActionButton.setOnClickListener { showAddStudentDialog() }
    }

    private fun detailStudent(position: Int) {
        val student = studentList[position]
        val viewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
        viewModel.Student = student
        findNavController().navigate(R.id.lineGotoDetail)
    }

    private fun updateStudentDialog(position: Int) {
        val dialogBinding = DialogAddStudentBinding.inflate(layoutInflater)
        val student = studentList[position]

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        dialog.setView(dialogBinding.root)
        dialog.setTitle("Chỉnh sửa sinh viên")

        dialogBinding.apply {
            edtId.setText(student.Id)
            edtName.setText(student.Name)
            edtAge.setText(student.Age)
            edtId.isEnabled = false

            // FIX ID: Sử dụng đúng ID trong XML dialog
            if (student.Sex == "Nam") dialogRbMale.isChecked = true else dialogRbFemale.isChecked = true
            dialogCbGame.isChecked = student.Hobbies.contains("Dạo bộ")
            dialogCbMusic.isChecked = student.Hobbies.contains("Nghe nhạc")
        }

        dialog.setPositiveButton("Cập nhật") { _, _ ->
            val name = dialogBinding.edtName.text.toString()
            val age = dialogBinding.edtAge.text.toString()

            val sex = if (dialogBinding.dialogRbMale.isChecked) "Nam" else "Nữ"
            val hobbyList = mutableListOf<String>()
            if (dialogBinding.dialogCbGame.isChecked) hobbyList.add("Dạo bộ")
            if (dialogBinding.dialogCbMusic.isChecked) hobbyList.add("Nghe nhạc")
            val hobbies = hobbyList.joinToString(", ")

            if (name.isNotEmpty() && age.isNotEmpty()) {
                studentList[position] = Student(student.Id, name, age, sex, hobbies)
                myAdapter.notifyItemChanged(position)
            }
        }
        dialog.setNegativeButton("Hủy", null)
        dialog.show()
    }

    private fun holdStudentDialog(position: Int) {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        dialog.setTitle("Xác nhận xóa")
        dialog.setMessage("Bạn có chắc chắn muốn xóa sinh viên ${studentList[position].Name}?")

        dialog.setPositiveButton("Có") { _, _ ->
            studentList.removeAt(position)
            myAdapter.notifyItemRemoved(position)
            myAdapter.notifyItemRangeChanged(position, studentList.size)
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
            val sex = if (dialogBinding.dialogRbMale.isChecked) "Nam" else "Nữ"

            val hobbyList = mutableListOf<String>()
            if (dialogBinding.dialogCbGame.isChecked) hobbyList.add("Dạo bộ")
            if (dialogBinding.dialogCbMusic.isChecked) hobbyList.add("Nghe nhạc")
            val hobbies = hobbyList.joinToString(", ")

            if (id.isNotEmpty() && name.isNotEmpty() && age.isNotEmpty()) {
                studentList.add(Student(id, name, age, sex, hobbies))
                myAdapter.notifyItemInserted(studentList.size - 1)
                binding.rvStudent.smoothScrollToPosition(studentList.size - 1)
            }
        }
        dialog.setNegativeButton("Hủy", null)
        dialog.show()
    }
}