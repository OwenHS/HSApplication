package com.hs.hshttplib;

import com.hs.hshttplib.util.StringUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class HttpParams {
    public Map<String, String> urlParams;
    public static final String SortBy = "SortBy";
    public static final String Default = "Default";
    private String order = "Default";
    public ConcurrentHashMap<String, HttpParams.FileWrapper> fileWraps;

    public HttpParams() {
        this.init();
    }

    public HttpParams(String order) {
        this.order = order;
        this.init();
    }

    public HttpParams(int i) {
        this.init(i);
    }

    public void init() {
        this.init(4);
    }

    public void init(int i) {
        if ("Default".equals(this.order)) {
            this.urlParams = new ConcurrentHashMap(8);
        } else {
            this.urlParams = new TreeMap();
        }

        this.fileWraps = new ConcurrentHashMap(i);
    }

    public boolean haveFile() {
        return this.fileWraps.size() != 0;
    }

    public void put(String key, int value) {
        this.put(key, String.valueOf(value));
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            this.urlParams.put(key, value);
        } else {
            throw new RuntimeException("key or value is NULL");
        }
    }

    public void put(String key, byte[] file) {
        this.put(key, (InputStream) (new ByteArrayInputStream(file)));
    }

    public void put(String key, File file) throws FileNotFoundException {
        this.put(key, new FileInputStream(file), file.getName());
    }

    public void put(String key, InputStream value) {
        this.put(key, value, "fileName");
    }

    public void put(String key, InputStream value, String fileName) {
        if (key != null && value != null) {
            this.fileWraps.put(key, new HttpParams.FileWrapper(value, fileName, (String) null));
        } else {
            throw new RuntimeException("key or value is NULL");
        }
    }

    public String getStringParams() {
        StringBuilder result = new StringBuilder();
        Iterator var3 = this.urlParams.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry entry = (Map.Entry) var3.next();
            if (result.length() > 0) {
                result.append("&");
            }

            if (this.fileWraps.size() != 0) {
                try {
                    result.append(URLEncoder.encode((String) entry.getKey(), "utf-8"));
                    result.append("=");
                    result.append(URLEncoder.encode((String) entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException var5) {
                    result.append((String) entry.getKey());
                    result.append("=");
                    result.append((String) entry.getValue());
                }
            }
        }

        return result.toString();
    }

    public void remove(String key) {
        if (this.urlParams.containsKey(key)) {
            this.urlParams.remove(key);
        }

        if (this.fileWraps.containsKey(key)) {
            this.fileWraps.remove(key);
        }

    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator var3 = this.urlParams.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry entry = (Map.Entry) var3.next();
            if (result.length() > 0) {
                result.append("&");
            }

            try {
                result.append(URLEncoder.encode((String) entry.getKey(), "utf-8"));
                result.append("=");
                result.append(URLEncoder.encode((String) entry.getValue(), "utf-8"));
            } catch (UnsupportedEncodingException var5) {
                result.append((String) entry.getKey());
                result.append("=");
                result.append((String) entry.getValue());
            }
        }

        return result.toString();
    }

    public String toJsonString() {
        Iterator var3 = this.urlParams.entrySet().iterator();
        JSONObject object = new JSONObject();
        try {
            while (var3.hasNext()) {
                Map.Entry entry = (Map.Entry) var3.next();
                object.put((String) entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();

    }

    public Set<Map.Entry<String, String>> entrySet() {
        return this.urlParams.entrySet();
    }

    public HttpEntity getEntity() {
        Object entity = null;
        if (!this.fileWraps.isEmpty()) {
            MultipartEntity e = new MultipartEntity();
            Iterator lastIndex = this.urlParams.entrySet().iterator();

            while (lastIndex.hasNext()) {
                Map.Entry currentIndex = (Map.Entry) lastIndex.next();
                e.addPart((String) currentIndex.getKey(), (String) currentIndex.getValue());
            }

            int var10 = 0;
            int var11 = this.fileWraps.entrySet().size() - 1;

            for (Iterator var6 = this.fileWraps.entrySet().iterator(); var6.hasNext(); ++var10) {
                Map.Entry entry = (Map.Entry) var6.next();
                HttpParams.FileWrapper file = (HttpParams.FileWrapper) entry.getValue();
                if (file.inputStream != null) {
                    boolean isLast = var10 == var11;
                    if (file.contentType != null) {
                        e.addPart((String) entry.getKey(), file.fileName, file.inputStream, file.contentType, isLast);
                    } else {
                        e.addPart((String) entry.getKey(), file.fileName, file.inputStream, isLast);
                    }
                }
            }

            entity = e;
        } else {
            try {
                entity = new UrlEncodedFormEntity(this.getParamsList(), "UTF-8");
            } catch (UnsupportedEncodingException var9) {
                var9.printStackTrace();
            }
        }

        return (HttpEntity) entity;
    }

    protected List<BasicNameValuePair> getParamsList() {
        LinkedList lparams = new LinkedList();
        Iterator var3 = this.urlParams.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry entry = (Map.Entry) var3.next();
            lparams.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
        }

        return lparams;
    }

    public static class FileWrapper {
        public InputStream inputStream;
        public String fileName;
        public String contentType;

        public FileWrapper(InputStream inputStream, String fileName, String contentType) {
            this.inputStream = inputStream;
            this.contentType = contentType;
            if (StringUtils.isEmpty(fileName)) {
                this.fileName = "nofilename";
            } else {
                this.fileName = fileName;
            }

        }
    }
}
