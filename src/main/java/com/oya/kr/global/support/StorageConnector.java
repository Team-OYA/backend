package com.oya.kr.global.support;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 김유빈
 * @since 2024.02.16
 */
public interface StorageConnector {

    String save(MultipartFile resource);

    List<String> saveAll(List<MultipartFile> resources);
}
