package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import salon.controller.AgendamentoController;
import salon.controller.UsuarioController;
import salon.model.Agendamento;
import salon.model.Usuario;

import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class MainAdministradorView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;

    public MainAdministradorView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Let's Salon - Painel do Administrador");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fundo com a marca d'água
        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());
        setContentPane(mainBg);
        
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
        pillUser.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        // Clicar na pílula abre a edição de dados
        pillUser.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose(); 
                new EditarDadosAdminView(nomeUsuario, nomeEmpresa).setVisible(true); 
            }
        });

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

        JButton btnSair = new JButton("🚪 Sair");
        btnSair.setContentAreaFilled(false);
        btnSair.setBorderPainted(false);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setForeground(Color.WHITE);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> {
            this.dispose();
            new AuthView().setVisible(true);
        });

        navRight.add(lblStudio);
        navRight.add(btnSair);

        navbar.add(lblNavLogo, BorderLayout.WEST);
        navbar.add(navCenter, BorderLayout.CENTER);
        navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // ==========================================
        // 2. CONTEÚDO PRINCIPAL (Scroll)
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // --- TOPO: PERFIL E NOTIFICAÇÕES ---
        JPanel topSection = new JPanel(new GridLayout(1, 2, 50, 0));
        topSection.setOpaque(false);
        topSection.setMaximumSize(new Dimension(1200, 180));

        // ESQUERDA: FOTO E INFO DO PERFIL
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        profilePanel.setOpaque(false);

        JLabel lblFotoPerfil = new JLabel();
        lblFotoPerfil.setPreferredSize(new Dimension(150, 150));
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4, true));
        lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);

        // A MÁGICA DA FOTO: Busca o usuário para carregar a imagem
        Usuario adminLogado = new UsuarioController().buscarUsuarioPorLogin(nomeUsuario);
        if (adminLogado != null && adminLogado.getFoto() != null && adminLogado.getFoto().length > 0) {
            ImageIcon icon = new ImageIcon(adminLogado.getFoto());
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblFotoPerfil.setIcon(new ImageIcon(img));
        } else {
            lblFotoPerfil.setText("Sem Foto");
            lblFotoPerfil.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblFotoPerfil.setForeground(Color.GRAY);
            lblFotoPerfil.setBackground(Color.decode("#E0E0E0"));
            lblFotoPerfil.setOpaque(true);
        }

        JPanel infoProfilePanel = new JPanel();
        infoProfilePanel.setLayout(new BoxLayout(infoProfilePanel, BoxLayout.Y_AXIS));
        infoProfilePanel.setOpaque(false);

        JLabel lblBemVindo = new JLabel(adminLogado != null && adminLogado.getNomeCompleto() != null ? adminLogado.getNomeCompleto() : nomeUsuario);
        lblBemVindo.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
        
        String tipoFormatado = adminLogado != null ? adminLogado.getTipoUsuario() : "Colaborador(a)";
        tipoFormatado = tipoFormatado.substring(0, 1).toUpperCase() + tipoFormatado.substring(1);
        JLabel lblCargo = new JLabel(tipoFormatado);
        lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblCargo.setForeground(Color.DARK_GRAY);

        infoProfilePanel.add(Box.createRigidArea(new Dimension(0, 35))); // Centraliza o texto com a foto
        infoProfilePanel.add(lblBemVindo);
        infoProfilePanel.add(lblCargo);

        profilePanel.add(lblFotoPerfil);
        profilePanel.add(infoProfilePanel);
        topSection.add(profilePanel);

        // DIREITA: PAINEL DE NOTIFICAÇÕES (MÁGICO)
        JPanel notifPanel = new JPanel();
        notifPanel.setLayout(new BoxLayout(notifPanel, BoxLayout.Y_AXIS));
        notifPanel.setOpaque(false);

        JLabel lblNotifTitle = new JLabel("Notificações:");
        lblNotifTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        lblNotifTitle.setForeground(Color.decode("#D81B60"));
        lblNotifTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedPanel notifBox = new RoundedPanel(20, Color.WHITE);
        notifBox.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 3));
        notifBox.setLayout(new BorderLayout());
        notifBox.setPreferredSize(new Dimension(400, 100));

        // Busca dados reais para a notificação
        AgendamentoController agendamentoController = new AgendamentoController();
        int pendentes = agendamentoController.contarPendentes(nomeEmpresa);
        List<Agendamento> antigos = agendamentoController.listarConcluidosAntigos(nomeEmpresa);
        
        String textoNotificacao = "<html><center>";
        if (pendentes == 0 && antigos.isEmpty()) {
            textoNotificacao += "Nenhuma nova notificação no momento.";
        } else {
            if (pendentes > 0) {
                textoNotificacao += "⚠️ Você possui <b>" + pendentes + "</b> solicitação(ões) de agendamento pendente(s).<br>";
            }
            if (!antigos.isEmpty()) {
                textoNotificacao += "🔔 Existem atendimentos realizados há mais de 15 dias!";
            }
        }
        textoNotificacao += "</center></html>";

        JLabel lblNotificacaoTexto = new JLabel(textoNotificacao);
        lblNotificacaoTexto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNotificacaoTexto.setHorizontalAlignment(SwingConstants.CENTER);
        
        notifBox.add(lblNotificacaoTexto, BorderLayout.CENTER);

        notifPanel.add(lblNotifTitle);
        notifPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        notifPanel.add(notifBox);
        topSection.add(notifPanel);

        contentPanel.add(topSection);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        // --- MEIO: BOTÕES DE MENU ---
        JPanel buttonsGrid = new JPanel(new GridLayout(2, 3, 30, 30));
        buttonsGrid.setOpaque(false);
        buttonsGrid.setMaximumSize(new Dimension(1000, 200));

        String[] menuItems = {
            "Agendamentos", "Cadastro de Insumos", "Cadastro de Profissionais", 
            "Estoque", "Cadastro de Serviços", "Relatórios"
        };

        for (String item : menuItems) {
            RoundedButton btn = new RoundedButton(item);
            btn.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
            
            btn.addActionListener(e -> {
                this.dispose();
                switch(item) {
                    case "Agendamentos": new AgendamentosView(nomeUsuario, nomeEmpresa).setVisible(true); break;
                    case "Cadastro de Profissionais": new CadastroFuncionarioView(nomeUsuario, nomeEmpresa).setVisible(true); break;
                    case "Cadastro de Serviços": new CadastroServicoView(nomeUsuario, nomeEmpresa).setVisible(true); break;
                    case "Cadastro de Insumos": new CadastroInsumoView(nomeUsuario, nomeEmpresa).setVisible(true); break;
                    case "Estoque": new EstoqueView(nomeUsuario, nomeEmpresa).setVisible(true); break;
                    case "Relatórios": new RelatoriosMenuView(nomeUsuario, nomeEmpresa).setVisible(true); break;
                }
            });
            buttonsGrid.add(btn);
        }

        contentPanel.add(buttonsGrid);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 60)));

        // --- INFERIOR: TABELA DE RETORNO (Clientes de 15 dias atrás) ---
        JPanel tableSection = new JPanel(new BorderLayout(0, 10));
        tableSection.setOpaque(false);
        tableSection.setMaximumSize(new Dimension(1000, 300));
        
        JLabel lblTableTitle = new JLabel("Lembrete de Retorno (Atendimentos > 15 dias)");
        lblTableTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        lblTableTitle.setForeground(Color.decode("#D81B60"));
        tableSection.add(lblTableTitle, BorderLayout.NORTH);

        String[] colunas = {"Data", "Hora", "Cliente", "Serviço Realizado"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, colunas) { 
            @Override public boolean isCellEditable(int r, int c) { return false; } 
        };
        
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        table.setSelectionBackground(Color.decode("#FFCDD2"));
        
        // Povoa a tabela
        for (Agendamento a : antigos) {
            String dataF = a.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            model.addRow(new Object[]{dataF, a.getHora().toString(), a.getNomeCliente(), a.getNomeServico()});
        }
        
        if (antigos.isEmpty()) {
            model.addRow(new Object[]{"-", "-", "Nenhum cliente para contactar no momento", "-"});
        }

        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4));
        scrollTable.setPreferredSize(new Dimension(1000, 180));
        tableSection.add(scrollTable, BorderLayout.CENTER);

        contentPanel.add(tableSection);

        // Adiciona Scroll no painel principal
        JScrollPane mainScroll = new JScrollPane(contentPanel);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(25);
        mainBg.add(mainScroll, BorderLayout.CENTER);

        setVisible(true);
    }
}