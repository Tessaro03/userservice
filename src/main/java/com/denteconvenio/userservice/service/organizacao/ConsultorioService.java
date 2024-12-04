package com.denteconvenio.userservice.service.organizacao;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.denteconvenio.userservice.domain.organizacao.consultorio.Consultorio;
import com.denteconvenio.userservice.domain.organizacao.consultorio.ConsultorioRequestDTO;
import com.denteconvenio.userservice.domain.organizacao.consultorio.ConsultorioResponseDTO;
import com.denteconvenio.userservice.domain.organizacao.consultorio.DentistasDTO;
import com.denteconvenio.userservice.domain.organizacao.consultorio.endereco.EnderecoPatchDTO;
import com.denteconvenio.userservice.infra.security.TokenService;
import com.denteconvenio.userservice.repository.ConsultorioRepository;
import com.denteconvenio.userservice.repository.DentistaRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ConsultorioService {
    
    @Autowired
    private ConsultorioRepository repository;

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Cacheable(value = "consultorios")
    public List<ConsultorioResponseDTO> verConsultorios(Pageable pageable){
        var consultorios = repository.findAll(pageable);
        return consultorios.stream().map(ConsultorioResponseDTO::new).collect(Collectors.toList());
    }

    @Cacheable(value = "consultorio")
    public ConsultorioResponseDTO verConsultorio(HttpServletRequest request){
        var consultorioToken = tokenService.extrairInformacoes(request);

        var consultorio = repository.getReferenceById(consultorioToken.id());
        return new ConsultorioResponseDTO(consultorio);
    }


    @CacheEvict(value={"consultorios", "consultorio"}, allEntries=true)
    public void criarConsultorio(ConsultorioRequestDTO dto) {
        var consultorio = new Consultorio.ConsultorioBuilder(dto.nome(), dto.email(), dto.senha(), dto.cnpj())
                                            .ativo(true)
                                            .telefone(dto.telefone())
                                            .endereco(dto.endereco().cep(), dto.endereco().estado(), dto.endereco().cidade(),dto.endereco().rua(), dto.endereco().numero())
                                            .build();
        repository.save(consultorio);
        rabbitTemplate.convertAndSend("user-consultorio","criado",consultorio.getId());
    }

    @CacheEvict(value={"consultorios", "consultorio"}, allEntries=true)
    public void adicionarDentistas(HttpServletRequest request, DentistasDTO dto){
        var consultorioToken = tokenService.extrairInformacoes(request);
        var consultorio = repository.getReferenceById(consultorioToken.id());
        for (UUID id : dto.dentistas()) {
            var dentista = dentistaRepository.getReferenceById(id);
            if (!consultorio.getDentistas().contains(dentista)) {
                consultorio.getDentistas().add(dentista);
                repository.save(consultorio);
            }
        }
    }

    @CacheEvict(value={"consultorios", "consultorio"}, allEntries=true)
    public void removerDentistas(HttpServletRequest request, DentistasDTO dto){
        var consultorioToken = tokenService.extrairInformacoes(request);
        var consultorio = repository.getReferenceById(consultorioToken.id());
        for (UUID id : dto.dentistas()) {
            var dentista = dentistaRepository.getReferenceById(id);
            consultorio.getDentistas().remove(dentista);
            repository.save(consultorio);
        }
    }

    @CacheEvict(value={"consultorios", "consultorio"}, allEntries=true)
    public ConsultorioResponseDTO alterarEnderecoConsultorio(HttpServletRequest request, EnderecoPatchDTO dto){
        var consultorioToken = tokenService.extrairInformacoes(request);
        var consultorio = repository.getReferenceById(consultorioToken.id());
        if (dto.cep() != null) {
            consultorio.getEndereco().setCep(dto.cep()); 
        }
        if (dto.estado() != null) {
            consultorio.getEndereco().setEstado(dto.estado()); 
        }
        if (dto.cidade() != null) {
            consultorio.getEndereco().setCidade(dto.cidade()); 
        }
        if (dto.rua() != null) {
            consultorio.getEndereco().setRua(dto.rua()); 
        }
        if (dto.numero() != null) {
            consultorio.getEndereco().setNumero(dto.numero()); 
        }
        repository.save(consultorio);
        return new ConsultorioResponseDTO(consultorio);   
    }

    @CacheEvict(value={"consultorios", "consultorio"}, allEntries=true)
    public void deletarConsultorio(HttpServletRequest request){
        var consultorioToken = tokenService.extrairInformacoes(request);
        repository.deleteById(consultorioToken.id());
    }

}
