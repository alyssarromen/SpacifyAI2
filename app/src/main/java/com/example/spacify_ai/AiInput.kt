package com.example.spacify_ai;
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.spacify_ai.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AiInput : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var spinnerStyle: Spinner
    private lateinit var spinnerType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ai_input, container, false)

        // Initialize the Spinners
        spinnerStyle = view.findViewById(R.id.styledropdown)
        spinnerType = view.findViewById(R.id.typedropdown)

        // Create a list of items for the spinner
        val styleOptions = arrayOf("Modern", "Traditional", "Minimalist")
        val roomTypeOptions = arrayOf("Living Room", "Bedroom", "Kitchen")

        // Create an ArrayAdapter for style spinner
        val styleAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, styleOptions)
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStyle.adapter = styleAdapter

        // Create an ArrayAdapter for room type spinner
        val roomTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roomTypeOptions)
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = roomTypeAdapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AiInput().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
