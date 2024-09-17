package app.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.controller.EquipamentosController;
import app.entity.Equipamentos;
import app.service.EquipamentosService;

@WebMvcTest(EquipamentosController.class)
class EquipamentosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipamentosService equipamentosService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveEquipamento() throws Exception {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setPatrimonio("12345");
        equipamento.setMarca("Dell");
        equipamento.setModelo("XPS 13");
        equipamento.setDataAquisicao(LocalDate.of(2022, 5, 20));

        when(equipamentosService.save(equipamento)).thenReturn("Equipamento salvo com sucesso!");

        mockMvc.perform(post("/api/equipamentos/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipamento)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testUpdateEquipamento() throws Exception {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setPatrimonio("12345");
        equipamento.setMarca("Dell");
        equipamento.setModelo("XPS 13");
        equipamento.setDataAquisicao(LocalDate.of(2022, 5, 20));

        when(equipamentosService.update(equipamento, 1L)).thenReturn("Equipamento salvo com sucesso!");

        mockMvc.perform(put("/api/equipamentos/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(equipamento)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testFindById() throws Exception {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setId(1L);
        equipamento.setPatrimonio("12345");
        equipamento.setMarca("Dell");
        equipamento.setModelo("XPS 13");
        equipamento.setDataAquisicao(LocalDate.of(2022, 5, 20));

        when(equipamentosService.findById(1L)).thenReturn(equipamento);

        mockMvc.perform(get("/api/equipamentos/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.patrimonio").value("12345"))
                .andExpect(jsonPath("$.marca").value("Dell"))
                .andExpect(jsonPath("$.modelo").value("XPS 13"));
    }

    @Test
    void testFindAllEquipamentos() throws Exception {
        Equipamentos equipamento1 = new Equipamentos();
        equipamento1.setId(1L);
        equipamento1.setPatrimonio("12345");
        equipamento1.setMarca("Dell");
        equipamento1.setModelo("XPS 13");
        equipamento1.setDataAquisicao(LocalDate.of(2022, 5, 20));

        Equipamentos equipamento2 = new Equipamentos();
        equipamento2.setId(2L);
        equipamento2.setPatrimonio("67890");
        equipamento2.setMarca("HP");
        equipamento2.setModelo("Spectre");
        equipamento2.setDataAquisicao(LocalDate.of(2021, 3, 15));

        List<Equipamentos> equipamentosList = Arrays.asList(equipamento1, equipamento2);

        when(equipamentosService.findAll()).thenReturn(equipamentosList);

        mockMvc.perform(get("/api/equipamentos/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].patrimonio").value("12345"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].patrimonio").value("67890"));
    }

    @Test
    void testFindByPatrimonio() throws Exception {
        Equipamentos equipamento = new Equipamentos();
        equipamento.setId(1L);
        equipamento.setPatrimonio("12345");
        equipamento.setMarca("Dell");
        equipamento.setModelo("XPS 13");
        equipamento.setDataAquisicao(LocalDate.of(2022, 5, 20));

        when(equipamentosService.findByPatrimonio("12345")).thenReturn(equipamento);

        mockMvc.perform(get("/api/equipamentos/findByPatrimonio")
                .param("patrimonio", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.patrimonio").value("12345"))
                .andExpect(jsonPath("$.marca").value("Dell"));
    }

    @Test
    void testDesativarEquipamento() throws Exception {
        when(equipamentosService.delete("12345")).thenReturn("Equipamento desativado com sucesso!");

        mockMvc.perform(put("/api/equipamentos/desativarEquipamento")
                .param("patrimonio", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Equipamento desativado com sucesso!"));
    }

    @Test
    void testReativarEquipamento() throws Exception {
        when(equipamentosService.reativarEquipamento("12345")).thenReturn("Equipamento reativado com sucesso!");

        mockMvc.perform(put("/api/equipamentos/reativarEquipamento")
                .param("patrimonio", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Equipamento reativado com sucesso!"));
    }
}
