package com.grossmann.apps.homeorganizer.database.entity.stock.Barcode

import com.grossmann.apps.homeorganizer.database.entity.stock.category.StockCategory
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItem
import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItemStatus
import org.springframework.data.jpa.domain.Specification
import javax.persistence.*

@Entity
@Table(name = "barcode", indexes = [
  Index(columnList = "barcode", name="narcode_index", unique = true)
])
class Barcode(

  @Column(length = 15)
  var barcode: String?,

  @Column(name = "stockitem")
  var stockItemId: Long?,
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "stockitem", insertable = false, updatable = false)
  var stockItem: StockItem? = null

  object Spec {
    fun barcodeIs(barcode: String) : Specification<Barcode> {
      return Specification { root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<String>("barcode"), barcode)
      }
    }
  }
}
