package dev.matheusGama.gerenciamento_pescadores_api.service;

import dev.matheusGama.gerenciamento_pescadores_api.dto.request.PescadorRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.ParcelaResumoResponse;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.PescadorResponse;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Parcela;
import dev.matheusGama.gerenciamento_pescadores_api.entity.Pescador;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingMonthException;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingPescadorByCpfException;
import dev.matheusGama.gerenciamento_pescadores_api.exception.ErrorLocatingPescadorByIDException;
import dev.matheusGama.gerenciamento_pescadores_api.repository.PescadorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PescadorServiceTest {

    @Mock
    PescadorRepository pescadorRepository;

    @InjectMocks
    PescadorService pescadorService;

    @Test
    void deveSalvarUmNovoPescador() {
        // Arrange
        PescadorRequest request = new PescadorRequest(
                "Matheus",
                "111.222.333-44",
                "99 999999999",
                "Rua x"
        );

        when(pescadorRepository.save(any(Pescador.class)))
                .thenAnswer(invocation -> {
                    Pescador pescador = invocation.getArgument(0);
                    pescador.setId(UUID.randomUUID());
                    return pescador;
                });

        // Act
        PescadorResponse result = pescadorService.createPescador(request);

        // Assert
        assertNotNull(result);
        assertEquals("Matheus", result.nome());
        assertEquals("111.222.333-44", result.cpf());
        assertEquals("Rua x", result.endereco());
        assertEquals(12, result.parcelas().size());

        verify(pescadorRepository).save(any(Pescador.class));
    }

    @Test
    void deveLancarUmaExceptionQuandoNomeForNull() {
        // Arrange
        PescadorRequest request = new PescadorRequest(
                null,
                "111.222.333-44",
                "99 999999999",
                "Rua x"
        );

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class, () -> {
                    pescadorService.createPescador(request);
                });

        verify(pescadorRepository, never()).save(any(Pescador.class));
    }

    @Test
    void deveRetornarUmaListaDePescadores() {
        // Assert
        Pescador pescador = new Pescador();

        pescador.setNome("Matheus");
        pescador.setCpf("111.222.333-44");
        pescador.setEndereco("Rua x");

        List<Pescador> pescadores = new ArrayList<>();

        pescadores.add(pescador);

        when(pescadorRepository.findAll()).thenReturn(pescadores);

        // Act
        List<PescadorResponse> resut = pescadorService.findAllPescadores();

        // Assert
        assertNotNull(resut);
        assertEquals(1, resut.size());
        assertEquals("Matheus", resut.get(0).nome());

        verify(pescadorRepository).findAll();
    }

    @Test
    void deveRetornarUmaListaVazia() {
        // Assert
        List<Pescador> pescadores = new ArrayList<>();

        when(pescadorRepository.findAll()).thenReturn(pescadores);

        // Act
        List<PescadorResponse> resut = pescadorService.findAllPescadores();

        // Assert
        assertTrue(resut.isEmpty());
        assertEquals(0, resut.size());

        verify(pescadorRepository).findAll();
    }

    @Test
    void deveRetornarUmPescadorComIdPassado() {
        // Assert
        UUID id = UUID.randomUUID();
        Pescador pescador = new Pescador();

        pescador.setId(id);
        pescador.setNome("Matheus");
        pescador.setCpf("111.222.333-44");
        pescador.setEndereco("Rua x");

        when(pescadorRepository.findById(id)).thenReturn(Optional.of(pescador));

        // Act
        PescadorResponse resut = pescadorService.findByPescadorId(id);

        // Assert
        assertNotNull(resut);
        assertEquals(id, resut.pescador_id());
        assertEquals("Matheus", resut.nome());
        assertEquals("111.222.333-44", resut.cpf());
        assertEquals("Rua x", resut.endereco());

        verify(pescadorRepository).findById(id);
    }

    @Test
    void deveRetornarUmaExceptionQuandoNaoEncontrarPescador() {
        // Assert
        UUID id = UUID.randomUUID();

        when(pescadorRepository.findById(id)).thenReturn(Optional.empty());

        ErrorLocatingPescadorByIDException exception = assertThrows(
                ErrorLocatingPescadorByIDException.class,
                () -> pescadorService.findByPescadorId(id)
        );

        assertEquals("Erro ao buscar pescador com id: " + id, exception.getMessage());
        verify(pescadorRepository).findById(id);
    }

    @Test
    void deveRetornarUmPescadorComCpfPassado() {
        // Assert
        String cpf = "111.222.333-44";
        Pescador pescador = new Pescador();

        pescador.setNome("Matheus");
        pescador.setCpf(cpf);
        pescador.setEndereco("Rua x");

        when(pescadorRepository.findByCpf(cpf)).thenReturn(Optional.of(pescador));

        // Act
        PescadorResponse resut = pescadorService.findByPescadorCpf(cpf);

        // Assert
        assertNotNull(resut);
        assertEquals("Matheus", resut.nome());
        assertEquals("111.222.333-44", resut.cpf());
        assertEquals("Rua x", resut.endereco());

        verify(pescadorRepository).findByCpf(cpf);
    }

    @Test
    void deveRetornarUmaExceptionQuandoNaoEncontrarPescadorComCpfPassado() {
        // Assert
        String cpf = "111.222.333-44";

        when(pescadorRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        ErrorLocatingPescadorByCpfException exception = assertThrows(
                ErrorLocatingPescadorByCpfException.class,
                () -> pescadorService.findByPescadorCpf(cpf)
        );

        assertEquals("Erro ao buscar pescador com cpf: " + cpf, exception.getMessage());
        verify(pescadorRepository).findByCpf(cpf);
    }

    @Test
    void deveDeletarUmPescador() {
        // Assert
        UUID id = UUID.randomUUID();
        Pescador pescador = new Pescador();

        pescador.setId(id);
        pescador.setNome("Matheus");

        when(pescadorRepository.findById(id)).thenReturn(Optional.of(pescador));

        // Act
        pescadorService.deletePescador(id);

        // Assert
        verify(pescadorRepository).findById(id);
        verify(pescadorRepository).delete(pescador);
    }

    @Test
    void deveRetornarUmaExceptionQuandoDeletarPescadorNaoEncontrado() {
        // Arrage
        UUID id = UUID.randomUUID();

        when(pescadorRepository.findById(id)).thenReturn(Optional.empty());

        ErrorLocatingPescadorByIDException exception = assertThrows(
                ErrorLocatingPescadorByIDException.class,
                () -> pescadorService.deletePescador(id)
        );

        assertEquals("Erro ao buscar pescador com id: " + id, exception.getMessage());

        verify(pescadorRepository).findById(id);
        verify(pescadorRepository, never()).delete(any(Pescador.class));
    }

    @Test
    void deveAtualizardadosDosPescadores() {
        // Arrage
        PescadorRequest request = new PescadorRequest(
                "Gabigol",
                "157.157.157-44",
                "99 999999999",
                "Barra da Tijuca"
        );

        UUID id = UUID.randomUUID();
        Pescador pescador = new Pescador();

        pescador.setId(id);
        pescador.setNome("Matheus");
        pescador.setCpf("111.222.333-44");
        pescador.setEndereco("Rua x");

        when(pescadorRepository.findById(id)).thenReturn(Optional.of(pescador));
        when(pescadorRepository.save(any(Pescador.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PescadorResponse result = pescadorService.updatePescador(request, id);

        // Assert

        assertNotNull(result);
        assertEquals("Gabigol", result.nome());
        assertEquals("157.157.157-44", result.cpf());
        assertEquals("Barra da Tijuca", result.endereco());

        verify(pescadorRepository).findById(id);
        verify(pescadorRepository).save(any(Pescador.class));
    }

    @Test
    void devePagarParcelaDoPescador() {
        // Arrage
        long mes = 2;
        UUID pescador_id = UUID.randomUUID();

        Pescador pescador = new Pescador();
        pescador.setId(pescador_id);

        Parcela parcela = new Parcela();

        parcela.setMes(mes);
        parcela.setPago(false);
        parcela.setValor(BigDecimal.valueOf(25));

        pescador.setParcelas(new ArrayList<>(List.of(parcela)));

        when(pescadorRepository.findById(pescador_id)).thenReturn(Optional.of(pescador));

        // Act
        ParcelaResumoResponse result = pescadorService.pagarParcela(pescador_id, mes);

        // Assert

        assertNotNull(result);
        assertTrue(result.pago());
        assertNotNull(parcela.getDataPagamento());

        verify(pescadorRepository).findById(pescador_id);
    }

    @Test
    void deveLancarExcecaoQuandoPescadorNaoExisteAoPagarParcela() {
        // Arrange
        UUID pescadorId = UUID.randomUUID();

        when(pescadorRepository.findById(pescadorId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ErrorLocatingPescadorByIDException.class,
                () -> pescadorService.pagarParcela(pescadorId, 1L)
        );
    }

    @Test
    void deveLancarExcecaoQuandoParcelaDoMesNaoExiste() {
        // Arrange
        UUID pescadorId = UUID.randomUUID();

        Pescador pescador = new Pescador();
        pescador.setId(pescadorId);
        pescador.setParcelas(new ArrayList<>());

        when(pescadorRepository.findById(pescadorId))
                .thenReturn(Optional.of(pescador));

        // Act + Assert
        assertThrows(
                ErrorLocatingMonthException.class,
                () -> pescadorService.pagarParcela(pescadorId, 1L)
        );
    }
}