package app.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.entity.Equipamentos;
import app.repository.EmprestimosRepository;
import app.repository.EquipamentosRepository;
import app.service.EquipamentosService;

public class EquipamentosServiceTest {

    @InjectMocks
    private EquipamentosService equipamentosService;

    @Mock
    private EquipamentosRepository equipamentosRepository;

    @Mock
    private EmprestimosRepository emprestimosRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Save() {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setPatrimonio("123456");
        equipamento.setMarca("Dell");
        equipamento.setModelo("Inspiron");
        equipamento.setDataAquisicao(LocalDate.now());

        when(equipamentosRepository.save(any(Equipamentos.class))).thenReturn(equipamento);

        String result = equipamentosService.save(equipamento);
        assertEquals("Equipamento salvo com sucesso!", result);
        verify(equipamentosRepository, times(1)).save(equipamento);
    }

    @Test
    void Update() {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setPatrimonio("123456");

        when(equipamentosRepository.save(any(Equipamentos.class))).thenReturn(equipamento);

        String result = equipamentosService.update(equipamento, 1L);
        assertEquals("Equipamento salvo com sucesso!", result);
        assertEquals(1L, equipamento.getId());
        verify(equipamentosRepository, times(1)).save(equipamento);
    }

    @Test
    void FindByIdFound() {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setId(1L);

        when(equipamentosRepository.findById(1L)).thenReturn(Optional.of(equipamento));

        Equipamentos found = equipamentosService.findById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getId());
        verify(equipamentosRepository, times(1)).findById(1L);
    }

    @Test
    void FindByIdNotFound() {
        when(equipamentosRepository.findById(1L)).thenReturn(Optional.empty());

        Equipamentos found = equipamentosService.findById(1L);
        assertNull(found);
        verify(equipamentosRepository, times(1)).findById(1L);
    }

    @Test
    void FindAll() {
        List<Equipamentos> equipamentosList = new ArrayList<>();
        equipamentosList.add(new Equipamentos());

        when(equipamentosRepository.findAll()).thenReturn(equipamentosList);

        List<Equipamentos> result = equipamentosService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipamentosRepository, times(1)).findAll();
    }

    @Test
    void FindByPatrimonio() {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setPatrimonio("123456");

        when(equipamentosRepository.findByPatrimonio("123456")).thenReturn(equipamento);

        Equipamentos found = equipamentosService.findByPatrimonio("123456");
        assertNotNull(found);
        assertEquals("123456", found.getPatrimonio());
        verify(equipamentosRepository, times(1)).findByPatrimonio("123456");
    }

    @Test
    void FindByMarca() {
        List<Equipamentos> equipamentosList = new ArrayList<>();
        equipamentosList.add(new Equipamentos());

        when(equipamentosRepository.findByMarca("Dell")).thenReturn(equipamentosList);

        List<Equipamentos> result = equipamentosService.findByMarca("Dell");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipamentosRepository, times(1)).findByMarca("Dell");
    }

    @Test
    void FindByModelo() {
        List<Equipamentos> equipamentosList = new ArrayList<>();
        equipamentosList.add(new Equipamentos());

        when(equipamentosRepository.findByModeloContains("Inspiron")).thenReturn(equipamentosList);

        List<Equipamentos> result = equipamentosService.findByModelo("Inspiron");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipamentosRepository, times(1)).findByModeloContains("Inspiron");
    }

    @Test
    public void testFindBySituacao() {
        List<Equipamentos> equipamentosList = new ArrayList<>();
        equipamentosList.add(new Equipamentos());

        when(equipamentosRepository.findBySituacao("Disponível")).thenReturn(equipamentosList);

        List<Equipamentos> result = equipamentosService.findBySituacao("Disponível");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipamentosRepository, times(1)).findBySituacao("Disponível");
    }



    @Test
    void FindEquipamentosAtivos() {
        List<Equipamentos> equipamentosList = new ArrayList<>();
        equipamentosList.add(new Equipamentos());

        when(equipamentosRepository.findByAtivoTrue()).thenReturn(equipamentosList);

        List<Equipamentos> result = equipamentosService.findEquipamentosAtivos();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipamentosRepository, times(1)).findByAtivoTrue();
    }

    @Test
    void FindEquipamentosInativos() {
        List<Equipamentos> equipamentosList = new ArrayList<>();
        equipamentosList.add(new Equipamentos());

        when(equipamentosRepository.findByAtivoFalse()).thenReturn(equipamentosList);

        List<Equipamentos> result = equipamentosService.findEquipamentosInativos();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(equipamentosRepository, times(1)).findByAtivoFalse();
    }
}
