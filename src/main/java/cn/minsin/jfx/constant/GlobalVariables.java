package cn.minsin.jfx.constant;

import cn.minsin.jfx.model.GData;
import com.alibaba.fastjson.JSON;
import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author: minton.zhang
 * @since: 2020/4/6 16:51
 */
public class GlobalVariables {

    public static double width = 500;

    public static double height = 500;

    public static String LOCAL_CACHE_DIR = System.getProperty("user.home").concat("/code-auto-creator");

    static {
        File file = new File(LOCAL_CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String LOCAL_FILE = "/LAST_CONFIG";


    public static void save(String data) {
        try {
            @Cleanup
            FileOutputStream fileOutputStream = new FileOutputStream(LOCAL_CACHE_DIR.concat(LOCAL_FILE),false);
            fileOutputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GData read() {
        try {
            File file = new File(LOCAL_CACHE_DIR.concat(LOCAL_FILE));
            if (file.exists()) {
                //读取文件
                FileInputStream fileInputStream = new FileInputStream(file);
                StringBuilder stringBuilder = new StringBuilder();
                byte[] buf = new byte[1024];
                while (fileInputStream.read(buf) != -1) {
                    stringBuilder.append(new String(buf, StandardCharsets.UTF_8));
                }
                return JSON.parseObject(stringBuilder.toString(), GData.class);
            }
        } catch (Exception ignored) {

        }
        return null;
    }
}
