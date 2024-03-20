package com.example.moneymarketshoprite.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Table
import org.springframework.data.annotation.Id

@Entity
@Table(name = "user", schema = "kmm", catalog = "moneymarket")
data class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, insertable = false, updatable = false)
        var id: Long? = null,
        @Column(name = "user_name", nullable = false)
        var userName: Long,
        @Column(name = "password", nullable = false)
        var password: String,
)