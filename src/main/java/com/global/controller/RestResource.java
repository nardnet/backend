package com.global.controller;

import javax.json.Json;
import javax.json.JsonObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RestResource {	
	
	@RequestMapping(value = "/login" , method = RequestMethod.GET)
	public String login() {
		return "index";
	}
	
	
	@RequestMapping(value="/filial", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String filial() {
		  JsonObject result = Json.createObjectBuilder()
	                .add("idt_filial", "1")
	                .add("nom_filial", "BH")	               
	                .build();
	        return result.toString();
		
	}
	
}
