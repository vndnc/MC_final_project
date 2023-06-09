package com.example.mc_finalproject

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.mc_finalproject.databinding.StorageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class SaveFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return StorageBinding.inflate(inflater, container, false).root
    }

    // 갤러리에서 이미지 불러오기
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
                // 갤러리에서 사진 불러와서 imageView를 바꿈
                if (bitmap != null) {
                    binding.insertPhoto.setImageBitmap(bitmap)
                } else {
                    Log.d("TAG", "image null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // 버튼 누르면 갤러리에서 선택한 사진 등록(일단 view에 보이게만 > 그냥 이미지 누르면으로 바꿀까요?
        binding.insertPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            requestGal.launch(intent)
        }

        // 저장버튼 누르면 db에 넣는 코드 추가해야함.
        val dbHelper = MyDatabase.MyDbHelper(requireContext())
        binding.insertBtn.setOnClickListener {
            var db = dbHelper.writableDatabase

            val myentry = MyDatabase.MyDBContract.MyEntry
            val values = ContentValues().apply {
                put(myentry.image, drawableToByteArray(binding.insertPhoto.drawable))
                put(myentry.date, binding.date.text.toString())
                put(myentry.place, binding.place.text.toString())
                put(myentry.name, binding.people.text.toString())
                put(myentry.comment, binding.memo.text.toString())
            }
            Log.d("TAG", values.toString())

            val newRowId = db?.insertOrThrow(myentry.TABLE_NAME, null, values)

            // 예외처리 필요하면 추가 해주기
//            try {
//                val newRowId = db?.insertOrThrow(myentry.TABLE_NAME, null, values)
//                Log.d("TAG", newRowId.toString())
//            } catch (e: SQLiteConstraintException) {
//                db?.update(
//                    myentry.TABLE_NAME,
//                    values,
//                    "${myentry.xx} LIKE ? ",
//                    arrayOf(binding.xx.text.toString())
//                )
//            }

            db.close()

            val getList = dbHelper.selectAll()
            for (e in getList) {
                Log.d("TAG", e.toString())
            }
        }
    }
}

private fun drawableToByteArray(drawable: Drawable?): ByteArray? {
    if (drawable is BitmapDrawable) {
        // BitmapDrawable인 경우 바로 bitmap을 얻어와서 byte 배열로 변환합니다.
        val bitmap = drawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        return byteArray
    } else if (drawable is VectorDrawable) {
        // VectorDrawable인 경우 비트맵을 생성하여 캔버스에 그린 후 byte 배열로 변환합니다.
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        return byteArray
    }

    return null
}
