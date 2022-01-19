package com.grossmann.apps.homeorganizer.service.barcode

import org.springframework.stereotype.Service
import java.util.*


@Service
interface BarcodeInformationService {

  fun queryBarcode(barcode : String) : Optional<BarcodeQueryResult>

}
