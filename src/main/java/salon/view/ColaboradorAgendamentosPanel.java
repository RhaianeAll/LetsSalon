package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import salon.view.components.RoundedPanel;

public class ColaboradorAgendamentosPanel extends JPanel {

    public ColaboradorAgendamentosPanel() {
        setOpaque(false); // Transparente para usar a cor de fundo do MainColaboradorView
        setLayout(new BorderLayout(30, 30));
        setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50)); // Margens generosas

        // 1. Cabeçalho da Página
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Meus Agendamentos");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        lblTitle.setForeground(Color.decode("#D81B60"));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // 2. Card Branco Central (Onde fica a tabela)
        RoundedPanel card = new RoundedPanel(30, Color.WHITE);
        card.setLayout(new BorderLayout(0, 20));
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // 2.1 Barra de Ferramentas (Buscar e Filtrar)
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        toolbar.setOpaque(false);
        
        JLabel lblBuscar = new JLabel("🔍 Filtrar por Data:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscar.setForeground(Color.decode("#555555"));
        
        JTextField txtBusca = new JTextField(15);
        txtBusca.setPreferredSize(new Dimension(200, 35));
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(Color.decode("#F48FB1"));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        
        toolbar.add(lblBuscar);
        toolbar.add(txtBusca);
        toolbar.add(btnBuscar);

        card.add(toolbar, BorderLayout.NORTH);

        // 2.2 Tabela de Agendamentos Customizada
        String[] colunas = {"Data", "Hora", "Cliente", "Serviço", "Status"};
        // Dados fictícios por enquanto (Em breve puxaremos do Banco pelo DAO)
        Object[][] dados = {
            {"25/06/2026", "14:30", "Patrícia Bravanel", "Lash Lifting", "Confirmado"},
            {"26/06/2026", "10:00", "Mariana Silva", "Design de Sobrancelha", "Pendente"},
            {"26/06/2026", "17:00", "Emeli Sechi", "Massagem Relaxante", "Confirmado"}
        };
        
        DefaultTableModel model = new DefaultTableModel(dados, colunas);
        JTable table = new JTable(model);
        
        // Estilizando a Tabela para parecer um sistema moderno
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(Color.decode("#FFCDD2")); // Linha selecionada fica rosa claro
        table.setSelectionForeground(Color.decode("#D81B60"));
        table.setShowVerticalLines(false);
        table.setGridColor(Color.decode("#EEEEEE"));
        
        // Estilizando o Cabeçalho da Tabela
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.decode("#FAFAFA"));
        table.getTableHeader().setForeground(Color.decode("#D81B60"));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#FFCDD2")));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Tira a borda feia do JScrollPane
        scrollPane.getViewport().setBackground(Color.WHITE);

        card.add(scrollPane, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }
}