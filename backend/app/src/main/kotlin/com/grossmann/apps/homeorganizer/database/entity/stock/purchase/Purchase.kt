package com.grossmann.apps.homeorganizer.database.entity.stock.purchase

import com.grossmann.apps.homeorganizer.database.entity.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "purchase")
class Purchase (
  @Column(name = "user")
  var userId : Long,

  var date : LocalDateTime
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "user", insertable = false, updatable = false)
  var user: User? = null

  object Spec {

  }
}
