package com.grossmann.apps.homeorganizer.database.entity.stock.item

import com.fasterxml.jackson.annotation.JsonValue

enum class StockItemStatus(@JsonValue val value: Int) {
  Full(0),
  Opened(1),
  AlmostEmpty(2)
}
