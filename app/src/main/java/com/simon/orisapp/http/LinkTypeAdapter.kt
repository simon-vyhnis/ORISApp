package com.simon.orisapp.http

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.simon.orisapp.model.Link

class LinkTypeAdapter : TypeAdapter<Link>() {
    override fun write(out: JsonWriter?, value: Link) {
        TODO("Not yet implemented")
    }

    override fun read(`in`: JsonReader): Link {
        var name: String? = null
        var url: String? = null

        `in`.beginObject()
        while(`in`.hasNext()) {
            var token: JsonToken = `in`.peek()
            if(token == JsonToken.NAME){
                when (`in`.nextName()){
                    "Url" -> url = `in`.nextString()
                    "OtherDescCZ" -> if(name == null){ name = `in`.nextString() }
                    "SourceType" -> name = parseSourceType(`in`)
                }
            }else{
                `in`.skipValue()
            }
        }
        `in`.endObject()

        return Link(url, name)
    }

    private fun parseSourceType(reader: JsonReader) : String? {
        var name : String? = null
        if(reader.peek() == JsonToken.BEGIN_ARRAY){
            reader.beginArray()
            reader.endArray()
        }else{
            reader.beginObject()
            while(reader.hasNext()) {
                val token: JsonToken = reader.peek()
                if (token == JsonToken.NAME) {
                    var temp = reader.nextName()
                    if (temp == "NameCZ") {
                        name = reader.nextString()
                    }else{
                        print(temp)
                    }
                } else if (token == JsonToken.END_OBJECT) {
                    reader.endObject()
                    break
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()
        }
        return name
    }
}