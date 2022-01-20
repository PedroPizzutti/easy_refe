package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;
import io.github.pedropizzutti.acervo_referencias.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private LivroService livroService;

    public LivroController(LivroService livroService){
        this.livroService = livroService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody LivroDTO salvarLivro(@RequestBody @Valid LivroDTO livroDTO) throws RegraNegocioException {

        LivroDTO livroSalvoDTO = livroService.salvarLivro(livroDTO);

        return livroSalvoDTO;

    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody LivroDTO atualizarLivro(@RequestBody @Valid LivroDTO livroDTO) throws RegraNegocioException {

        LivroDTO livroAtualizadoDTO = livroService.atualizarLivro(livroDTO);

        return livroAtualizadoDTO;

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLivro(@RequestBody @Valid LivroDTO livroDTO) throws RegraNegocioException {

        livroService.deletarLivro(livroDTO);

    }

    @GetMapping("/{loginUsuario}/{paginacaoBusca}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<LivroDTO> buscarLivrosUsuario
            (@PathVariable String loginUsuario, @PathVariable Integer paginacaoBusca) throws RegraNegocioException {

        List<LivroDTO> livroDTOS = livroService.listarLivrosUsuario(loginUsuario, paginacaoBusca);

        return  livroDTOS;

    }

    @GetMapping("{paginacaoBuscaFiltro}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<LivroDTO> pesquisarLivrosFiltro
            (@RequestBody LivroDTO livroDTOFiltrado, @PathVariable Integer paginacaoBuscaFiltro) throws RegraNegocioException {

        List<LivroDTO> livrosDTO = livroService.listarLivrosFiltro(livroDTOFiltrado, paginacaoBuscaFiltro);

        return livrosDTO;

    }


}
