package com.example.movies2app.data.local.roomDb

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.jar.Attributes

@TypeConverters
class TypeConvertor {
@TypeConverter
fun fromAnyToString(attributes: Any?):String{
    if(attributes==null){
        return ""
    }
    else{
        return attributes as String
    }
}

    @TypeConverter
    fun fromStringToAny(attributes:String?):Any{
        if(attributes==null){
            return ""
        }
        else{
            return attributes
        }
    }
}


@TypeConverters
class IntTypeConverter {
    @TypeConverter
    fun saveIntList(list: List<Int>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun getIntList(list: List<Int>): List<Int> {
        return Gson().fromJson(
            list.toString(),
            object : TypeToken<List<Int?>?>() {}.type
        )
    }

    @TypeConverter
    fun getIntList(list: String?): List<Int> {
        return Gson().fromJson(
            list,
            object : TypeToken<List<Int>>() {}.type
        )
    }
}

class TheTypeConverters {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String = intList.toString()

    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val result = ArrayList<Int>()
        val split = stringList.replace("[", "").replace("]", "").replace(" ", "").split(",")
        for (n in split) {
            try {
                result.add(n.toInt())
            } catch (e: Exception) {

            }
        }
        return result
    }
}