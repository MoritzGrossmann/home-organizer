package com.grossmann.apps.homeorganizer.database.entity.stock.category

import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItem
import javax.persistence.*

@Entity
@Table(name = "stockcategory", indexes = [
  Index(columnList = "name")
])
class StockCategory(
  var name: String?,
  val icon: String?,
  val description: String?,

  @Column(name = "manual_order")
  val manualOrder: Int? = null,

  @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "category")
  val items : List<StockItem> = listOf()
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0
}
