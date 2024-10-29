package com.example.spacify_ai

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView // Import ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class FragmentAiResult : Fragment() {

    private var imageUrl: String? = null
    private lateinit var generatedDesignImageView: ImageView // Declare ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString(ARG_IMAGE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ai_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Use findViewById to initialize the ImageView
        generatedDesignImageView = view.findViewById(R.id.generatedDesignImageView)

        // Use Glide to load the image into the ImageView
        imageUrl?.let {
            Glide.with(this)
                .load(it) // Load the image from the URL
                .into(generatedDesignImageView) // Set the ImageView
        }
    }

    companion object {
        private const val ARG_IMAGE_URL = "image_url"

        @JvmStatic
        fun newInstance(imageUrl: String) =
            FragmentAiResult().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_URL, imageUrl)
                }
            }
    }
}
