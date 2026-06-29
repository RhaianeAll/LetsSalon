package com.letssalon.view;

import com.letssalon.dao.ClienteDAO;
import com.letssalon.model.Cliente;
import com.letssalon.util.SenhaUtil;
import com.letssalon.util.CPFUtil;
import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import com.letssalon.util.CoresUtil;
import com.letssalon.util.BotaoUtil;

public class TelaInscricaoCliente extends JFrame {

    private JTextField txtNome, txtEmailCliente;
    private JFormattedTextField txtCpf, txtTelefone, txtDataNascimento;
    private JPasswordField txtSenha, txtConfirmarSenha;
private JButton btnInscrever, btnLimpar, btnVoltar;

    public TelaInscricaoCliente() {
        initComponents();
    }

    private void initComponents() {
        // Tela de cadastro exclusiva para novos clientes
        setTitle("Let's Salon - Inscrição Cliente");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── PAINEL ESQUERDO: Logo ──
        JPanel painelEsquerdo = new JPanel(new GridBagLayout());
        painelEsquerdo.setBackground(CoresUtil.ROSA_FUNDO);
        painelEsquerdo.setPreferredSize(new Dimension(320, 550));

        JLabel lblNome = new JLabel("Let's Salon.");
        lblNome.setFont(new Font("Serif", Font.ITALIC, 42));
        lblNome.setForeground(new Color(201, 72, 105));
        painelEsquerdo.add(lblNome);

        add(painelEsquerdo, BorderLayout.WEST);

        // ── PAINEL DIREITO: Formulário ──
        JPanel painelDireito = new JPanel(new GridBagLayout());
        painelDireito.setBackground(CoresUtil.ROSA_PAINEL_FORM);
        painelDireito.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Título
        JLabel lblTitulo = new JLabel("INSCREVA-SE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(201, 72, 105));
        gbc.gridy = 0;
        painelDireito.add(lblTitulo, gbc);

        // Nome
        gbc.gridy = 1;
        painelDireito.add(new JLabel("Nome Completo:"), gbc);

        txtNome = new JTextField(20);

        gbc.gridy = 2;
        painelDireito.add(txtNome, gbc);


        // CPF
        gbc.gridy = 3;
        painelDireito.add(new JLabel("CPF:"), gbc);

        try {

            MaskFormatter mascaraCpf =
            new MaskFormatter("###.###.###-##");

            mascaraCpf.setPlaceholderCharacter('_');

            txtCpf = new JFormattedTextField(mascaraCpf);

    }  catch (Exception e) {

        txtCpf = new JFormattedTextField();
    }

        gbc.gridy = 4;
        painelDireito.add(txtCpf, gbc);


        // Telefone
        gbc.gridy = 5;
        painelDireito.add(
            new JLabel("Número de Telefone (Whatsapp):"),
            gbc
    );

        try {

            MaskFormatter mascaraTelefone =
            new MaskFormatter("(##) #####-####");

            mascaraTelefone.setPlaceholderCharacter('_');
    
            txtTelefone = new JFormattedTextField(
            mascaraTelefone
     );

    }  catch (Exception e) {

            txtTelefone = new JFormattedTextField();
    }

            gbc.gridy = 6;
            painelDireito.add(txtTelefone, gbc);


            // E-mail
            gbc.gridy = 7;
            painelDireito.add(new JLabel("E-mail:"), gbc);

            txtEmailCliente = new JTextField(20);

            gbc.gridy = 8;
            painelDireito.add(txtEmailCliente, gbc);
        
            // Data de nascimento
            gbc.gridy = 9;
            painelDireito.add(
            new JLabel("Data de Nascimento:"),
            gbc
);

        try {

            MaskFormatter mascaraData =
                new MaskFormatter("##/##/####");

            mascaraData.setPlaceholderCharacter('_');

            txtDataNascimento =
            new JFormattedTextField(mascaraData);

}           catch (Exception e) {

            txtDataNascimento =
                new JFormattedTextField();
}

    gbc.gridy = 10;
    painelDireito.add(txtDataNascimento, gbc);

        // Campo Senha
        gbc.gridy = 11;
        painelDireito.add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField(20);
        gbc.gridy = 12;
        painelDireito.add(
        SenhaUtil.criarPainelSenha(txtSenha),
        gbc);

        // Campo Confirmar Senha
        gbc.gridy = 13;
        painelDireito.add(new JLabel("Confirme a Senha:"), gbc);
       txtConfirmarSenha = new JPasswordField(20);
        gbc.gridy = 14;
        painelDireito.add(
        SenhaUtil.criarPainelSenha(txtConfirmarSenha),
        gbc);

        // Botões
        btnInscrever = BotaoUtil.criarBotao("INSCREVER-SE", new Color(201, 72, 105));
        gbc.gridy = 15;
        painelDireito.add(btnInscrever, gbc);
        
        btnLimpar = BotaoUtil.criarBotao(
        "LIMPAR",
        CoresUtil.ROSA_PAINEL_FORM);

        gbc.gridy = 16;
        painelDireito.add(btnLimpar, gbc);

        btnVoltar = BotaoUtil.criarBotao("VOLTAR", CoresUtil.CINZA_VOLTAR);
        gbc.gridy = 17;
        painelDireito.add(btnVoltar, gbc);

        add(painelDireito, BorderLayout.CENTER);

        // ── AÇÕES ──
        btnInscrever.addActionListener(e -> realizarInscricao());

        btnVoltar.addActionListener(e -> {
            new TelaLoginCliente().setVisible(true);
            dispose();
        });
        btnLimpar.addActionListener(e -> limparCampos());
    }

    private void realizarInscricao() {
        String senha = new String(txtSenha.getPassword()).trim();
        String confirmar = new String(txtConfirmarSenha.getPassword()).trim();

        // Validação: campos obrigatórios
        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty()
                || txtEmailCliente.getText().isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos obrigatórios!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação: senhas coincidem
        if (!senha.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                "As senhas não coincidem!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmailCliente.getText().trim();   

        // CAMPOS VAZIOS
        if (
        nome.isEmpty() ||
        cpf.isEmpty() ||
        telefone.isEmpty() ||
        email.isEmpty() ||
        senha.isEmpty()
) {

            JOptionPane.showMessageDialog(
             this,
             "Preencha todos os campos!",
             "Campos obrigatórios",
             JOptionPane.WARNING_MESSAGE
    );  

    return;
}
        //Validar e-mail
        if (!email.contains("@") || !email.contains(".")) {

    JOptionPane.showMessageDialog(
        this,
        "Digite um e-mail válido!",
        "E-mail inválido",
        JOptionPane.WARNING_MESSAGE
    );

    return;
}
        //Validar senha
        if (senha.length() < 6) {

    JOptionPane.showMessageDialog(
        this,
        "A senha deve ter pelo menos 6 caracteres!",
        "Senha fraca",
        JOptionPane.WARNING_MESSAGE
    );

    return;
}
        if (cpf.contains("_")) {

    JOptionPane.showMessageDialog(
        this,
        "CPF incompleto!",
        "Erro",
        JOptionPane.WARNING_MESSAGE
    );

    return;
}
        //Validar tefefone
        if (telefone.contains("_")) {

    JOptionPane.showMessageDialog(
        this,
        "Telefone incompleto!",
        "Erro",
        JOptionPane.WARNING_MESSAGE
    );

    return;
}
        //Validar CPF
        if (!CPFUtil.validarCPF(cpf)) {

    JOptionPane.showMessageDialog(
        this,
        "CPF inválido!",
        "Erro",
        JOptionPane.WARNING_MESSAGE
    );

    return;
}
        //Validar data de nascimento incompleta
        String dataNascimento =
    txtDataNascimento.getText().trim();

if (dataNascimento.contains("_")) {

    JOptionPane.showMessageDialog(
        this,
        "Data de nascimento incompleta!",
        "Erro",
        JOptionPane.WARNING_MESSAGE
    );

    return;
}
        // Cria o objeto cliente e salva no banco
        Cliente cliente = new Cliente();
        cliente.setNomeCliente(txtNome.getText().trim());
        cliente.setCpfCliente(txtCpf.getText().trim());
        cliente.setTelefoneCliente(txtTelefone.getText().trim());
        cliente.setEmailCliente(txtEmailCliente.getText().trim());
        cliente.setDataNascimento(txtDataNascimento.getText().trim());
        cliente.setSenhaCliente(senha);
        cliente.setPerfil("cliente");

        ClienteDAO clienteDAO = new ClienteDAO();

boolean sucesso =
    clienteDAO.cadastrar(cliente);

if (sucesso) {

    JOptionPane.showMessageDialog(
        this,
        "Cadastro realizado com sucesso!\nAgora faça seu login."
    );

    new TelaLoginCliente().setVisible(true);
    dispose();

} else {

    JOptionPane.showMessageDialog(
        this,
        "Erro ao realizar cadastro!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
    );
}       
    }
    
    private void limparCampos() {

        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmailCliente.setText("");
        txtDataNascimento.setText("");

        txtSenha.setText("");
        txtConfirmarSenha.setText("");

        txtNome.requestFocus();
}
 
}