package com.surf2024.learningcustomviews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.surf2024.learningcustomviews.R
import com.surf2024.learningcustomviews.features.AvatarsLineView

class CircularAvatarsLineFragmentView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_circular_avatars_line_view, container, false)

        return view
    }

}