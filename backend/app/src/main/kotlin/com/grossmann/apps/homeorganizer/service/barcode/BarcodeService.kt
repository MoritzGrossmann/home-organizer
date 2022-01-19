package com.grossmann.apps.homeorganizer.service.barcode

import com.grossmann.apps.homeorganizer.service.barcode.barcodemonster.BarcodeMonsterInformationService
import com.grossmann.apps.homeorganizer.service.barcode.local.LocalBarcodeInformationService
import org.springframework.stereotype.Service
import java.util.*

@Service
class BarcodeService(
  private val localBarcodeInformationService: LocalBarcodeInformationService,
  private val barcodeMonsterInformationService: BarcodeMonsterInformationService,
){

  fun queryBarcode(barcode : String) : Optional<BarcodeQueryResult> {
    val result = localBarcodeInformationService.queryBarcode(barcode)
    return if(result.isEmpty) barcodeMonsterInformationService.queryBarcode(barcode)
    else result
  }
}
