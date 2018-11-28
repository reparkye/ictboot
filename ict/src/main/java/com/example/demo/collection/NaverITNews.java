package com.example.demo.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("naverit")
public class NaverITNews {

	private String uri;
	private String tittle;
	private String aid;
	
}
