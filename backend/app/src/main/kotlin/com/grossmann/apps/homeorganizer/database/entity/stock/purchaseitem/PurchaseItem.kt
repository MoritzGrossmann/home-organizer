package com.grossmann.apps.homeorganizer.database.entity.stock.purchaseitem

import com.grossmann.apps.homeorganizer.database.entity.stock.item.StockItem
import com.grossmann.apps.homeorganizer.database.entity.stock.purchase.Purchase
import javax.persistence.*

@Entity
@Table(name = "purchaseitem")
class PurchaseITem (
  @Column(name = "purchase")
  var purchaseId : Long?,

  @Column(name = "stockitem")
  var stockItemId : Long?,

  @Column(name = "amount")
  var count : Int = 0
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "purchase", insertable = false, updatable = false)
  var purchase: Purchase? = null

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "stockitem", insertable = false, updatable = false)
  var stockItem: StockItem? = null

  object Spec {

  }
}
