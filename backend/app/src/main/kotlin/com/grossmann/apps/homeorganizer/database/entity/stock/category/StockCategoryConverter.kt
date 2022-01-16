package com.grossmann.apps.homeorganizer.database.entity.stock.category

import com.grossmann.apps.homeorganizer.database.entity.stock.category.dto.CreateStockCategoryDto
import com.grossmann.apps.homeorganizer.database.entity.stock.category.dto.GetStockCategoryDto
import com.grossmann.apps.homeorganizer.database.entity.stock.category.dto.GetStockCategoryWithItemsDto
import com.grossmann.apps.homeorganizer.database.entity.stock.item.SimpleStockItemConverter
import org.springframework.stereotype.Component

@Component
class StockCategoryConverter(private val stockItemConverter: SimpleStockItemConverter) {

  fun toDto(entity: StockCategory) = GetStockCategoryDto().apply {
    id = entity.id
    name = entity.name
    description = entity.description
    icon = entity.icon
    manualOrder = entity.manualOrder
  }

  fun toDtoWithItems(entity: StockCategory) = GetStockCategoryWithItemsDto().apply {
    id = entity.id
    name = entity.name
    description = entity.description
    icon = entity.icon
    manualOrder = entity.manualOrder
    items = entity.items.map { stockItemConverter.toGetStockItemDto(it) }
  }

  fun toEntity(dto: CreateStockCategoryDto, baseEntity: StockCategory? = null): StockCategory {
    return baseEntity ?: StockCategory(
      dto.name,
      dto.description,
      dto.icon
    )
  }
}
