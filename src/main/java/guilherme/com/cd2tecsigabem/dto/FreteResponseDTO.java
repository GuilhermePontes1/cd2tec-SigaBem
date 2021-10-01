package guilherme.com.cd2tecsigabem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import guilherme.com.cd2tecsigabem.entity.Frete;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FreteResponseDTO {

    private Long id;
    private String cepOrigem;
    private String cepDestino;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataPrevistaEntrega;
    private Double vlTotalFrete;

    public FreteResponseDTO(Frete entity) {
        id = entity.getId();
        cepOrigem = entity.getCepOrigem();
        cepDestino = entity.getCepDestino();
        vlTotalFrete = entity.getVlTotalFrete();
        dataPrevistaEntrega = entity.getDataPrevistaEntrega();
    }
}
