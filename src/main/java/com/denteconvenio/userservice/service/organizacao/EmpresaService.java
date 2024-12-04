package com.denteconvenio.userservice.service.organizacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.denteconvenio.userservice.domain.organizacao.empresa.ColaboradoresDTO;
import com.denteconvenio.userservice.domain.organizacao.empresa.Empresa;
import com.denteconvenio.userservice.domain.organizacao.empresa.EmpresaRequestDTO;
import com.denteconvenio.userservice.domain.organizacao.empresa.EmpresaResponseDTO;
import com.denteconvenio.userservice.domain.pessoal.beneficiario.Beneficiario;
import com.denteconvenio.userservice.domain.pessoal.beneficiario.BeneficiarioResponseDTO;
import com.denteconvenio.userservice.infra.security.TokenService;
import com.denteconvenio.userservice.repository.BeneficiarioRepository;
import com.denteconvenio.userservice.repository.EmpresaRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmpresaService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmpresaRepository repository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;
    
    @Cacheable(value="empresa")
    public EmpresaResponseDTO verEmpresa(HttpServletRequest request){
        var empresaToken = tokenService.extrairInformacoes(request);
        var empresa = repository.getReferenceById(empresaToken.id());
        return new EmpresaResponseDTO(empresa);
    }

    public void criarEmpresa(EmpresaRequestDTO dto){
        var empresa = new Empresa.EmpresaBuilder(dto.nome(), dto.email(), dto.senha(), dto.cnpj())
                            .ativo(true)
                            .telefone(dto.telefone())
                            .build();
        repository.save(empresa);
    }
    
    @CacheEvict(value={"empresa", "beneficiarios"}, allEntries=true)
    public void adicionarColaboradores(HttpServletRequest request,ColaboradoresDTO dto){
        var empresaToken = tokenService.extrairInformacoes(request);
        var empresa = repository.getReferenceById(empresaToken.id());
        for (UUID id : dto.colaboradores()) {
            var beneficiario = beneficiarioRepository.getReferenceById(id);
            if (!empresa.getColaboradores().contains(beneficiario)) {
                beneficiario.setEmpresa(empresa);
                beneficiarioRepository.save(beneficiario);
            }
        }
    }

    @CacheEvict(value={"empresa", "beneficiarios", "idEmpresaBeneficiario"}, allEntries=true)
    public void removerColaboradores(ColaboradoresDTO dto){
        for (UUID id : dto.colaboradores()) {
            var beneficiario = beneficiarioRepository.getReferenceById(id);
            beneficiario.setEmpresa(null);
            beneficiarioRepository.save(beneficiario);
        }
    }


    @Cacheable(value="beneficiarios")
    public List<BeneficiarioResponseDTO> verBeneficiarios(Pageable pageable, HttpServletRequest request) {
        var empresa = tokenService.extrairInformacoes(request);
        var beneficiarios = beneficiarioRepository.findAllByEmpresaId(empresa.id(), pageable);
        return beneficiarios.stream().map(BeneficiarioResponseDTO::new).toList(); 
    }

    @CacheEvict(value={"empresa", "beneficiarios"}, allEntries=true)
    public void deletarEmpresa(HttpServletRequest request){
        var empresaToken = tokenService.extrairInformacoes(request);
        
        var empresa = repository.getReferenceById(empresaToken.id());
        List<UUID> colaboradoresIds = empresa.getColaboradores().stream()
                                    .map(Beneficiario::getId) 
                                    .collect(Collectors.toList());

        removerColaboradores(new ColaboradoresDTO(colaboradoresIds));
        repository.deleteById(empresaToken.id());
    }

}
