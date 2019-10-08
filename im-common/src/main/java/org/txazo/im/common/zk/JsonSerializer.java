package org.txazo.im.common.zk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

@Slf4j
public class JsonSerializer implements ZkSerializer {

    public byte[] serialize(Object data) throws ZkMarshallingError {
        try {
            return JSON.toJSONString(data).getBytes("UTF-8");
        } catch (Exception e) {
            log.error("serialize error", e);
        }
        return null;
    }

    public Object deserialize(byte[] bytes) throws ZkMarshallingError {
        try {
            return JSONObject.parseObject(new String(bytes, "Utf-8"));
        } catch (Exception e) {
            log.error("deserialize error", e);
        }
        return null;
    }

}
