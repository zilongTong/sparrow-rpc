package org.sparrow.common;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author :Leo
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SerializeProtoStuff {

    public static <T> byte[] serialize(Class<T> clazz, T message) {
        System.out.println("SerializeProtoStuff----serialize序列化" );
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        LinkedBuffer buffer = LinkedBuffer.allocate(4096);
        byte[] protostuff = null;
        protostuff = ProtostuffIOUtil.toByteArray(message, schema, buffer);
        System.out.println(protostuff.toString());
        return protostuff;
    }

    public static <T> T deserialize(Class<T> clazz, byte[] msg) {
        System.out.println("SerializeProtoStuff----deserialize反序列化" );
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        try {
            T t = clazz.newInstance();
            ProtostuffIOUtil.mergeFrom(msg, t, schema);
            return t;
        } catch (InstantiationException e) {
            System.out.println("InstantiationException" + e.getCause() + "///" + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException" + e.getCause() + "///" + e.getMessage());
        }
        return null;
    }
}
