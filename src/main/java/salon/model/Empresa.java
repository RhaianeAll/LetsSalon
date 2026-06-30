package salon.model;

public class Empresa {
    private int idEmpresa;
    private String nomeEmpresa;
    private String telefone;
    private String email;

    public Empresa() {}

    public Empresa(int idEmpresa, String nomeEmpresa, String telefone, String email) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.telefone = telefone;
        this.email = email;
    }

    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getTelefoneEmpresa() { return telefone; }
    public void setTelefoneEmpresa(String telefone) { this.telefone = telefone; }

    public String getEmailEmpresa() { return email; }
    public void setEmailEmpresa(String email) { this.email = email; }
}