package guilherme.com.cd2tecsigabem.controller;


import guilherme.com.cd2tecsigabem.dto.FreteRequestDTO;
import guilherme.com.cd2tecsigabem.dto.FreteResponseDTO;
import guilherme.com.cd2tecsigabem.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/frete")
public class FreteController {

    @Autowired
    private FreteService service;


    @PostMapping
    public ResponseEntity<FreteResponseDTO> save(@RequestBody FreteRequestDTO dto) {
        return ResponseEntity.ok().body(service.save(dto));
    }
    @GetMapping(value = "/cepOrigem/{cepOrigem}/cepDestino/{cepDestino}/nomeDestinatario/{nomeDestinatario}")
    public ResponseEntity<List<FreteResponseDTO>> findFrete(@PathVariable String cepOrigem,
                                                            @PathVariable String cepDestino, @PathVariable String nomeDestinatario) {
        return ResponseEntity.ok().body(service.findFrete(cepOrigem, cepDestino, nomeDestinatario));
    }
}
