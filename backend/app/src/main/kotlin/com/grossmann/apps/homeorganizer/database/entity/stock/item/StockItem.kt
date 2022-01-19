package com.grossmann.apps.homeorganizer.database.entity.stock.item

import com.grossmann.apps.homeorganizer.database.entity.stock.Barcode.Barcode
import com.grossmann.apps.homeorganizer.database.entity.stock.category.StockCategory
import org.springframework.data.jpa.domain.Specification
import javax.persistence.*

@Entity
@Table(name = "stockitem", indexes = [
  Index(columnList = "name")
])
class StockItem(
  var name: String?,

  @Column(name = "category")
  var categoryId: Long?,

  var count : Int = 0,

  var status : StockItemStatus = StockItemStatus.Full,

  @Column(name = "minimum_stock")
  var minimumStock: Int? = null
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "category", insertable = false, updatable = false)
  var category: StockCategory? = null

  @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "stockitem")
  val barcodes : MutableList<Barcode> = mutableListOf()

  fun addBarcode(barcode : String) {
    this.barcodes.add(Barcode(barcode))
  }

  fun addBarcode(barcode : Barcode) {
    this.barcodes.add(barcode)
  }

  object Spec {
    fun nameContains(name: String) : Specification<StockItem> {
      return Specification { root, _, criteriaBuilder ->
        criteriaBuilder.like(root.get("name"), "%$name%")
      }
    }

    fun isCategory(category: Long) : Specification<StockItem> {
      return Specification { root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Long>("category"), category)
      }
    }
  }
}
