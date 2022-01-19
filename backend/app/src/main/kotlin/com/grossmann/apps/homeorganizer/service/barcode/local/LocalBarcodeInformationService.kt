package com.grossmann.apps.homeorganizer.service.barcode.local

import com.grossmann.apps.homeorganizer.service.StockItemService
import com.grossmann.apps.homeorganizer.service.barcode.BarcodeInformationService
import com.grossmann.apps.homeorganizer.service.barcode.BarcodeQueryResult
import org.springframework.stereotype.Service
import java.util.*

@Service
class LocalBarcodeInformationService(
  private val stockItemService: StockItemService
) : BarcodeInformationService {
  override fun queryBarcode(barcode: String): Optional<BarcodeQueryResult> {
    return stockItemService.getStockItemFromBarcode(barcode).map {
      BarcodeQueryResult(it.name, it.id)
    }
  }
}
