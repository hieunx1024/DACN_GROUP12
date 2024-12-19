package com.laptrinhoop.config;

import com.laptrinhoop.agent.AgentLogInterceptor;
import com.laptrinhoop.agent.SendGripAgent;
import com.laptrinhoop.constants.Constant;
import com.laptrinhoop.converter.Jksonizer;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;
@Configuration
public class RetrofitConfig {
    @Value("${service-discovery.sendgrip.base-url}")
    private String sendgripUrl;

    @Bean
    @Qualifier("sendgrip")
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new AgentLogInterceptor("SENDGRIP"))
                .readTimeout(Constant.Timeout.RETROFIT_IN_MINUTE, TimeUnit.MINUTES).
                        build();

    }

    @Bean
    @Qualifier("sendgrip")
    Retrofit providesSendGripHttpClient(@Qualifier("sendgrip") OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(this.sendgripUrl)
                .addConverterFactory(Jksonizer.getConverterFactory())
                .client(httpClient)
                .build();
    }

    @Bean
    SendGripAgent provideSendGripAgent(@Qualifier("sendgrip") Retrofit retrofit) {
        return retrofit.create(SendGripAgent.class);
    }

}
