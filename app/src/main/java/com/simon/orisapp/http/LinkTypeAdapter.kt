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

        while(`in`.hasNext()) {
            var token: JsonToken = `in`.peek()
            if(token == JsonToken.NAME){
                when (`in`.nextName()){
                    "Url" -> url = `in`.nextString()
                    "OtherDescCZ" -> name.let { name = `in`.nextString() }
                    "SourceType" -> parseSourceType(`in`)
                }
            }else{
                `in`.skipValue()
            }
        }

        return Link(url, name)
    }

    private fun parseSourceType(reader: JsonReader) : String? {
        var name : String? = null
        if(reader.peek() == JsonToken.BEGIN_ARRAY){
            reader.beginArray()
            reader.endArray()
        }else{
            while(reader.hasNext()) {
                var token: JsonToken = reader.peek()
                if (token == JsonToken.NAME) {
                    if (reader.nextName() === "NameCZ") {
                        name = reader.nextString()
                    }
                } else if (token == JsonToken.END_OBJECT) {
                    reader.endObject()
                    break
                } else {
                    reader.skipValue()
                }
            }
        }
        return name
    }
}