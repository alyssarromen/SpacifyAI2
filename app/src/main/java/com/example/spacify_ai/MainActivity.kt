package com.example.spacify_ai

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    private lateinit var styleSpinner: Spinner
    private lateinit var typeSpinner: Spinner
    private var selectedStyle: String? = null
    private var selectedType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_ai_input)

        // Initialize views
        imageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE

        // Initialize Spinners
        styleSpinner = findViewById(R.id.styledropdown)
        typeSpinner = findViewById(R.id.typedropdown)

        // Set up the Spinners
        setupSpinners()

        // Set up the ImageButton click listener for image selection
        findViewById<ImageButton>(R.id.imageView5).setOnClickListener {
            openImageChooser()
        }

        // Set up the generate button click listener
        findViewById<ImageButton>(R.id.generateButton).setOnClickListener {
            if (validateInputs()) {
                val roomTypeEnum = RoomType.values().firstOrNull { it.value == selectedType }
                val designStyleEnum = DesignStyle.values().firstOrNull { it.value == selectedStyle }

                val request = DesignRequest(
                    input_image_url = selectedImageUri.toString(),
                    room_type = roomTypeEnum?.name ?: "",
                    design_style = designStyleEnum?.name ?: "",
                    num_images = 1
                )

                generateDesign(
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlfa2V5X3V1aWQiOiJlYjllNWUxMi03NWI0LTQxMjAtOTFjZS0zMTYwNzJmYWE1OGEifQ.XZk7gzj13trrM5Blf3qjQRlZwjP6TBDiFABgIB_TL9s", // Replace with actual token
                    request
                )
            }
        }
    }

    private fun setupSpinners() {
        val styles = DesignStyle.values().map { it.value }
        val types = RoomType.values().map { it.value }

        val styleAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, styles)
        styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        styleSpinner.adapter = styleAdapter

        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = typeAdapter

        styleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedStyle = DesignStyle.values()[position].value
                Log.d("Spinner", "Selected style: $selectedStyle")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedType = RoomType.values()[position].value
                Log.d("Spinner", "Selected type: $selectedType")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun openImageChooser() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            imageView.setImageURI(selectedImageUri)
        }
    }

    private fun validateInputs(): Boolean {
        return when {
            selectedImageUri == null -> {
                Toast.makeText(this, "Please select an image.", Toast.LENGTH_SHORT).show()
                false
            }
            selectedStyle.isNullOrEmpty() -> {
                Toast.makeText(this, "Please select a style.", Toast.LENGTH_SHORT).show()
                false
            }
            selectedType.isNullOrEmpty() -> {
                Toast.makeText(this, "Please select a room type.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun getFilePathFromUri(uri: Uri): String? {
        var filePath: String? = null
        val cursor = contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath
    }

    private fun generateDesign(token: String, request: DesignRequest) {
        progressBar.visibility = View.VISIBLE
        val filePath = getFilePathFromUri(selectedImageUri!!)
        if (filePath != null) {
            val file = File(filePath)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val roomType = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedType ?: "")
            val designStyle = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedStyle ?: "")
            val numImages = RequestBody.create("text/plain".toMediaTypeOrNull(), "1")

            RetrofitClient.apiService.generateDesign(token, body, roomType, designStyle, numImages)
                .enqueue(object : Callback<DesignResponse> {
                    override fun onResponse(call: Call<DesignResponse>, response: Response<DesignResponse>) {
                        progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            val designResponse = response.body()
                            val designUrl = designResponse?.designUrl

                            if (designUrl != null) {
                                // Use the newInstance method to pass the design URL to FragmentAiResult
                                val fragment = FragmentAiResult.newInstance(designUrl)
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment) // Ensure fragment_container is your container ID
                                    .addToBackStack(null)
                                    .commit()
                            }
                        } else {
                            Log.e("DesignError", "Error: ${response.errorBody()?.string()}")
                            Toast.makeText(this@MainActivity, "Error generating design.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DesignResponse>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        Log.e("NetworkError", "Failed to generate design: ${t.message}")
                        Toast.makeText(this@MainActivity, "Network error occurred.", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Unable to access image file.", Toast.LENGTH_SHORT).show()
        }
    }

}
