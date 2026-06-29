package com.letssalon.view;

import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.dao.InsumoDAO;
import com.letssalon.dao.ServicoDAO;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Insumo;

import com.letssalon.util.CPFUtil;
import com.letssalon.util.SenhaUtil;
import com.letssalon.util.MascaraUtil;
import com.letssalon.util.CoresUtil;
import com.letssalon.util.BotaoUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class TelaCRUDInsumo extends JFrame {
    
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtValor;
    private JTextField txtQuantidade;
    private JTextField txtCapacidade;

    private JTable tabelaInsumos;
    private DefaultTableModel modeloTabela;

    private JButton btnCadastrar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    private InsumoDAO insumoDAO = new InsumoDAO();
    private Colaborador colaboradorLogado;

    public TelaCRUDInsumo(Colaborador colaborador) {

        this.colaboradorLogado = colaborador;

        initComponents();

        carregarTabela();
    }
    
    private void initComponents() {
        setTitle("CRUD de Insumos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));

        formulario.add(new JLabel("Código"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Nome"));
        txtNome = new JTextField();
        formulario.add(txtNome);

        formulario.add(new JLabel("Valor"));
        txtValor = new JTextField();
        formulario.add(txtValor);

        formulario.add(new JLabel("Quantidade"));
        txtQuantidade = new JTextField();
        formulario.add(txtQuantidade);

        formulario.add(new JLabel("Capacidade"));
        txtCapacidade = new JTextField();
        formulario.add(txtCapacidade);

        JPanel botoes = new JPanel();

        btnCadastrar = BotaoUtil.criarBotao("Cadastrar", CoresUtil.ROSA_PRINCIPAL);
        btnAtualizar = BotaoUtil.criarBotao("Atualizar", CoresUtil.VERDE_CONFIRMAR);
        btnExcluir   = BotaoUtil.criarBotao("Excluir", CoresUtil.VERMELHO_EXCLUIR);
        btnLimpar    = BotaoUtil.criarBotao("Limpar", CoresUtil.ROSA_SECUNDARIO);

        botoes.add(btnCadastrar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        botoes.add(btnLimpar);

        formulario.add(botoes);

        painel.add(formulario, BorderLayout.NORTH);

        String[] colunas = {
                "Código",
                "Nome",
                "Valor",
                "Quantidade",
                "Capacidade"
        };

        modeloTabela = new DefaultTableModel(colunas, 0);

        tabelaInsumos = new JTable(modeloTabela);

        JScrollPane scroll = new JScrollPane(tabelaInsumos);

        painel.add(scroll, BorderLayout.CENTER);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());

        tabelaInsumos.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        selecionarLinha();
                    }
                }
        );
    }

private void carregarTabela() {

    modeloTabela.setRowCount(0);

    List<Insumo> lista =
            insumoDAO.listar();

    for (Insumo i : lista) {

        modeloTabela.addRow(
            new Object[]{

                i.getCodigoInsumo(),

                i.getNomeInsumo(),

                i.getValorInsumo(),

                i.getQuantidadeInsumo(),

                i.getCapacidade()
            }
        );
    }
}

    private void cadastrar() {

    Insumo i = new Insumo();

    i.setNomeInsumo(
            txtNome.getText()
    );

    i.setValorInsumo(
        Double.parseDouble(
            txtValor.getText()
                  .replace(",", ".")
        )
    );

    i.setQuantidadeInsumo(
        Integer.parseInt(
            txtQuantidade.getText()
        )
    );

    i.setCapacidade(
            txtCapacidade.getText()
    );

    i.setCodigoEmpresa(
        colaboradorLogado
            .getCodigoEmpresa()
    );

    boolean sucesso = insumoDAO.cadastrar(i);

if (sucesso) {

    carregarTabela();
    limparCampos();

    JOptionPane.showMessageDialog(
        this,
        "Insumo cadastrado com sucesso!"
    );

} else {

    JOptionPane.showMessageDialog(
        this,
        "Erro ao cadastrar insumo!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
    );
}
}
private void atualizar() {

        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um insumo!"
            );
            return;
        }

        Insumo i = new Insumo();

        i.setCodigoInsumo(
                Integer.parseInt(
                        txtCodigo.getText()
                )
        );

        i.setNomeInsumo(txtNome.getText());

        i.setValorInsumo(
                Double.parseDouble(
                        txtValor.getText().replace(",", ".")
                )
        );

        i.setQuantidadeInsumo(
                Integer.parseInt(
                        txtQuantidade.getText()
                )
        );

        i.setCapacidade(
                txtCapacidade.getText()
        );

        i.setCodigoEmpresa(
                colaboradorLogado.getCodigoEmpresa()
        );

        insumoDAO.atualizar(i);

        carregarTabela();

        limparCampos();

        JOptionPane.showMessageDialog(
                this,
                "Insumo atualizado com sucesso!"
        );
    }
private void excluir() {

    if (txtCodigo.getText().isEmpty()) {

        JOptionPane.showMessageDialog(
            this,
            "Selecione um insumo!"
        );

        return;
    }

    insumoDAO.excluir(
        Integer.parseInt(
            txtCodigo.getText()
        )
    );

    carregarTabela();

    limparCampos();
     JOptionPane.showMessageDialog(
                this,
                "Insumo excluído com sucesso!"
        );
}
private void selecionarLinha() {

    int linha =
            tabelaInsumos
            .getSelectedRow();

    if (linha != -1) {

        txtCodigo.setText(
            modeloTabela
                .getValueAt(linha,0)
                .toString()
        );

        txtNome.setText(
            modeloTabela
                .getValueAt(linha,1)
                .toString()
        );

        txtValor.setText(
            modeloTabela
                .getValueAt(linha,2)
                .toString()
        );

        txtQuantidade.setText(
            modeloTabela
                .getValueAt(linha,3)
                .toString()
        );

        txtCapacidade.setText(
            modeloTabela
                .getValueAt(linha,4)
                .toString()
        );
    }
}
private void limparCampos() {

        txtCodigo.setText("");
        txtNome.setText("");
        txtValor.setText("");
        txtQuantidade.setText("");
        txtCapacidade.setText("");
}
}


