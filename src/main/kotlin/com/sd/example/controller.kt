package com.sd.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.io.Serializable
import javax.servlet.http.HttpServletRequest
import kotlin.collections.ArrayList

@RestController
@RequestMapping("images", method=[RequestMethod.GET, RequestMethod.POST])
class ImageController() {

    @Autowired
    lateinit var imageRepository:ImageRepository


    @RequestMapping(value= ["/bicycle"], method =[ RequestMethod.GET])
    fun bycicles(@RequestParam(value="page") page:Int, request:HttpServletRequest): ArrayList<Map<String, Serializable>> {
        println("Requisição recebida IP:" + request.remoteAddr)
        val images =  imageRepository.findByPath("/bicycle/train")
        val imageList = ArrayList<Map<String, Serializable>>()
        var iterator = page*10
        when {
            iterator >= images.size -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found")
            iterator+10 >= images.size -> for(i in iterator until images.size){
                imageList.add(mapOf("name" to images[i].name, "data" to images[i].data ))
            }
            else -> for(i in iterator until iterator+10){
                imageList.add(mapOf("name" to images[i].name, "data" to images[i].data ))
            }
        }
        return imageList
    }

    @RequestMapping(value=["/bycicle"], method = [RequestMethod.POST])
    fun receiveImage(@RequestBody image: Image, @RequestParam(value="type") type:String) {
        val lenDB = imageRepository.findAllByPath("requests/bycicle/$type").size
        if(lenDB > 100){
            throw ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Database is full")
        }else{
            println("Imagem recebida")
            println(image)
            if(type != "train" && type != ("database")){
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Type should be train or database")
            }
            image.path = "requests/bycicle/$type"
            image.id = imageRepository.findFirstByOrderByIdDesc().id!! + 1
            imageRepository.save(image)
        }
    }


    @PostMapping
    fun add(@RequestBody image: Image) {
        imageRepository.save(image)
    }
}