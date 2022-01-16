package com.grossmann.apps.homeorganizer.database.entity.stock.category.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.GetStockItemDto

data class GetStockCategoryWithItemsDto(
  override var id: Long = 0,
  override var name: String? = null,
  override var description: String? = null,
  override var icon: String? = null,

  @JsonProperty("manual_order")
  override var manualOrder: Int? = null,
  var items: List<GetStockItemDto> = listOf()
) : GetStockCategoryDto(id, name, description, icon, manualOrder)


