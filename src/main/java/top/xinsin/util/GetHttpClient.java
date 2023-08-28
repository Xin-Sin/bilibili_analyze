package top.xinsin.util;

import okhttp3.OkHttpClient;

/**
 * @author xinsin
 * Created On 2023/8/26 10:58
 * @version 1.0
 */
public class GetHttpClient {
    private volatile static OkHttpClient singleton;
    private GetHttpClient() {
    }
    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (GetHttpClient.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient();
                }
            }
        }
        return singleton;
    }
}
