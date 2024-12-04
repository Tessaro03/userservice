package com.denteconvenio.userservice.service.pessoal;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.denteconvenio.userservice.domain.pessoal.dentista.Dentista;
import com.denteconvenio.userservice.domain.pessoal.dentista.DentistaRequestDTO;
import com.denteconvenio.userservice.domain.pessoal.dentista.DentistaResponseDTO;
import com.denteconvenio.userservice.infra.security.TokenService;
import com.denteconvenio.userservice.repository.ConsultorioRepository;
import com.denteconvenio.userservice.repository.DentistaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class DentistaService {
    
    @Autowired
    private DentistaRepository repository;

    @Autowired
    private ConsultorioRepository consultorioRepository;


    @Autowired
    private TokenService tokenService;

    @Cacheable(value="dentistas")
    public List<DentistaResponseDTO> verDentistas(Pageable pageable){
        var dentistas = repository.findAll(pageable);
        return dentistas.stream().map(DentistaResponseDTO::new).toList();
    }

    @CacheEvict(value="dentistas", allEntries=true)
    public DentistaResponseDTO criarDentista(DentistaRequestDTO dto){
        var dentista = new Dentista.DentistaBuilder(dto.nome(), dto.email(), dto.senha(), dto.cpf())
                                    .ativo(false)
                                    .cro(dto.cro())
                                    .build();
        repository.save(dentista);

        var dtoResponse = new DentistaResponseDTO(dentista);
        return dtoResponse;
    }

    @Transactional
    @CacheEvict(value={"dentistas" ,"consultorios", "consultorio"}, allEntries=true)
    public void deletarDentista(HttpServletRequest request){
        var dentistaToken = tokenService.extrairInformacoes(request);
        repository.removerConsultorios(dentistaToken.id());
        repository.deleteById(dentistaToken.id());
    }

    @CacheEvict(value={"dentistas" ,"consultorios", "consultorio"}, allEntries=true)
    public void sairConsultorio(HttpServletRequest request,UUID id) {
        var dentistaToken = tokenService.extrairInformacoes(request);
        var dentista = repository.getReferenceById(dentistaToken.id());
        var consultorio = consultorioRepository.getReferenceById(id);
        consultorio.getDentistas().remove(dentista);
        consultorioRepository.save(consultorio);
    }
}
