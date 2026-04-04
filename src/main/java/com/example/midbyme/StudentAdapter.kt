package com.example.midbyme


import android.R.attr.onClick
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.midbyme.databinding.ItemStudentBinding

class StudentAdapter(private val students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.binding.apply {
            tvName.text = "Họ và tên: ${student.Name}"
            tvClass.text = "Tuổi: ${student.Age}"

            // --- Đổ dữ liệu Giới tính ---
            if (student.Sex == "Nam") {
                rbMale.isChecked = true
            } else {
                rbFemale.isChecked = true
            }


            inCbGame.isChecked = student.Hobbies.contains("Dạo bộ")
            inCbMusic.isChecked = student.Hobbies.contains("Nghe nhạc")

            rbMale.isEnabled = false
            rbFemale.isEnabled = false
            inCbGame.isEnabled = false
            inCbMusic.isEnabled = false

            root.setOnClickListener {
                onClick?.invoke(position)
            }

            root.setOnLongClickListener {
                onLongClick?.invoke(position)
                true
            }

            btnDetail.setOnClickListener {
                onDetaiClick?.invoke(position)
            }

            btnDelete.setOnClickListener {
                onDeleteClick?.invoke(position)
            }
        }
    }

    var onLongClick: ((Int) -> Unit)? = null

    var onDetaiClick: ((Int) -> Unit)? = null

    var onDeleteClick: ((Int) -> Unit)? = null

    var onClick: ((Int) -> Unit)? = null

    override fun getItemCount(): Int = students.size
}