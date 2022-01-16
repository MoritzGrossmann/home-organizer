package com.grossmann.apps.homeorganizer.api.stock.categorie

import com.grossmann.apps.homeorganizer.database.entity.stock.category.StockCategory
import com.grossmann.apps.homeorganizer.database.entity.stock.category.StockCategoryConverter
import com.grossmann.apps.homeorganizer.database.entity.stock.category.dto.CreateStockCategoryDto
import com.grossmann.apps.homeorganizer.database.entity.stock.category.dto.GetStockCategoryDto
import com.grossmann.apps.homeorganizer.database.entity.stock.category.dto.GetStockCategoryWithItemsDto
import com.grossmann.apps.homeorganizer.database.entity.stock.item.SimpleStockItemConverter
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.CreateStockItemDto
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.GetStockItemDto
import com.grossmann.apps.homeorganizer.database.respoitory.stock.StockCategoryRepository
import com.grossmann.apps.homeorganizer.service.StockItemService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("api/stock/category")
@CrossOrigin(originPatterns = ["http://localhost:*" ])
class StockCategoryController(
  private val stockCategoryRepository: StockCategoryRepository,
  private val stockCategoryConverter: StockCategoryConverter,
  private val stockItemService: StockItemService,
  private val stockItemConverter: SimpleStockItemConverter
  ) {

  @GetMapping
  fun getStockCategories() : List<GetStockCategoryDto> {
    return stockCategoryRepository.findAll().map { stockCategoryConverter.toDto(it) }
  }

  @GetMapping("{id}")
  fun getById(@PathVariable id : Long) : GetStockCategoryWithItemsDto {
    return stockCategoryConverter.toDtoWithItems(stockCategoryRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) })
  }

  @GetMapping("{id}/items")
  fun getItems(@PathVariable id : Long, @RequestParam search : String?) : List<GetStockItemDto> {
    return stockItemService.getStockItemsFromCategory(id, search).map {
      stockItemConverter.toGetStockItemDto(it)
    }
  }

  @PostMapping
  fun createStockCategory(@RequestBody stockCategory : CreateStockCategoryDto) : GetStockCategoryDto {
    return stockCategoryConverter.toDto(stockCategoryRepository.save(stockCategoryConverter.toEntity(stockCategory)))
  }

  @PostMapping("{id}/item")
  fun createStockItem(@RequestParam id: Long, @RequestBody stockItem: CreateStockItemDto) : GetStockItemDto {
    val category = stockCategoryRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
    stockItem.categoryId = category.id
    return stockItemConverter.toGetStockItemDto(stockItemService.createStockItem(stockItemConverter.toEntity(stockItem)))
  }
}
