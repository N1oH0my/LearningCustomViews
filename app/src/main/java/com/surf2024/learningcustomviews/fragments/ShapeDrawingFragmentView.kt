package com.surf2024.learningcustomviews.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.surf2024.learningcustomviews.R
import com.surf2024.learningcustomviews.features.ShapeDrawingView

class ShapeDrawingFragmentView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shape_drawing_view, container, false)
        val shapeDrawingView = view.findViewById<ShapeDrawingView>(R.id.shapeDrawingView)
        shapeDrawingView.setColors(listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW))
        return view
    }

}