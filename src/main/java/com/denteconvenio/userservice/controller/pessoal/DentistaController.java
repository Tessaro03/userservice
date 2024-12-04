package com.denteconvenio.userservice.controller.pessoal;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.denteconvenio.userservice.domain.pessoal.dentista.DentistaRequestDTO;
import com.denteconvenio.userservice.service.pessoal.DentistaService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("dentista")
public class DentistaController {

    @Autowired
    private DentistaService service;
    
    @PostMapping("/cadastrar")
    public ResponseEntity criarDentista(@RequestBody DentistaRequestDTO dto) {
        var dtoResponse = service.criarDentista(dto);
        return ResponseEntity.ok(dtoResponse);
    }
    
    @GetMapping
    public ResponseEntity verDentistas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.verDentistas(pageable));
    }

    @DeleteMapping("/consultorio/{id}")
    public void sairConsultorio(HttpServletRequest request,@PathVariable UUID id){
        service.sairConsultorio(request,id);
    }

    @DeleteMapping
    public void deletarDentista(HttpServletRequest request){
        service.deletarDentista(request);
    }

}
