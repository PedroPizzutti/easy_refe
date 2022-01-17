package io.github.pedropizzutti.acervo_referencias.rest.controller;

import io.github.pedropizzutti.acervo_referencias.exception.RegraNegocioException;
import io.github.pedropizzutti.acervo_referencias.rest.dto.LivroDTO;
import io.github.pedropizzutti.acervo_referencias.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


}
