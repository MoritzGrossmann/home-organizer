package com.grossmann.apps.homeorganizer.database.entity.stock.category.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

open class GetStockCategoryDto(
  open var id: Long = 0,
  open var name: String? = null,
  open var description: String? = null,
  open var icon: String? = null,

  @JsonProperty("manual_order")
  open var manualOrder: Int? = null
) : Serializable


