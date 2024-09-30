package com.surf2024.learningcustomviews.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.surf2024.learningcustomviews.R
import com.surf2024.learningcustomviews.features.landscape.LandscapeDrawable

class LandscapeDrawableFragmentView : Fragment() {

    private lateinit var landscapeDrawable: LandscapeDrawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landscape_drawable_view, container, false)

        landscapeDrawable = LandscapeDrawable(
            starCount = 20,
            starSize = 15f,
            starColor = Color.WHITE,
            cloudCount = 20,
            cloudSize = 300f,
            cloudColor = Color.LTGRAY,
            windStrength = 50.0f,
            maxWind = 100.0f,
            sunSize = 40f,
            sunColor = Color.YELLOW,
            skyColor = Color.DKGRAY,
            skyHeight = 750f,
            fogColor = Color.argb(100, 255, 255, 255),
            landscapeColor = Color.GREEN,
            planesCount = 5,
            landscapeHeight = 700f,
            treeHeight = 200f
        )

        view.background = landscapeDrawable

        return view
    }

}