package com.global.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.global.manager.ContratosManager;
import com.global.vo.ContratosPublicacoes;

@RestController
public class TesteRestController {
 

  @RequestMapping(value = "/teste", method = RequestMethod.GET)
  public ResponseEntity<List<ContratosPublicacoes>> listar() {
		 ContratosManager cDao = new ContratosManager();
		 List<ContratosPublicacoes> contratos = null;
		try {
			contratos = cDao.getBuscaContratosPublicacoes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
   return new ResponseEntity<List<ContratosPublicacoes>>(contratos,HttpStatus.OK);
  }
 
  

}
