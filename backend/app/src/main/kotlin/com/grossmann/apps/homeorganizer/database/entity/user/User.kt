package com.grossmann.apps.homeorganizer.database.entity.user

import javax.persistence.*


@Entity
@Table(name = "user", indexes = [ Index(
  name = "mail_is_unique", columnList = "email", unique = true
)])
class User(
  @Column(name = "email")
  var email: String,

  @Column(name = "firstname")
  var firstname: String?,

  @Column(name = "lastname")
  var lastname: String?
) {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0
}
