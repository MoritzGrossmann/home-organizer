package com.grossmann.apps.homeorganizer.api.stock.barcode

import com.grossmann.apps.homeorganizer.service.barcode.BarcodeQueryResult
import com.grossmann.apps.homeorganizer.service.barcode.BarcodeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("api/stock/barcode")
@CrossOrigin(originPatterns = ["http://localhost:*" ])
class BarcodeController(
  private val barcodeService: BarcodeService
) {
  @GetMapping("query/{barcode}")
  fun queryBarcode(barcode : String) : BarcodeQueryResult {
    return barcodeService.queryBarcode(barcode).orElseThrow{
      return@orElseThrow ResponseStatusException(HttpStatus.NOT_FOUND)
    }
  }
}
