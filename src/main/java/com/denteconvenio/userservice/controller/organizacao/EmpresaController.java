package com.denteconvenio.userservice.controller.organizacao;

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

import com.denteconvenio.userservice.domain.organizacao.empresa.ColaboradoresDTO;
import com.denteconvenio.userservice.domain.organizacao.empresa.EmpresaRequestDTO;
import com.denteconvenio.userservice.service.organizacao.EmpresaService;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("empresa")
public class EmpresaController {
    
    @Autowired
    private EmpresaService service;


    @PostMapping("/cadastrar")
    public ResponseEntity criarEmpresa(@RequestBody EmpresaRequestDTO dto) {
        service.criarEmpresa(dto);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/colaboradores")
    public void adicionarColaboradores(HttpServletRequest request, @RequestBody ColaboradoresDTO dto){
        service.adicionarColaboradores(request, dto);
    }

    @DeleteMapping("/colaboradores")
    public void removerColaboradores(@RequestBody ColaboradoresDTO dto){
        service.removerColaboradores(dto);
    }


    @GetMapping 
    public ResponseEntity verEmpresa(HttpServletRequest request) {
        return ResponseEntity.ok(service.verEmpresa(request));
    }
    
    @GetMapping("/colaboradores")
    public ResponseEntity verBeneficiariosEmpresa(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.verBeneficiarios(pageable, request)); 
    }
    
    @DeleteMapping
    public void deletarEmpresa(HttpServletRequest request){
        service.deletarEmpresa(request);
    }

}
