package com.laptrinhoop.agent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class AgentLogInterceptor implements Interceptor {
    private static final long MAX_BUFF_SIZE = 1024L * 1024;
    private String agentName;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String requestId = UUID.randomUUID().toString();
        log.info("{} {} [REQ] {} {} {} BODY: {}", agentName, requestId,
                request.method(), request.url(), request.headers(), stringifyRequestBody(request));
        Response response = chain.proceed(request);
        log.info("{} {} [RES] {} MESSAGE: {}", agentName, requestId,
                response.code(), response.message());
        return response;
    }

    private static String stringifyRequestBody(final Request request) {
        Request copy = request.newBuilder().build();
        try (Buffer buff = new Buffer()) {
            if (copy.body().contentLength() > MAX_BUFF_SIZE) {
                return ">>> OVER 1MB <<<";
            }
            copy.body().writeTo(buff);
            return buff.readUtf8();
        } catch (Exception e) {
            return "";
        }
    }
}
