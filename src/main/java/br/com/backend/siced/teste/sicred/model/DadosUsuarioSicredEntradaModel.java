package br.com.backend.siced.teste.sicred.model;

public class DadosUsuarioSicredEntradaModel {
    private String agencia;
    private String conta;
    private String saldo;
    private String status;

    public DadosUsuarioSicredEntradaModel(String agencia, String conta, String saldo, String status) {
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.status = status;
    }

    public DadosUsuarioSicredEntradaModel() {
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DadosUsuarioSicredModel{" +
                "agencia=" + agencia +
                ", conta='" + conta + '\'' +
                ", saldo='" + saldo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
