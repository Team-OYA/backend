package com.oya.kr.global.support;

public interface ChatGPTProvider {

    String getUrl();

    String getSecretKey();

    String getModel();

    String getRole();
}
