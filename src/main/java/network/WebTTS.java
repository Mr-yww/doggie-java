package network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class WebTTS {
    private static final String WEBTTS_URL = "http://api.xfyun.cn/v1/service/v1/tts";
    private static final String APPID = "5cc263fe";
    private static final String API_KEY = "e0737049d51bf4450464d38cb536269b";
    private static final String AUE = "lame";
    private static final String AUF = "audio/L16;rate=16000";
    private static final String SPEED = "50";
    private static final String VOLUME = "50";
    private static final String PITCH = "50";
    private static final String VOICE_NAME = "xiaoyan";
    private static final String ENGINE_TYPE = "intp65";
    private static final String TEXT_TYPE = "text";
    private static final String filePath = "/root/xfAudio/";

    public WebTTS() {
    }

    public static String createAudio(String text) throws IOException {
        Map<String, String> header = buildHttpHeader();
        Map<String, Object> resultMap = HttpUtil.doPost2("http://api.xfyun.cn/v1/service/v1/tts", header, "text=" + URLEncoder.encode(text, "utf-8"));
        System.out.println("占用内存大小： " + URLEncoder.encode(text, "utf-8").getBytes().length);
        if ("audio/mpeg".equals(resultMap.get("Content-Type"))) {
            String audioName = "";
            if ("raw".equals("lame")) {
                FileUtil.save("/root/xfAudio/", resultMap.get("sid") + ".wav", (byte[])resultMap.get("body"));
                audioName = resultMap.get("sid") + ".wav";
                System.out.println("合成 WebAPI 调用成功，音频保存位置：/root/xfAudio/" + resultMap.get("sid") + ".wav");
            } else {
                FileUtil.save("/root/xfAudio/", resultMap.get("sid") + ".mp3", (byte[])resultMap.get("body"));
                audioName = resultMap.get("sid") + ".mp3";
                System.out.println("合成 WebAPI 调用成功，音频保存位置：/root/xfAudio/" + resultMap.get("sid") + ".mp3");
            }

            return audioName;
        } else {
            System.out.println("合成 WebAPI 调用失败，错误信息：" + resultMap.get("body").toString());
            return null;
        }
    }

    private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
        throw new Error("Unresolved compilation problems: \n\tBase64 cannot be resolved\n\tDigestUtils cannot be resolved\n");
    }
}
