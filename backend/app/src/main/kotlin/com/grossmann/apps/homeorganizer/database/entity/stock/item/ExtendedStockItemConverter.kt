package com.grossmann.apps.homeorganizer.database.entity.stock.item

import com.grossmann.apps.homeorganizer.database.entity.stock.category.StockCategoryConverter
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.CreateStockItemDto
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.GetStockItemWithCategoryDto
import org.springframework.stereotype.Component

@Component
class ExtendedStockItemConverter(private val stockCategoryConverter: StockCategoryConverter) : StockItemConverter() {
    fun toGetStockItemDto(entity: StockItem) = GetStockItemWithCategoryDto().apply {
    id = entity.id
    name = entity.name
    categoryId = entity.categoryId
    category = if(entity.category != null) stockCategoryConverter.toDto(entity.category!!) else null
    count = entity.count
    status = entity.status
    minimumStock = entity.minimumStock
  }
}
