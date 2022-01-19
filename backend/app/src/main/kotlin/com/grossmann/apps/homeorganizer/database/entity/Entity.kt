package com.grossmann.apps.homeorganizer.database.entity

import com.grossmann.apps.homeorganizer.database.entity.user.User
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.TemporalType

abstract class Entity {



  @CreatedBy
  lateinit var createdBy : User



  @LastModifiedBy
  lateinit var lastModifiedBy : User
}
