package lgl.androidstart.http;

import java.util.Map;

/**
 * Created by LGL on 2016/11/28.
 * 用于构建请求接口
 */

public interface IRequest {
    String getUrl(String host, int port, String pathSegments, Map<String, String> ParameterMap);

    String postUrl(String host, int port, String pathSegments, Map<String, String> ParameterMap, Map<String, String> extra);
}
