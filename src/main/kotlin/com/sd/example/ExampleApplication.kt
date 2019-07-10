package com.sd.example

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.io.File

val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"

@SpringBootApplication
@EnableConfigurationProperties()
class ExampleApplication{


	fun populate(imageRepository: ImageRepository) = ApplicationRunner {
		var i = 0
		imageRepository.deleteAll()
		File(resourcesPath+"bicycle/train/").walk().forEach { file ->
			if(!file.isDirectory){
				val buffer = ByteArray(file.length().toInt())
				file.inputStream().read(buffer)
				val image = Image(i, "/bicycle/train", buffer, file.name)
				imageRepository.save(image)
				i+=1
			}
		}
	}
}

fun main(args: Array<String>) {
	SpringApplication.run(ExampleApplication::class.java, *args)
}

