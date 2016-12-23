package com.global.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalController {
	@RequestMapping("/")
	public String index(){
		System.out.println("ola");
		return "principal/index";
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		
	List<String> nomes	= new ArrayList<String>();
	nomes.add("Rafael");
	nomes.add("Sergio");
	nomes.add("Mathes");
		
		
	ModelAndView modelAndView =
	new ModelAndView("principal/list");
	modelAndView.addObject("nomes",nomes);
	return modelAndView;
	}
	
	
	
}
