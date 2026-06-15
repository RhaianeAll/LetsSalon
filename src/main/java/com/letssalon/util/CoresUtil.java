package com.letssalon.util;

import java.awt.Color;

/**
 * CLASSE CENTRALIZADA DE CORES — LET'S SALON
 *
 * Todas as cores do sistema estão definidas aqui.
 * Para mudar qualquer cor do programa, altere apenas
 * esta classe. Nenhuma outra tela precisa ser tocada.
 *
 * Paleta definida pelo design no Canva (hex convertido para RGB).
 */
public class CoresUtil {

    // -------------------------------------------------------
    // CORES PRINCIPAIS
    // -------------------------------------------------------

    /**
     * Rosa principal — #e06882 → RGB(224, 104, 130)
     * Usado em: barra de topo, cabeçalho de tabelas,
     * botões primários (Cadastrar, Entrar, Agendar),
     * títulos coloridos e bordas de destaque.
     */
    public static final Color ROSA_PRINCIPAL = new Color(224, 104, 130);

    /**
     * Rosa fundo — #fff2ef → RGB(255, 242, 239)
     * Usado em: fundo geral de todas as telas e painéis internos.
     */
    public static final Color ROSA_FUNDO = new Color(255, 242, 239);

    /**
     * Rosa painel form — #feadb9 → RGB(254, 173, 185)
     * Usado em: painéis de login e inscrição (lado direito/formulário).
     */
    public static final Color ROSA_PAINEL_FORM = new Color(254, 173, 185);

    /**
     * Rosa secundário — #ff7290 → RGB(255, 114, 144)
     * Usado em: botão "Limpar", "Inscreva-se", grid das tabelas.
     */
    public static final Color ROSA_SECUNDARIO = new Color(255, 114, 144);

    // -------------------------------------------------------
    // CORES DE AÇÃO
    // -------------------------------------------------------

    /**
     * Vermelho excluir — #a0465a → RGB(160, 70, 90)
     * Usado em: botão "Excluir".
     */
    public static final Color VERMELHO_EXCLUIR = new Color(160, 70, 90);

    /**
     * Cinza voltar — #ebebeb → RGB(235, 235, 235)
     * Usado em: botão "Voltar" e "Sair".
     * Atenção: com fundo claro, use CINZA_VOLTAR_TEXTO no texto do botão.
     */
    public static final Color CINZA_VOLTAR = new Color(235, 235, 235);

    /**
     * Texto do botão Voltar/Sair — escuro para contrastar com #ebebeb.
     * Usado somente nos botões que usam CINZA_VOLTAR como fundo.
     */
    public static final Color CINZA_VOLTAR_TEXTO = new Color(80, 80, 80);

    /**
     * Verde confirmar — #b6e2d3 → RGB(182, 226, 211)
     * Usado em: botão "Confirmar Agendamento".
     * Atenção: cor clara, use VERDE_CONFIRMAR_TEXTO no texto do botão.
     */
    public static final Color VERDE_CONFIRMAR = new Color(182, 226, 211);

    /**
     * Texto do botão Confirmar — escuro para contrastar com #b6e2d3.
     * Usado somente nos botões que usam VERDE_CONFIRMAR como fundo.
     */
    public static final Color VERDE_CONFIRMAR_TEXTO = new Color(40, 100, 80);

    // -------------------------------------------------------
    // CORES DE TEXTO
    // -------------------------------------------------------

    /**
     * Texto claro — branco, usado sobre fundos rosa escuro.
     */
    public static final Color TEXTO_CLARO = Color.WHITE;

    /**
     * Texto título — rosa principal, em fundo branco ou rosa claro.
     */
    public static final Color TEXTO_TITULO = ROSA_PRINCIPAL;

    /**
     * Texto corpo — cinza escuro para descrições e labels.
     */
    public static final Color CINZA_TEXTO_CORPO = new Color(80, 80, 80);

    /**
     * Link suave — "Esqueci minha senha" e links discretos.
     */
    public static final Color ROSA_ESCURO_LINK = new Color(100, 50, 60);

    // -------------------------------------------------------
    // CORES DE TABELA
    // -------------------------------------------------------

    /**
     * Cabeçalho de tabela — fundo rosa principal.
     */
    public static final Color TABELA_CABECALHO_FUNDO = ROSA_PRINCIPAL;

    /**
     * Cabeçalho de tabela — texto branco.
     */
    public static final Color TABELA_CABECALHO_TEXTO = Color.WHITE;

    /**
     * Linhas de grade da tabela.
     */
    public static final Color TABELA_GRADE = ROSA_SECUNDARIO;

    /**
     * Fundo das linhas da tabela.
     */
    public static final Color TABELA_LINHA_FUNDO = Color.WHITE;

    /**
     * Borda de card (ex: card "Quem Somos").
     */
    public static final Color CINZA_BORDA_CARD = new Color(220, 220, 220);

    // Construtor privado: esta classe não deve ser instanciada.
    private CoresUtil() {}
}