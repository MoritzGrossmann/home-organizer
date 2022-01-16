package com.grossmann.apps.homeorganizer.database.respoitory.stock

import com.grossmann.apps.homeorganizer.database.entity.stock.category.StockCategory
import org.springframework.data.jpa.repository.JpaRepository

interface StockCategoryRepository : JpaRepository<StockCategory, Long>
