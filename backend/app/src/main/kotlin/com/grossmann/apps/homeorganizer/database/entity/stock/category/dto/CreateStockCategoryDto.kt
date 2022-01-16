package com.grossmann.apps.homeorganizer.database.entity.stock.category.dto

import java.io.Serializable

data class CreateStockCategoryDto(
  val name: String? = null,
  val description: String? = null,
  val icon: String? = null
) : Serializable
