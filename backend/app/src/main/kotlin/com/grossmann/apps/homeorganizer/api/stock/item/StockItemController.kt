package com.grossmann.apps.homeorganizer.api.stock.item

import com.grossmann.apps.homeorganizer.database.entity.stock.item.ExtendedStockItemConverter
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItem
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItemStatus
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.CreateStockItemDto
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.GetStockItemDto
import com.grossmann.apps.homeorganizer.database.entity.stock.item.dto.GetStockItemWithCategoryDto
import com.grossmann.apps.homeorganizer.database.respoitory.stock.StockItemRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("api/stock/item")
@CrossOrigin(originPatterns = ["http://localhost:*" ])
class StockItemController(
  private val stockItemRepository: StockItemRepository,
  private val stockItemConverter: ExtendedStockItemConverter
  ) {

  @GetMapping
  fun get(
    @PageableDefault(size = 30) pageable: Pageable,
    @RequestParam search : String?
  ) : Page<GetStockItemWithCategoryDto> {

    return stockItemRepository.findAll(if(search != null) StockItem.Spec.nameContains(search) else null, pageable).map {
      stockItemConverter.toGetStockItemDto(it)
    }
  }

  @GetMapping("{id}")
  fun getById(@PathVariable id : Long) : GetStockItemWithCategoryDto {
    val item = stockItemRepository.findById(id)
      .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    return stockItemConverter.toGetStockItemDto(item)
  }

  @GetMapping("{id}")
  fun getById(@PathVariable id : Long) : GetStockItemWithCategoryDto {
    val item = stockItemRepository.findById(id)
      .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    return stockItemConverter.toGetStockItemDto(item)
  }

  @PostMapping
  fun create(@RequestBody createStockItemDto: CreateStockItemDto) : GetStockItemWithCategoryDto {
    return stockItemConverter.toGetStockItemDto(
      stockItemRepository.save(
        stockItemConverter.toEntity(createStockItemDto)
      )
    )
  }

  @PutMapping("{id}")
  fun update(@PathVariable id: Long, @RequestBody dto: CreateStockItemDto) : GetStockItemWithCategoryDto {
    val item = stockItemRepository.findById(id)
      .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    item.name = dto.name
    item.count = dto.count
    item.categoryId = dto.categoryId

    return stockItemConverter.toGetStockItemDto(
      stockItemRepository.save(item)
    )
  }

  @PatchMapping("{id}/opened")
  fun setOpened(@PathVariable id : Long) : GetStockItemWithCategoryDto {
    val item = stockItemRepository.findById(id).map {
      it.status = StockItemStatus.Opened
      return@map stockItemRepository.save(it)
    }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    return stockItemConverter.toGetStockItemDto(item)
  }

  @PatchMapping("{id}/almost_empty")
  fun setAlmostEmpty(@PathVariable id : Long) : GetStockItemWithCategoryDto {
    val item = stockItemRepository.findById(id).map {
      it.status = StockItemStatus.AlmostEmpty
      return@map stockItemRepository.save(it)
    }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    return stockItemConverter.toGetStockItemDto(item)
  }

  @PatchMapping("{id}/decrease")
  fun decrease(@PathVariable id : Long) : GetStockItemWithCategoryDto {
    val item = stockItemRepository.findById(id).map {

      if(it.count <= 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Count cannot be further reduced")

      it.count--
      it.status = StockItemStatus.Full
      return@map stockItemRepository.save(it)
    }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    return stockItemConverter.toGetStockItemDto(item)
  }
}
