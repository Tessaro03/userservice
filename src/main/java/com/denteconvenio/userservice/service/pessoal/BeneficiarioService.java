package com.denteconvenio.userservice.service.pessoal;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.denteconvenio.userservice.domain.pessoal.beneficiario.Beneficiario;
import com.denteconvenio.userservice.domain.pessoal.beneficiario.BeneficiarioRequestDTO;
import com.denteconvenio.userservice.domain.pessoal.beneficiario.BeneficiarioResponseDTO;
import com.denteconvenio.userservice.infra.security.TokenService;
import com.denteconvenio.userservice.repository.BeneficiarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BeneficiarioService {

    @Autowired
    private BeneficiarioRepository repository;

    @Autowired
    private TokenService tokenService;

    public BeneficiarioResponseDTO criarBeneficiario(BeneficiarioRequestDTO dto){
        var beneficiario = new Beneficiario.BeneficiarioBuilder(dto.nome(), dto.email(), dto.senha(), dto.cpf())
                                    .ativo(false)
                                    .telefone(dto.telefone())
                                    .build();
        repository.save(beneficiario);
        var dtoResponse = new BeneficiarioResponseDTO(beneficiario);
        return  dtoResponse;
    }
    
    public void deletarBeneficiario(HttpServletRequest request){
        var beneficiarioToken = tokenService.extrairInformacoes(request);
        repository.deleteById(beneficiarioToken.id());
    }

    public List<BeneficiarioResponseDTO> verBeneficiarios() {
        var beneficiarios = repository.findAll();
        return beneficiarios.stream().map(BeneficiarioResponseDTO::new).collect(Collectors.toList());

    }

    @Cacheable(value="idEmpresaBeneficiario")
    public UUID verIdEmpresaDeBeneficiario(HttpServletRequest request) {
        var beneficiarioToken = tokenService.extrairInformacoes(request);
        var beneficiario = repository.findById(beneficiarioToken.id());
        if (beneficiario.isPresent()) {
            return beneficiario.get().getEmpresa().getId();
        }
        return null;
    }

}
