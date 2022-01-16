package com.grossmann.apps.homeorganizer.database.entity.stock.item

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
object StockItemStatusConverter : AttributeConverter<StockItemStatus, Int> {
  override fun convertToDatabaseColumn(attribute: StockItemStatus?): Int? {
    return attribute?.value
  }

  override fun convertToEntityAttribute(dbData: Int?): StockItemStatus? {
    return StockItemStatus.values().find { it.value == dbData }
  }
}
