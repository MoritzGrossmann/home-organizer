package com.grossmann.apps.homeorganizer.database.entity.stock.item.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.grossmann.apps.homeorganizer.database.entity.stock.category.dto.GetStockCategoryDto
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItemStatus

data class GetStockItemWithCategoryDto(
  override var id: Long = 0,
  override var name: String? = null,

  @JsonProperty("category_id")
  override var categoryId: Long? = null,
  override var count: Int = 0,
  override var status : StockItemStatus = StockItemStatus.Full,

  @JsonProperty("minimum_stock")
  override var minimumStock: Int? = null,
  var category: GetStockCategoryDto? = null
) : GetStockItemDto(id, name, categoryId, count, status, minimumStock)
