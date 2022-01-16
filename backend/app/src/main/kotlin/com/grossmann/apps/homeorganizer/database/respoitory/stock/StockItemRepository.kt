package com.grossmann.apps.homeorganizer.database.respoitory.stock

import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface StockItemRepository : JpaRepository<StockItem, Long>, JpaSpecificationExecutor<StockItem>
