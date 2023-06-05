package com.example.mc_finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mc_finalproject.databinding.SearchBinding
import com.example.mc_finalproject.databinding.SearchFragDateBinding

class SearchFragment : Fragment() {
    lateinit var binding: SearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchBinding.inflate(layoutInflater)
        return binding.root
    }

    // 서치 frag 나누는거 다시 해봤는데 이제 앱이 꺼지네요....
    // 아래 주석처리하면 선규님이 해두셨던 하단버튼으로 frag 이동은 정상적으로 됩니다!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchDateBut.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.searchFragView, SearchFragment())
                .commit()
        }

        binding.searchNameBut.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.searchFragView, SearchFragmentName())
                .commit()
        }

        // 기본적으로 Date Fragment를 보여줌
        childFragmentManager.beginTransaction()
            .replace(R.id.searchFragView, SearchFragment())
            .commit()
    }
}

