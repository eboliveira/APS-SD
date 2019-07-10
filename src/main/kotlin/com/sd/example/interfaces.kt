package com.sd.example

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param

interface ImageRepository: MongoRepository<Image, Long>{
    fun findByPath(@Param("path")path:String):List<Image>
    fun findAllByPath(@Param("path") path: String):List<Image>
    fun findFirstByOrderByIdDesc():Image
}