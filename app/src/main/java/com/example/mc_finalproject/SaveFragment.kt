package com.example.mc_finalproject

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.mc_finalproject.databinding.StorageBinding

class SaveFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return StorageBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = StorageBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val requestGal = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            try {
                Log.d("TAG", "$it")
                Log.d("TAG", "${it.data!!}")
                Log.d("TAG", "${it.data!!.data!!}")
                val inputStream = view.context.contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, null)
                inputStream?.close()
                // 갤러리에서 선택한 사진 등록(일단 view에 보이게만
                if (bitmap != null) {
                    binding.insertPhoto.setImageBitmap(bitmap)
                } else {
                    Log.d("TAG", "image null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // 버튼을 갤러리를 불러와서 imageView를 바꿈 > 그냥 이미지 누르면으로 바꿀까요?
        binding.insertPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            requestGal.launch(intent)
        }

        // 저장버튼 누르면 db에 넣는 코드 추가해야함.
    }
}