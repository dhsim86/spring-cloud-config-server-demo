package com.dongho.demo.springcloudconfigserverdemo.domain.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigService {

	private ObjectMapper jsonMapper;
	private ObjectMapper yamlMapper;

	@PostConstruct
	public void init() {
		this.jsonMapper = new ObjectMapper();

		this.yamlMapper = new ObjectMapper(new YAMLFactory());
		this.yamlMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
	}

	public String loadConfigAsString(String path) {
		File file = null;

		try {
			file = ResourceUtils.getFile(path);
		} catch (FileNotFoundException e) {
			log.error("Not found configuration file. path: {}", path, e);
			return "";
		}

		String config = "";

		try (InputStream in = new FileInputStream(file)) {
			config = new String(in.readAllBytes(), StandardCharsets.UTF_8);

		} catch (FileNotFoundException e) {
			log.error("Not found configuration file. path: {}", file.getPath(), e);
		} catch (IOException e) {
			log.error("IO Error. path: {}", path, e);
		}

		try {
			Object obj = yamlMapper.readValue(config, Object.class);
			return jsonMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("Mapping Error. path: {}", path, e);
		}

		return "";
	}

}
