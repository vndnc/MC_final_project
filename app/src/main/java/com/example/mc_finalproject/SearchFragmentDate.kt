package com.example.mc_finalproject

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mc_finalproject.MyDatabase
import com.example.mc_finalproject.MyElement
import com.example.mc_finalproject.R
import com.example.mc_finalproject.databinding.HomePhotoListBinding
import com.example.mc_finalproject.databinding.SearchFragDateBinding
import java.text.SimpleDateFormat
import java.util.*

class SearchFragmentDate : Fragment() {
    lateinit var binding: SearchFragDateBinding
    private lateinit var db: MyDatabase.MyDbHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MyDatabase.MyDbHelper(requireContext())

        // 현재 날짜
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // 선택한 날짜로 이미지 검색
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                val images = db.selectAll()
                showImages(images)

            },
            year,
            month,
            day
        )


        binding.datePicker.setOnClickListener {
            datePicker.show()
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ImageAdapter()
        recyclerView.adapter = adapter
    }

    private fun showImages(images: List<MyElement>) {
        if (images.isNotEmpty()) {
            binding.resultText.visibility = View.VISIBLE
            binding.resultText.text = "${images.size}"
        } else {
            binding.resultText.visibility = View.GONE
        }
        adapter.setImages(images)
    }

    private inner class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
        private var images: List<MyElement> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            // 뷰 홀더 생성
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_photo_list, parent, false)
            val binding = HomePhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ImageViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            // 뷰 홀더에 데이터 바인딩
            val image = images[position]
            val drawable = byteArrayToDrawable(holder.binding.root.context, image.image)
            holder.binding.photo.setImageDrawable(drawable)
        }

        override fun getItemCount(): Int {
            // 데이터셋 크기 반환
            return images.size
        }

        fun setImages(images: List<MyElement>) {
            // 데이터셋 업데이트 후 어댑터에 알림
            this.images = images
            notifyDataSetChanged()
        }

        inner class ImageViewHolder(val binding: HomePhotoListBinding): RecyclerView.ViewHolder(binding.root)
    }

    private fun byteArrayToDrawable(context: Context, byteArray: ByteArray): Drawable? {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return BitmapDrawable(context.resources, bitmap)
    }
}
