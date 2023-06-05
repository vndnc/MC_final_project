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
        binding = SearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchDateBut.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(binding.searchFragView.id, SearchFragmentDate())
                .commit()
        }

        binding.searchNameBut.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(binding.searchFragView.id, SearchFragmentName())
                .commit()
        }

        // 기본적으로 Date Fragment를 보여줌
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.searchFragView, SearchFragmentDate())
                .commit()
        }
    }
}

