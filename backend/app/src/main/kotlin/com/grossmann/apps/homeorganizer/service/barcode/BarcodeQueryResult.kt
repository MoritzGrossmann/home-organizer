package com.grossmann.apps.homeorganizer.service.barcode

import com.fasterxml.jackson.annotation.JsonProperty

data class BarcodeQueryResult(
  @JsonProperty("product_name")
  val productName: String,

  val id : Long? = null,

  @JsonProperty("fail_reason")
  val failReason: String? = null
)
