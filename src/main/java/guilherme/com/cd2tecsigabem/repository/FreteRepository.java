package guilherme.com.cd2tecsigabem.repository;

import guilherme.com.cd2tecsigabem.entity.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FreteRepository extends JpaRepository <Frete, Long> {

    @Query("SELECT obj FROM Frete obj WHERE obj.cepOrigem = :cepOrigem AND " + "obj.cepDestino = :cepDestino AND "
            + "obj.nomeDestinatario = :nomeDestinatario ")
    List<Frete> findByFrete(String cepOrigem, String cepDestino, String nomeDestinatario);}
