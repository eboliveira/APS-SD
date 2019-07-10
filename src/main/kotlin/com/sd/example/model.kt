package com.sd.example

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import javax.persistence.*

@Entity
@JsonSerialize
data class Image(
        @Id
        var id: Int?,

        @Column (name="path")
        var path: String?,

        @Column(name="data", nullable = false, columnDefinition = "BLOB")
        @Lob
        val data: ByteArray,

        @Column(name="name")
        val name:String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false
        if (path != other.path) return false
        if (!data.contentEquals(other.data)) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result!! + path!!.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}