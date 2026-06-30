package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class CadastroFuncionarioView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;

    private JTextField txtId, txtNome, txtLogin;
    private JFormattedTextField txtCpf, txtTelefone;
    private JComboBox<String> cbServicos;
    private JPasswordField txtSenha;
    
    private JTable table;
    private DefaultTableModel model;

    public CadastroFuncionarioView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Let's Salon - Cadastro de Funcionários");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());

        // ==========================================
        // 1. NAVBAR SUPERIOR
        // ==========================================
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.decode("#F48FB1"));
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿");
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
        lblNavLogo.setForeground(Color.WHITE);

        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090"));
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JLabel lblNavName = new JLabel(nomeUsuario);
        lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navCenter.setOpaque(false);
        navCenter.add(pillUser);

        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navRight.setOpaque(false);
        
        JLabel lblStudio = new JLabel(nomeEmpresa);
        lblStudio.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblStudio.setForeground(Color.WHITE);

        // BOTÃO VOLTAR
        JButton btnVoltar = new JButton("↩ Voltar");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            this.dispose();
            new MainAdministradorView(nomeUsuario, nomeEmpresa).setVisible(true);
        });

        navRight.add(lblStudio);
        navRight.add(btnVoltar);

        navbar.add(lblNavLogo, BorderLayout.WEST);
        navbar.add(navCenter, BorderLayout.CENTER);
        navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // ==========================================
        // 2. CONTEÚDO PRINCIPAL (Formulário e Foto)
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 50, 80));

        // TÍTULO DA PÁGINA
        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleWrapper.setOpaque(false);
        titleWrapper.setMaximumSize(new Dimension(9999, 50));
        JLabel lblTitle = new JLabel("Cadastro de Funcionários(as)");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        lblTitle.setForeground(Color.decode("#FF6090"));
        titleWrapper.add(lblTitle);
        contentPanel.add(titleWrapper);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // PAINEL DIVIDIDO (Esquerda: Form | Direita: Imagem)
        JPanel splitPanel = new JPanel(new BorderLayout(100, 0));
        splitPanel.setOpaque(false);
        splitPanel.setMaximumSize(new Dimension(1200, 350));

        // ---> ESQUERDA: FORMULÁRIO (CRUD)
        JPanel formLeft = new JPanel(new GridBagLayout());
        formLeft.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 14);
        Color labelColor = Color.decode("#D81B60");
        Dimension fieldSize = new Dimension(320, 35);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(Color.decode("#F5F5F5"));
        
        txtNome = new JTextField();
        
        txtCpf = new JFormattedTextField();
        configurarCampoComMascara(txtCpf, "###.###.###-##");
        
        txtTelefone = new JFormattedTextField();
        configurarCampoComMascara(txtTelefone, "(##) #####-####");

        // ComboBox para os Serviços
        String[] servicosIniciais = {"Selecione um serviço...", "Cabelereiro(a)", "Manicure/Pedicure", "Maquiador(a)", "Esteticista", "Massagista"};
        cbServicos = new JComboBox<>(servicosIniciais);
        cbServicos.setBackground(Color.WHITE);
        
        txtLogin = new JTextField();
        txtSenha = new JPasswordField();

        adicionarCampo(formLeft, gbc, 0, "Id:", txtId, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 1, "Nome:", txtNome, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 2, "CPF:", txtCpf, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 3, "Telefone:", txtTelefone, labelFont, labelColor, fieldSize);
        
        // Adicionando o JComboBox manualmente
        JLabel lblServ = new JLabel("Serviços:");
        lblServ.setFont(labelFont); lblServ.setForeground(labelColor);
        lblServ.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.2;
        formLeft.add(lblServ, gbc);
        cbServicos.setPreferredSize(fieldSize);
        cbServicos.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        gbc.gridx = 1; gbc.weightx = 0.8;
        formLeft.add(cbServicos, gbc);

        adicionarCampo(formLeft, gbc, 5, "Login:", txtLogin, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 6, "Senha:", txtSenha, labelFont, labelColor, fieldSize);

        // BOTÕES CRUD
        JPanel crudButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        crudButtons.setOpaque(false);
        RoundedButton btnCadastrar = new RoundedButton("Cadastrar");
        RoundedButton btnAtualizar = new RoundedButton("Atualizar");
        RoundedButton btnExcluir = new RoundedButton("Excluir");
        RoundedButton btnLimpar = new RoundedButton("Limpar");

        RoundedButton[] botoes = {btnCadastrar, btnAtualizar, btnExcluir, btnLimpar};
        for (RoundedButton btn : botoes) {
            btn.setPreferredSize(new Dimension(100, 35));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            crudButtons.add(btn);
        }
        
        gbc.gridx = 1; gbc.gridy = 7; gbc.insets = new Insets(20, 5, 0, 5);
        formLeft.add(crudButtons, gbc);
        
        splitPanel.add(formLeft, BorderLayout.WEST);

        // ---> DIREITA: ÍCONE/FOTO DO FUNCIONÁRIO
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setOpaque(false);
        iconPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 150)); // Afasta um pouco da margem direita
        
        // Ícone de usuário gigante simulando a sua imagem rosa
        JLabel lblFotoPlaceholder = new JLabel("👤", SwingConstants.CENTER);
        lblFotoPlaceholder.setFont(new Font("Segoe UI", Font.PLAIN, 200));
        lblFotoPlaceholder.setForeground(Color.decode("#FF6090"));
        iconPanel.add(lblFotoPlaceholder);

        splitPanel.add(iconPanel, BorderLayout.CENTER);
        
        contentPanel.add(splitPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // ==========================================
        // 3. TABELA DE FUNCIONÁRIOS
        // ==========================================
        JPanel searchBar = new JPanel(new BorderLayout());
        searchBar.setOpaque(false);
        searchBar.setMaximumSize(new Dimension(9999, 40));
        
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlBusca.setOpaque(false);
        JLabel lblBusca = new JLabel("Buscar:");
        lblBusca.setFont(labelFont); lblBusca.setForeground(labelColor);
        JTextField txtBusca = new JTextField(20);
        txtBusca.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        txtBusca.setPreferredSize(new Dimension(200, 35));
        pnlBusca.add(lblBusca); pnlBusca.add(txtBusca);
        
        JLabel lblFiltrar = new JLabel("Filtrar ≡");
        lblFiltrar.setFont(labelFont); lblFiltrar.setForeground(labelColor);
        searchBar.add(pnlBusca, BorderLayout.WEST);
        searchBar.add(lblFiltrar, BorderLayout.EAST);
        contentPanel.add(searchBar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ... (Seu código da Navbar, Formulário e Painel de Busca permanecem iguais até aqui) ...

        String[] colunas = {"Id", "Nome", "CPF", "Telefone", "Serviços", "Login", "Senha"};
        model = new DefaultTableModel(new Object[][]{}, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table = new JTable(model);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(table.getValueAt(linha, 0).toString());
                    txtNome.setText(table.getValueAt(linha, 1).toString());
                    txtCpf.setText(table.getValueAt(linha, 2).toString());
                    txtTelefone.setText(table.getValueAt(linha, 3).toString());
                    cbServicos.setSelectedItem(table.getValueAt(linha, 4).toString());
                    txtLogin.setText(table.getValueAt(linha, 5).toString());
                    txtSenha.setText(table.getValueAt(linha, 6).toString());
                }
            }
        });
        
        table.setRowHeight(40);
        table.setSelectionBackground(Color.decode("#FFCDD2"));
        table.setSelectionForeground(Color.decode("#D81B60"));
        table.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        table.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 6)); 
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setMaximumSize(new Dimension(9999, 400));
        tableContainer.setPreferredSize(new Dimension(1000, 300));
        tableContainer.add(scrollTable, BorderLayout.CENTER);

        contentPanel.add(tableContainer);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JScrollPane mainScroll = new JScrollPane(contentPanel);
        mainScroll.setBorder(null);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.getVerticalScrollBar().setUnitIncrement(20); 
        
        mainBg.add(mainScroll, BorderLayout.CENTER);
        add(mainBg);

        // ==========================================
        // 4. LÓGICA DO CRUD E BANCO DE DADOS
        // ==========================================
        salon.controller.FuncionarioController funcionarioController = new salon.controller.FuncionarioController();
        int idEmpresaSelecionada = 1; // FIXO APENAS PARA TESTES. No futuro receberemos via construtor!

        // CADASTRAR
        btnCadastrar.addActionListener(e -> {
            try {
                funcionarioController.cadastrarFuncionario(
                    txtNome.getText(), txtCpf.getText(), txtTelefone.getText(),
                    txtLogin.getText(), new String(txtSenha.getPassword()),
                    cbServicos.getSelectedItem().toString(), idEmpresaSelecionada
                );
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
                carregarTabela(funcionarioController, idEmpresaSelecionada);
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ATUALIZAR
        btnAtualizar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtId.getText());
                funcionarioController.atualizarFuncionario(
                    id, txtNome.getText(), txtCpf.getText(), txtTelefone.getText(),
                    txtLogin.getText(), new String(txtSenha.getPassword()),
                    cbServicos.getSelectedItem().toString()
                );
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
                carregarTabela(funcionarioController, idEmpresaSelecionada);
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // EXCLUIR
        btnExcluir.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este funcionário?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    funcionarioController.excluirFuncionario(id);
                    JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                    carregarTabela(funcionarioController, idEmpresaSelecionada);
                    limparCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpar.addActionListener(e -> limparCampos());

        // Carrega a tabela ao abrir a tela
        carregarTabela(funcionarioController, idEmpresaSelecionada);
    }

    // ==========================================
    // MÉTODOS AUXILIARES
    // ==========================================
    
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setValue(null);
        txtTelefone.setValue(null);
        cbServicos.setSelectedIndex(0);
        txtLogin.setText("");
        txtSenha.setText("");
        table.clearSelection();
    }

    private void carregarTabela(salon.controller.FuncionarioController controller, int idEmpresa) {
        model.setRowCount(0); // Limpa as linhas visuais
        try {
            java.util.List<salon.model.Funcionario> lista = controller.listarFuncionarios(idEmpresa);
            for (salon.model.Funcionario func : lista) {
                model.addRow(new Object[]{
                    func.getIdFuncionario(),
                    func.getNome(),
                    func.getCpf(),
                    func.getTelefone(),
                    func.getCargo(),
                    func.getLogin(),
                    func.getSenha() // Num sistema real a senha não vem pra tabela, mas como está no design, mantemos!
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarCampoComMascara(JFormattedTextField campo, String mascara) {
        try {
            MaskFormatter mf = new MaskFormatter(mascara);
            mf.setPlaceholderCharacter('_'); 
            mf.install(campo);
        } catch (Exception e) {}
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JTextField campo, Font fonteLabel, Color corLabel, Dimension tamanhoCampo) {
        JLabel lbl = new JLabel(textoLabel);
        lbl.setFont(fonteLabel);
        lbl.setForeground(corLabel);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.2;
        painel.add(lbl, gbc);

        campo.setPreferredSize(tamanhoCampo);
        campo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        gbc.gridx = 1; gbc.weightx = 0.8;
        painel.add(campo, gbc);
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme();
        SwingUtilities.invokeLater(() -> new CadastroFuncionarioView("RhaianeAll", "Madame Studio").setVisible(true));
    }
}