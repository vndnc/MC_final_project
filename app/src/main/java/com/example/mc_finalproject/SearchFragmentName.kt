package com.example.mc_finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mc_finalproject.databinding.SearchFragNameBinding

class SearchFragmentName : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // 이름 검색으로 바꿀 경우
        return SearchFragNameBinding.inflate(inflater, container, false).root
    }
}