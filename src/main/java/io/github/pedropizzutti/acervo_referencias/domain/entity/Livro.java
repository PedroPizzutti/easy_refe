package io.github.pedropizzutti.acervo_referencias.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table (name = "tb_referencias_bibliograficas")
public class Livro {

    @Id
    @Column(name = "id_referencia")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "autor_referencia", length = 100, nullable = false)
    @NotEmpty
    private String autor;

    @Column(name = "titulo_referencia", length = 200, nullable = false)
    @NotEmpty
    private String titulo;

    @Column(name = "ano_referencia", length = 4)
    private Integer ano;

    @Column(name = "referencia_referencia", length = 300, nullable = false)
    @NotEmpty
    private String referencia;

    @Column(name = "anotacoes_referencia", length = 300)
    private String anotacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk")
    private Usuario usuario;

}
