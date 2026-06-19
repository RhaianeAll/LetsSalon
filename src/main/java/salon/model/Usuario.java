package salon.model;

public class Usuario {
    private int idUsuario;
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private String usuarioLogin;
    private String senha;
    private String tipoUsuario;

    public Usuario(int idUsuario, String nomeCompleto, String cpf, String telefone, String usuarioLogin, String senha, String tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.telefone = telefone;
        this.usuarioLogin = usuarioLogin;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    
    public Usuario() {}

    public String getUsuarioLogin() { 
        return usuarioLogin; 
    }
    
    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin; 
    }
    // ... implementar demais getters e setters

    public byte[] getFoto() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}