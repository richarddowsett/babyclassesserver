package modules

import model.BabyClass
import org.bson.{BsonReader, BsonWriter}
import org.bson.codecs.{Codec, DecoderContext, EncoderContext}

/**
  * Created by richarddowsett on 23/11/2016.
  */
class BabyClassCodec extends Codec[BabyClass] {
  override def decode(reader: BsonReader, decoderContext: DecoderContext): BabyClass = {
    val id = reader.readString("_id")
    val category = reader.readString("category")
    val activity = reader.readString("activity")
    val postcode = reader.readString("postcode")
    BabyClass(id, category, activity, postcode)
  }

  override def encode(writer: BsonWriter, value: BabyClass, encoderContext: EncoderContext): Unit = {
    writer.writeString("category", value.category)
    writer.writeString("activity", value.activity)
    writer.writeString("postcode", value.postcode)
    writer.writeString("_id", value._id)
  }

  override def getEncoderClass: Class[BabyClass] = classOf[BabyClass]
}
