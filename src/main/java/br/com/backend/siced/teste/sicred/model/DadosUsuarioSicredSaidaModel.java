package br.com.backend.siced.teste.sicred.model;

public class DadosUsuarioSicredSaidaModel {
    private String agencia;
    private String conta;
    private String saldo;
    private String status;
    private boolean processado;

    public DadosUsuarioSicredSaidaModel() {
    }

    public DadosUsuarioSicredSaidaModel(String agencia, String conta, String saldo, String status, boolean processado) {
        this.agencia = agencia;
        this.conta = conta;
        this.saldo = saldo;
        this.status = status;
        this.processado = processado;
    }

    public DadosUsuarioSicredSaidaModel(DadosUsuarioSicredEntradaModel dados, Boolean retorno) {
        this.processado = retorno;
        this.agencia = dados.getAgencia();
        this.conta = dados.getConta();
        this.saldo = dados.getSaldo();
        this.status = dados.getStatus();
    }

    public boolean isProcessado() {
        return processado;
    }

    public void setProcessado(boolean processado) {
        this.processado = processado;
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

}
