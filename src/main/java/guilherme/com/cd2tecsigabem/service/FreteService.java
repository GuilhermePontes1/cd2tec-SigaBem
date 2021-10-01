package guilherme.com.cd2tecsigabem.service;

import guilherme.com.cd2tecsigabem.CalculatorFreteUtils.FreteCalculator;
import guilherme.com.cd2tecsigabem.dto.FreteRequestDTO;
import guilherme.com.cd2tecsigabem.dto.FreteResponseDTO;
import guilherme.com.cd2tecsigabem.entity.Frete;
import guilherme.com.cd2tecsigabem.repository.FreteRepository;
import guilherme.com.cd2tecsigabem.service.exceptions.ResourceNotFoundException;
import guilherme.com.cd2tecsigabem.viacep.EnderecoDTO;
import guilherme.com.cd2tecsigabem.viacep.ViaCepWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreteService {

    @Autowired
    private ViaCepWebService webService;

    @Autowired
    private FreteRepository repository;

    @Transactional
    public FreteResponseDTO save(FreteRequestDTO dto){
        Frete entity = new Frete();
        entity = copyDtoToEntity(entity, dto);
        repository.save(entity);
        return new FreteResponseDTO(entity);
    }
    @Transactional
    public List<FreteResponseDTO> findFrete(String cepOrigem, String cepDestino, String nomeDestinatario) {
        List<Frete> result = repository.findByFrete(cepOrigem, cepDestino, nomeDestinatario);

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não Localizado");
        }
        result.forEach(x -> x.setDataConsulta(LocalDateTime.now()));
        return result.stream().map(x -> new FreteResponseDTO(x)).collect(Collectors.toList());

    }


    private Frete copyDtoToEntity(Frete entity, FreteRequestDTO dto) {


        entity.setPeso(dto.getPeso());
        entity.setNomeDestinatario(dto.getNomeDestinatario());

        entity.setCepOrigem(dto.getCepOrigem());

        EnderecoDTO viaCepOrigem = webService.viaCepWebService(Integer.valueOf(entity.getCepOrigem()));
        String dddOrigem = viaCepOrigem.getDdd();
        String ufOrigem = viaCepOrigem.getUf();


        entity.setCepDestino(dto.getCepDestino());

        EnderecoDTO viaCepDestino = webService.viaCepWebService(Integer.valueOf(entity.getCepDestino()));
        String dddDestino = viaCepDestino.getDdd();
        String ufDestino = viaCepDestino.getUf();


        if (dddOrigem.equals(dddDestino) && ufOrigem.equals(ufDestino)) {
            entity.setDataPrevistaEntrega(LocalDateTime.now().plusDays(1L));
            FreteCalculator.CalcularDddIgual(entity);
        }

        if (ufOrigem.equals(ufDestino) && !dddOrigem.equals(dddDestino)) {
            entity.setDataPrevistaEntrega(LocalDateTime.now().plusDays(3L));
            FreteCalculator.CalcularUfIgual(entity);
        }

        if (!ufOrigem.equals(ufDestino)) {
            entity.setDataPrevistaEntrega(LocalDateTime.now().plusDays(10L));
            FreteCalculator.CalcularTudoDiferente(entity);
        }


        return entity;
    }



}
