package com.surf2024.learningcustomviews.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.surf2024.learningcustomviews.R
import com.surf2024.learningcustomviews.features.avatars_line_view.AvatarsLineView
import com.surf2024.learningcustomviews.features.avatars_line_view.AvatarView

class CircularAvatarsLineFragmentView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_circular_avatars_line_view, container, false)

        val circularImageView = view.findViewById<AvatarsLineView>(R.id.custom_image_card_line)
        val imageUrls = listOf(
            "https://img.freepik.com/free-photo/rainbow-at-the-end-of-a-road-landscape_23-2151596720.jpg?w=740&t=st=1727445059~exp=1727445659~hmac=f16f7d2f269809f57a6b63637f740272115eabdb3f0a0dfa07a9789b6bc3cfc5",
            "https://img.freepik.com/free-photo/abstract-autumn-beauty-in-multi-colored-leaf-vein-pattern-generated-by-ai_188544-9871.jpg?w=1380&t=st=1727445083~exp=1727445683~hmac=3b9dd6cd33fb5d9241c81670b5c7c38533c8ccdd1b76d72837a419f21143b7e0",
            "https://img.freepik.com/free-photo/butterfly-on-blossom_23-2150636355.jpg?t=st=1727445044~exp=1727445644~hmac=345696d28745de15627081c0eeebec7ae193e5178bb4fc6267ed2d2aed0fa493",
            "https://trikky.ru/wp-content/blogs.dir/1/files/2023/03/23/zyro-image-11.jpg",
            "https://trikky.ru/wp-content/blogs.dir/1/files/2023/03/23/zyro-image-11.jpg",
            "https://trikky.ru/wp-content/blogs.dir/1/files/2023/03/23/zyro-image-11.jpg",
        )
        circularImageView.setImages(imageUrls)
        return view
    }

}