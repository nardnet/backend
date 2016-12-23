package com.global.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.global.entity.Teste;

@RestController
public class TesteRestController {
 
  private Map<Integer, Teste> teste;
 
  public TesteRestController() {
    teste = new HashMap<Integer, Teste>();
 
    Teste c1 = new Teste(1, "Workshop Rest", "24hs");
    Teste c2 = new Teste(2, "Workshop Spring MVC", "24hs");
    Teste c3 = new Teste(3, "Desenvolvimento Web com JSF 2", "60hs");
 
    teste.put(1, c1);
    teste.put(2, c2);
    teste.put(3, c3);
  }
 
  @RequestMapping(value = "/teste", method = RequestMethod.GET)
  public ResponseEntity<List<Teste>> listar() {
    return new ResponseEntity<List<Teste>>(new ArrayList<Teste>(teste.values()), HttpStatus.OK);
  }
 
  

}
