package com.grossmann.apps.homeorganizer.service

import com.grossmann.apps.homeorganizer.database.entity.stock.Barcode.Barcode
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItem
import com.grossmann.apps.homeorganizer.database.respoitory.stock.BarcodeRepository
import com.grossmann.apps.homeorganizer.database.respoitory.stock.StockItemRepository
import org.springframework.stereotype.Service


@Service
class StockItemService(
  private val stockItemRepository: StockItemRepository,
  private val barcodeRepository: BarcodeRepository
  ) {
  fun createStockItem(stockItem: StockItem) : StockItem {
    return stockItemRepository.save(stockItem)
  }

  fun getStockItemsFromCategory(category: Long, search : String? = null) : List<StockItem> {
    return stockItemRepository.findAll(StockItem.Spec.isCategory(category).and(if(search != null) StockItem.Spec.nameContains(search) else null))
  }

  fun getStockItemFromBarcode(barcode: String) : StockItem? {
    return barcodeRepository.findOne(Barcode.Spec.barcodeIs(barcode)).map<StockItem?> { it.stockItem }.orElse(null)
  }
}
