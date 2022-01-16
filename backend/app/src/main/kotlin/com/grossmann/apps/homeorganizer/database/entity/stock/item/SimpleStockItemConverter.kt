package com.grossmann.apps.homeorganizer.database.entity.stock.item

import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.GetStockItemDto
import org.springframework.stereotype.Component

@Component
class SimpleStockItemConverter : StockItemConverter() {

  fun toGetStockItemDto(entity: StockItem) = GetStockItemDto().apply {
    id = entity.id
    name = entity.name
    categoryId = entity.categoryId
    count = entity.count
    status = entity.status
    minimumStock = entity.minimumStock
  }
}
