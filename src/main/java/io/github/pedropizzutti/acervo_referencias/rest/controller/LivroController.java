package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;
import io.github.pedropizzutti.acervo_referencias.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/livro")
public class LivroController {

    private LivroService livroService;

    public LivroController(LivroService livroService){
        this.livroService = livroService;
    }

    @PostMapping("/newBook")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody LivroDTO salvarLivro(@RequestBody @Valid LivroDTO livroDTO) throws RegraNegocioException {

        LivroDTO livroSalvoDTO = livroService.salvarLivro(livroDTO);

        return livroSalvoDTO;

    }

    @PutMapping("/attBook")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody LivroDTO atualizarLivro(@RequestBody @Valid LivroDTO livroDTO) throws RegraNegocioException {

        LivroDTO livroAtualizadoDTO = livroService.atualizarLivro(livroDTO);

        return livroAtualizadoDTO;

    }

    @DeleteMapping("/delBook/{idRegistroLivro}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLivro(@PathVariable Integer idRegistroLivro) throws RegraNegocioException {

        livroService.deletarLivro(idRegistroLivro);

    }

    @GetMapping("/findBook/{paginaAtual}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<LivroDTO> pesquisarLivrosFiltro
            (@RequestBody LivroDTO livroDTOFiltrado, @PathVariable Integer paginaAtual) throws RegraNegocioException {

        List<LivroDTO> livrosDTO = livroService.listarLivrosFiltro(livroDTOFiltrado, paginaAtual);

        return livrosDTO;

    }


}
