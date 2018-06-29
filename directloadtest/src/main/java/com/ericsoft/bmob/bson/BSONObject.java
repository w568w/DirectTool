package com.ericsoft.bmob.bson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BSONObject {

    protected Map<String, Object> bson;

    public BSONObject() {
        bson = new LinkedHashMap<String, Object>();
    }

    public BSONObject(String BSONString) throws BSONException {
        bson = new LinkedHashMap<String, Object>();
        BSONString bs = new BSONString();
        LinkedHashMap<String, Object> map = bs.getBSON(BSONString);
        String msg = (String) map.get(BSON.TAG_MSG);
        if (msg.equals(BSON.MSG_BSON)) {
            String tag = (String) map.get(BSON.TAG_KEY);
            String value = (String) map.get(BSON.TAG_VALUE);
            LinkedHashMap<String, Object> hm = bs.getValue(value);
            this.bson.put(tag, hm.get(BSON.TAG_VALUE));
        } else if (msg.equals(BSON.MSG_BSON_ARRAY)) {
            map.remove(BSON.TAG_MSG);
            Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();
                this.bson.put(entry.getKey(), entry.getValue());
            }
        } else if (msg.equals(BSON.MSG_BSON_EMPTY)) {
            BSON.Warn(BSON.MSG_BSON_EMPTY);
        } else if (msg.equals(BSON.MSG_ERROR_NOTBSON)) {
            throw new BSONException(msg);
        } else if (msg.equals(BSON.MSG_ERROR_EMPTY)) {
            throw new BSONException(msg);
        } else {
            throw new BSONException(BSON.MSG_ERROR_UNKNOW);
        }
    }

    public BSONObject(LinkedHashMap<String, Object> map) {
        bson = new LinkedHashMap<String, Object>();
        this.bson.putAll(map);
    }

    public BSONObject(HashMap<String, Object> map) {
        bson = new LinkedHashMap<String, Object>();
        this.bson.putAll(map);
    }

    public BSONObject(BSONObject bsonObj) {
        bson = new LinkedHashMap<String, Object>();
        this.bson.putAll(bsonObj.getMap());
    }

    public BSONObject(Date date) {
        bson = new LinkedHashMap<String, Object>();
        this.bson.put("__type", "Date");
        this.bson.put("iso", BSON.Long2DateTime(date.getTime()));
    }

    public BSONObject copy(BSONObject bsonObj) {
        bson.clear();
        this.bson.putAll(bsonObj.getMap());
        return this;
    }

    public BSONObject add(BSONObject bsonObj) {
        this.bson.putAll(bsonObj.getMap());
        return this;
    }

    public BSONObject put(String key, boolean value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, String value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, double value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, float value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, long value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, int value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, Date value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        if (value == null) {
            this.bson.remove(key);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, BSONObject value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        if (value == null) {
            this.bson.remove(key);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject put(String key, Object value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        if (value == null) {
            this.bson.remove(key);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject putDate(String key, Date value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        if (value == null) {
            this.bson.remove(key);
        }
        this.bson.put(key, value);
        return this;
    }

    public BSONObject putDate(String key, long value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.put(key, new Date(value));
        return this;
    }

    public BSONObject putPointer(String className, String objectId) {
        if (className == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        if (objectId == null) {
            this.bson.remove(className);
        }
        this.bson.put("__type", "Pointer");
        this.bson.put("className", className);
        this.bson.put("objectId", objectId);
        return this;
    }


    public BSONObject putValue(String key, String value) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        String BSONString = "{\"" + key + "\":" + value + "}";
        this.bson.putAll(new BSONObject(BSONString).getMap());
        return this;
    }

    public BSONObject remove(String key) {
        if (key == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        this.bson.remove(key);
        return this;
    }

    public Object get(String key) {
        Object obj = this.bson.get(key);
        if (obj == null) {
            throw new BSONException(BSON.MSG_ERROR_NULL_KEY);
        }
        return obj;
    }

    public Boolean getBoolean(String key) {
        Object obj = this.get(key);
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else {
            throw new BSONException("Not Boolean Type.");
        }
    }

    public String getString(String key) {
        Object obj = this.get(key);
        if (obj instanceof String) {
            return (String) obj;
        } else {
            throw new BSONException("Not String Type.");
        }
    }

    public Long getLong(String key) {
        Object obj = this.get(key);
        if (obj instanceof Long || obj instanceof Integer) {
            if (obj instanceof Integer) {
                return Long.valueOf((Integer) obj);
            }
            return (Long) obj;
        } else {
            throw new BSONException("Not Long Type.");
        }
    }

    public Double getDouble(String key) {
        Object obj = this.get(key);
        if (obj instanceof Double || obj instanceof Float) {
            return (Double) obj;
        } else {
            throw new BSONException("Not Double Type.");
        }
    }

    public Float getFloat(String key) {
        Object obj = this.get(key);
        if (obj instanceof Float) {
            return (Float) obj;
        } else {
            throw new BSONException("Not Float Type.");
        }
    }

    public int getInt(String key) {
        Object obj = this.get(key);
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else {
            throw new BSONException("Not int Type.");
        }
    }

    public Date getDate(String key) {
        Object obj = this.get(key);
        if (obj instanceof Date) {
            return (Date) obj;
        } else if (obj instanceof BSONObject) {
            BSONObject bsonObj = (BSONObject) obj;
            if ("Date".equals(bsonObj.getString(BSON.TAG_BMOB_TYPE))) {
                String date = bsonObj.getString("iso");
                return new Date(BSON.DateTime2Long(date));
            } else {
                throw new BSONException("Not Bmob Date Type.");
            }
        } else {
            throw new BSONException("Not Bmob Date Type.");
        }
    }

    public BSONObject getBSONObject(String key) {
        Object obj = this.get(key);
        if (obj instanceof BSONObject) {
            return (BSONObject) obj;
        } else {
            throw new BSONException("Not BSONObject Type.");
        }
    }

    public BSONObject[] getBSONArrayByBmobResults() {
        return this.getBSONArray("results");
    }

    public BSONObject[] getBSONArray(String key) {
        Object obj = this.get(key);
        if (obj instanceof Object[]) {
            try {
                Object[] objArray = (Object[]) obj;

                BSONObject[] bsonArray = new BSONObject[objArray.length];
                int id = 0;
                for (Object o : objArray) {
                    if (o instanceof String && o.equals(BSON.CHAR_NULL)) {
                        bsonArray[id++] = new BSONObject();
                    } else {
                        bsonArray[id++] = (BSONObject) o;
                    }
                }
                return bsonArray;
            } catch (BSONException be) {
                throw new BSONException("Not BSONArray Type.");
            }
        } else {
            throw new BSONException("Not BSONArray Type.");
        }
    }

    public int length() {
        return this.bson.size();
    }

    public boolean isEmpty() {
        return this.length() == 0;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return BSON.CHAR_LEFT_BIG + BSON.CHAR_RIGHT_BIG;
        } else {
            StringBuffer result = new StringBuffer();
            Iterator<Map.Entry<String, Object>> iter = this.bson.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();
                result.insert(0, this.entryToString(entry));
                if (iter.hasNext()) {
                    result.insert(0, BSON.CHAR_COMMA);
                }
            }
            return result.append(BSON.CHAR_RIGHT_BIG).insert(0, BSON.CHAR_LEFT_BIG).toString();
        }
    }

    public Map<String, Object> getMap() {
        return bson;
    }

    public boolean containsKey(String key) {
        return bson.containsKey(key);
    }

    public List<String> getAllKey() {
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : this.bson.entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    protected String entryToString(Map.Entry<String, Object> entry) {
        StringBuffer result = new StringBuffer();
        result.insert(0, BSON.CHAR_QUOTES + entry.getKey() + BSON.CHAR_QUOTES + BSON.CHAR_RISK);
        result.append(this.objectToString(entry.getValue()));
        return result.toString();
    }

    protected String objectToString(Object val) {
        StringBuffer result = new StringBuffer();
        if (val instanceof Integer
                || val instanceof Long
                || val instanceof Float
                || val instanceof Double
                || val instanceof Boolean) {
            result.append(val);
        } else if (val instanceof String) {
            if (!val.equals(BSON.CHAR_NULL)) {
                result.append(BSON.CHAR_QUOTES + val + BSON.CHAR_QUOTES);
            } else {
                result.append(BSON.CHAR_QUOTES + BSON.CHAR_QUOTES);
            }
        } else if (val instanceof Byte) {
            result.append(val);
        } else if (val instanceof Date) {
            //BmobDate ��ʽ
            result.append("{\"__type\":\"Date\",\"iso\":\"" + BSON.Long2DateTime(((Date) val).getTime()) + "\"}");
        } else if (val instanceof BSONObject) {
            result.append(val);
        } else if (val instanceof int[]) {
            int[] value = (int[]) val;
            result.append(BSON.CHAR_LEFT_MID);
            for (int i : value) {
                result.append(i + BSON.CHAR_COMMA);
            }
            result.replace(result.length() - 1, result.length(), BSON.CHAR_RIGHT_MID);
        } else if (val instanceof long[]) {
            long[] value = (long[]) val;
            result.append(BSON.CHAR_LEFT_MID);
            for (long l : value) {
                result.append(l + BSON.CHAR_COMMA);
            }
            result.replace(result.length() - 1, result.length(), BSON.CHAR_RIGHT_MID);
        } else if (val instanceof float[]) {
            float[] value = (float[]) val;
            result.append(BSON.CHAR_LEFT_MID);
            for (float f : value) {
                result.append(f + BSON.CHAR_COMMA);
            }
            result.replace(result.length() - 1, result.length(), BSON.CHAR_RIGHT_MID);
        } else if (val instanceof double[]) {
            double[] value = (double[]) val;
            result.append(BSON.CHAR_LEFT_MID);
            for (double d : value) {
                result.append(d + BSON.CHAR_COMMA);
            }
            result.replace(result.length() - 1, result.length(), BSON.CHAR_RIGHT_MID);
        } else if (val instanceof char[]) {
            char[] value = (char[]) val;
            result.append(BSON.CHAR_QUOTES);
            for (char d : value) {
                result.append(d + BSON.CHAR_COMMA);
            }
            result.replace(result.length() - 1, result.length(), BSON.CHAR_QUOTES);
        } else if (val instanceof Object[]) {
            Object[] value = (Object[]) val;
            result.append(BSON.CHAR_LEFT_MID);
            int size = value.length;
            for (int i = 0; i < size; i++) {
                if (i < size - 1) {
                    result.append(this.objectToString(value[i]) + BSON.CHAR_COMMA);
                } else {
                    result.append(this.objectToString(value[i]) + BSON.CHAR_RIGHT_MID);
                }
            }
        } else if (val instanceof ArrayList<?>) {
            ArrayList<?> value = (ArrayList<?>) val;
            result.append(BSON.CHAR_LEFT_MID);
            int size = value.size();
            for (int i = 0; i < size; i++) {
                Object obj = value.get(i);
                if (i < size - 1) {
                    result.append(this.objectToString(obj) + BSON.CHAR_COMMA);
                } else {
                    result.append(this.objectToString(obj) + BSON.CHAR_RIGHT_MID);
                }
            }
        } else {
            result.append(BSON.CHAR_LEFT_BIG + val + BSON.CHAR_RIGHT_BIG);
        }
        return result.toString();
    }

}