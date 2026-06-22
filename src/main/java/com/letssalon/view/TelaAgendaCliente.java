package com.letssalon.view;

import com.letssalon.dao.AgendaDAO;
import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.dao.ServicoDAO;
import com.letssalon.model.Agenda;
import com.letssalon.model.Cliente;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Servico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.letssalon.util.CoresUtil;

public class TelaAgendaCliente extends JFrame {

    private Cliente clienteLogado;
    private JTextField txtData, txtHora;
    private JComboBox<String> cmbColaborador, cmbServico;
    private JButton btnAgendar, btnCancelar, btnSair;
    private JTable tabelaMeusAgendamentos;
    private DefaultTableModel modeloTabela;

    private AgendaDAO agendaDAO = new AgendaDAO();
    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();

    public TelaAgendaCliente(Cliente cliente) {
        this.clienteLogado = cliente;
        initComponents();
        carregarDados();
    }

    private void initComponents() {
        setTitle("Let's Salon - Meus Agendamentos");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(750, 50));

        JLabel lblLogo = new JLabel("  LET'S SALON");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
        lblLogo.setForeground(Color.WHITE);
        painelTopo.add(lblLogo, BorderLayout.WEST);

        JLabel lblNomeCliente = new JLabel("Olá, " + clienteLogado.getNomeCliente() + "  ");
        lblNomeCliente.setFont(new Font("Arial", Font.BOLD, 14));
        lblNomeCliente.setForeground(Color.WHITE);
        painelTopo.add(lblNomeCliente, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // ── FORMULÁRIO ──
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(CoresUtil.ROSA_FUNDO);
        painelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Novo Agendamento",
            0, 0,
            new Font("Arial", Font.BOLD, 13),
            new Color(201, 72, 105)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Data
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Data:"), gbc);
        txtData = new JTextField("Ex: 12/06/2026", 12);
        gbc.gridx = 1;
        painelForm.add(txtData, gbc);

        // Hora
        gbc.gridx = 2;
        painelForm.add(new JLabel("Hora:"), gbc);
        txtHora = new JTextField("Ex: 16:30", 8);
        gbc.gridx = 3;
        painelForm.add(txtHora, gbc);

        // Colaborador
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Profissional:"), gbc);
        cmbColaborador = new JComboBox<>();
        gbc.gridx = 1;
        painelForm.add(cmbColaborador, gbc);

        // Serviço
        gbc.gridx = 2;
        painelForm.add(new JLabel("Serviço:"), gbc);
        cmbServico = new JComboBox<>();
        gbc.gridx = 3;
        painelForm.add(cmbServico, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        painelBotoes.setBackground(CoresUtil.ROSA_FUNDO);

        btnAgendar  = criarBotao("Agendar", new Color(201, 72, 105));
        btnCancelar = criarBotao("Cancelar Agendamento", CoresUtil.VERMELHO_EXCLUIR);
        btnSair     = criarBotao("Sair", CoresUtil.CINZA_VOLTAR);

        painelBotoes.add(btnAgendar);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSair);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        painelForm.add(painelBotoes, gbc);

        add(painelForm, BorderLayout.CENTER);

        // ── TABELA: Meus agendamentos ──
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(CoresUtil.ROSA_FUNDO);
        painelTabela.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Meus Agendamentos",
            0, 0,
            new Font("Arial", Font.BOLD, 13),
            new Color(201, 72, 105)
        ));

        String[] colunas = {"Data", "Hora", "Profissional", "Serviço", "Confirmado"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaMeusAgendamentos = new JTable(modeloTabela);
        tabelaMeusAgendamentos.setRowHeight(28);
        tabelaMeusAgendamentos.getTableHeader().setBackground(new Color(201, 72, 105));
        tabelaMeusAgendamentos.getTableHeader().setForeground(Color.WHITE);
        tabelaMeusAgendamentos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaMeusAgendamentos.setBackground(Color.WHITE);
        tabelaMeusAgendamentos.setGridColor(CoresUtil.ROSA_SECUNDARIO);

        painelTabela.add(new JScrollPane(tabelaMeusAgendamentos));
        add(painelTabela, BorderLayout.SOUTH);

        // Ações
        btnAgendar.addActionListener(e -> realizarAgendamento());
        btnCancelar.addActionListener(e -> cancelarAgendamento());
        btnSair.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    private void carregarDados() {
        // Carrega colaboradores
        cmbColaborador.removeAllItems();
        List<Colaborador> colaboradores = colaboradorDAO.listar();
        for (Colaborador c : colaboradores) {
            cmbColaborador.addItem(c.getIdColaborador() + " - " + c.getNomeColaborador());
        }

        // Carrega serviços
        cmbServico.removeAllItems();
        List<Servico> servicos = servicoDAO.listar();
        for (Servico s : servicos) {
            cmbServico.addItem(s.getCodigoServico() + " - " + s.getNomeServico());
        }

        // Carrega agendamentos do cliente logado
        modeloTabela.setRowCount(0);
        List<Agenda> agendamentos = agendaDAO.listar();
        for (Agenda a : agendamentos) {
            if (a.getCpfCliente().equals(clienteLogado.getCpfCliente())) {
                modeloTabela.addRow(new Object[]{
                    a.getData(),
                    a.getHorario(),
                    a.getIdColaborador(),
                    a.getCodigoServico(),
                    a.getStatus()
                });
            }
        }
    }

    private void realizarAgendamento() {
        if (txtData.getText().isEmpty() || txtHora.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha a data e o horário!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Agenda agenda = new Agenda();
        agenda.setCpfCliente(clienteLogado.getCpfCliente());
        agenda.setData(txtData.getText().trim());
        agenda.setHorario(txtHora.getText().trim());
        agenda.setStatus("AGENDADO");

        if (cmbColaborador.getSelectedItem() != null) {
            String idStr = cmbColaborador.getSelectedItem().toString().split(" - ")[0];
            agenda.setIdColaborador(Integer.parseInt(idStr));
        }
        if (cmbServico.getSelectedItem() != null) {

    String codStr =
        cmbServico.getSelectedItem()
                  .toString()
                  .split(" - ")[0];

    int codigoServico =
        Integer.parseInt(codStr);

    agenda.setCodigoServico(codigoServico);

    for (Servico s : servicoDAO.listar()) {

        if (s.getCodigoServico() == codigoServico) {

            agenda.setCodigoEmpresa(
                s.getCodigoEmpresa()
            );

            break;
        }
    }
}
        agendaDAO.cadastrar(agenda);
        JOptionPane.showMessageDialog(this,
            "Agendamento realizado!\nAguarde a confirmação do salão.",
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);

        limparCampos();
        carregarDados();
    }

    private void cancelarAgendamento() {
        int linha = tabelaMeusAgendamentos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um agendamento para cancelar!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja cancelar este agendamento?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            String data = modeloTabela.getValueAt(linha, 0).toString();
            String hora = modeloTabela.getValueAt(linha, 1).toString();
            for (Agenda a : agendaDAO.listar()) {
                if (a.getCpfCliente().equals(clienteLogado.getCpfCliente())
                        && a.getData().equals(data)
                        && a.getHorario().equals(hora)) {
                    agendaDAO.cancelar(a.getIdAgendamento());
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Agendamento cancelado!");
            carregarDados();
        }
    }
 
    private void limparCampos() {
        txtData.setText("");
        txtHora.setText("");
        cmbColaborador.setSelectedIndex(0);
        cmbServico.setSelectedIndex(0);
    }

    private JButton criarBotao(String texto, Color cor) {
    JButton btn = new JButton(texto);
    btn.setBackground(cor);

    if (cor.equals(CoresUtil.CINZA_VOLTAR) || cor.equals(CoresUtil.VERDE_CONFIRMAR)) {
        btn.setForeground(new Color(60, 60, 60));
    } else {
        btn.setForeground(Color.WHITE);
    }

    btn.setFont(new Font("Arial", Font.BOLD, 12));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return btn;
}
}