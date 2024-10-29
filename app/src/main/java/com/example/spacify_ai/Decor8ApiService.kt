package com.example.spacify_ai

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Decor8ApiService {
    @Multipart
    @POST("/generate_designs_for_room")
    fun generateDesign(
        @Header("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlfa2V5X3V1aWQiOiJlYjllNWUxMi03NWI0LTQxMjAtOTFjZS0zMTYwNzJmYWE1OGEiLCJpYXQiOjE3MzAyMzMwMDl9.c-1Wttdlg4Ca9y4smEquB4zHD4XdaiOCx0H7wustQpc") token: String,
        @Part image: MultipartBody.Part,
        @Part("room_type") roomType: RequestBody,
        @Part("design_style") designStyle: RequestBody,
        @Part("num_images") numImages: RequestBody
    ): Call<DesignResponse>
}
