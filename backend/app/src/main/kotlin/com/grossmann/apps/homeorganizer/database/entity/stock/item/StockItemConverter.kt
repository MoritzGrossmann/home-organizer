package com.grossmann.apps.homeorganizer.database.entity.stock.item

import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.CreateStockItemDto

abstract class StockItemConverter {
  open fun toEntity(dto: CreateStockItemDto, baseEntity: StockItem? = null): StockItem {
    val entity = baseEntity ?: StockItem(
      dto.name,
      dto.categoryId,
      dto.count,
      dto.status,
      dto.minimumStock
    )

    if(dto.barcode != null)
      entity.addBarcode(dto.barcode!!)

    return entity
  }
}
