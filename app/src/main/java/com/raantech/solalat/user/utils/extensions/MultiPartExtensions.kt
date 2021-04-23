package com.raantech.solalat.user.utils.extensions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun String.createImageMultipart(name: String): MultipartBody.Part {
    val file = File(this)
    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
    return MultipartBody.Part.createFormData(name, file.name, requestFile)
}

fun List<String>.createImageMultipart(name: String): ArrayList<MultipartBody.Part> {
    val images = ArrayList<MultipartBody.Part>()
    forEach {
        val file = File(it)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        images.add(MultipartBody.Part.createFormData(name, file.name, requestFile))
    }

    return images
}

fun String.createAudioMultipart(name: String): MultipartBody.Part {
    val file = File(this)
    val requestFile = RequestBody.create("audio/*".toMediaTypeOrNull(), file)
    return MultipartBody.Part.createFormData(name, file.name, requestFile)
}

fun String.getRequestBody(): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
}