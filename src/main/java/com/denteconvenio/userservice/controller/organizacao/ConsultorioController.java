package com.denteconvenio.userservice.controller.organizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.denteconvenio.userservice.domain.organizacao.consultorio.ConsultorioRequestDTO;
import com.denteconvenio.userservice.domain.organizacao.consultorio.DentistasDTO;
import com.denteconvenio.userservice.domain.organizacao.consultorio.endereco.EnderecoPatchDTO;
import com.denteconvenio.userservice.service.organizacao.ConsultorioService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("consultorio")
public class ConsultorioController {

    @Autowired
    private ConsultorioService service;
    
    @PostMapping("/cadastrar")
    public ResponseEntity criarConsultorio(@RequestBody ConsultorioRequestDTO dto) {
        service.criarConsultorio(dto);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/consultorios")
    public ResponseEntity verConsultorios(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  ResponseEntity.ok(service.verConsultorios(pageable));
    }

    @GetMapping
    public ResponseEntity verConsultorio(HttpServletRequest request) {
        return  ResponseEntity.ok(service.verConsultorio(request));
    }

    
    @PostMapping("/dentistas")
    public ResponseEntity adicionarDentistas(HttpServletRequest request, @RequestBody DentistasDTO dto) {
        service.adicionarDentistas(request, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/dentistas")
    public ResponseEntity removerDentistas(HttpServletRequest request, @RequestBody DentistasDTO dto) {
        service.removerDentistas(request, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity alterarEnderecoConsultorio(HttpServletRequest request, EnderecoPatchDTO dto){
        return ResponseEntity.ok(service.alterarEnderecoConsultorio(request, dto));
    }
    
    @DeleteMapping
    public void deletarConsultorio(HttpServletRequest request){
        service.deletarConsultorio(request);
    }

}
