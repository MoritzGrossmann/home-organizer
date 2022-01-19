package com.grossmann.apps.homeorganizer.service.barcode.barcodemonster

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.grossmann.apps.homeorganizer.service.barcode.BarcodeInformationService
import com.grossmann.apps.homeorganizer.service.barcode.BarcodeQueryResult
import org.springframework.http.HttpStatus
import java.util.*

class BarcodeMonsterInformationService : BarcodeInformationService {

  val barcodeMonsterApiUrl = "https://barcode.monster/api/"

  override fun queryBarcode(barcode: String): Optional<BarcodeQueryResult> {
    val (_,_,result) = (barcodeMonsterApiUrl + barcode)
      .httpGet()
      .responseString()

    return when (result) {
      is Result.Failure -> {
        val ex = result.getException()
        if(ex.response.statusCode == HttpStatus.NOT_FOUND.value())
          Optional.empty<BarcodeQueryResult>()
        else
          throw ex
      }
      is Result.Success -> {
        val response = ObjectMapper().readValue(result.get(),BarcodeMonsterResponse::class.java)
        return Optional.of(BarcodeQueryResult(response.description))
      }
    }
  }
}
