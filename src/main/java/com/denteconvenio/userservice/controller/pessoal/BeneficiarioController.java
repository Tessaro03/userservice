package com.denteconvenio.userservice.controller.pessoal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.denteconvenio.userservice.domain.pessoal.beneficiario.BeneficiarioRequestDTO;
import com.denteconvenio.userservice.service.pessoal.BeneficiarioService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("beneficiario")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioService service;
    
 
    @GetMapping("/idEmpresa")
    public ResponseEntity idEmpresa(HttpServletRequest request) {
        return ResponseEntity.ok(service.verIdEmpresaDeBeneficiario(request));
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity criarBeneficiario(@RequestBody BeneficiarioRequestDTO dto) {
        return ResponseEntity.ok(service.criarBeneficiario(dto));
    }

    @DeleteMapping
    public void deletarBeneficiario(HttpServletRequest request){
        service.deletarBeneficiario(request);
    }

}
