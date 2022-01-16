package com.grossmann.apps.homeorganizer.database.entity.stock.item.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItemStatus
import java.io.Serializable

data class CreateStockItemDto(
  var name: String? = null,

  @JsonProperty("category_id")
  var categoryId: Long,

  var count: Int = 0,

  var status : StockItemStatus = StockItemStatus.Full,

  @JsonProperty("minumum_stock")
  var minimumStock: Int? = null

) : Serializable
