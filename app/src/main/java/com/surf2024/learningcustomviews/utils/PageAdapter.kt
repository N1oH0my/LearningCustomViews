package com.surf2024.learningcustomviews.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.surf2024.learningcustomviews.fragments.LandscapeDrawableFragmentView
import com.surf2024.learningcustomviews.fragments.ShapeDrawingFragmentView

class PageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShapeDrawingFragmentView()
            1 -> LandscapeDrawableFragmentView()
            else -> ShapeDrawingFragmentView()
        }
    }
}
