package com.example.mc_finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mc_finalproject.databinding.ActivityMainBinding
import com.example.mc_finalproject.databinding.SearchBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var searchBinding = SearchBinding.inflate(layoutInflater)
        // setContentView(searchBinding.root) 이런거 있어야하는 것 같은데... 찾아보고 수정힐게요ㅠㅠ

        // 하단 바 Fragment 전환
        binding.homeBtn.setOnClickListener { supportFragmentManager.beginTransaction()
            .replace(binding.fragmentView.id, HomeFragment()).commit()
        }
        binding.searchBtn.setOnClickListener { supportFragmentManager.beginTransaction()
            .replace(binding.fragmentView.id, SearchFragment()).commit()
        }
        // 날짜로 검색할 경우
        searchBinding.searchDateBut.setOnClickListener { supportFragmentManager.beginTransaction()
            .replace(searchBinding.searchFragView.id, SearchFragment()).commit()
        }
        // 이름으로 검색할 경우
        searchBinding.searchNameBut.setOnClickListener { supportFragmentManager.beginTransaction()
            .replace(searchBinding.searchFragView.id, SearchFragmentName()).commit()
        }

        binding.saveBtn.setOnClickListener { supportFragmentManager.beginTransaction()
            .replace(binding.fragmentView.id, SaveFragment()).commit()
        }

        // Fragment 기본 화면 Home으로 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_view, HomeFragment()).commit()
        }
    }
}