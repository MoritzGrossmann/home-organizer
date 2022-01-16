package com.grossmann.apps.homeorganizer.database.entity.stock.item.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItemStatus
import java.io.Serializable

open class GetStockItemDto(
  open var id: Long = 0,

  open var name: String? = null,

  @JsonProperty("category_id")
  open var categoryId: Long? = null,

  open var count: Int = 0,

  open var status : StockItemStatus = StockItemStatus.Full,

  @JsonProperty("minimum_stock")
  open var minimumStock: Int? = null
) : Serializable
