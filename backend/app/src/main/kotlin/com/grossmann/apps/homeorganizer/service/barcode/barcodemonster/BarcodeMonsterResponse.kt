package com.grossmann.apps.homeorganizer.service.barcode.barcodemonster


import com.fasterxml.jackson.annotation.JsonProperty

data class BarcodeMonsterResponse(
    val code: String,

    @JsonProperty("class")
    val classX: String,

    val company: String?,

    val description: String,

    @JsonProperty("image_url")
    val imageUrl: String?,

    val size: String?,

    val status: String?
)
