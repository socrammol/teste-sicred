/*
Cenário de Negócio:
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de 
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi 
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, 
antes as 10:00 da manhã na abertura das agências.

Requisito:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

Funcionalidade:
0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma 
nova coluna.


Formato CSV:
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
3202;00321-2;34500,00;B
...

*/
package br.com.backend.siced.teste.sicred;

import br.com.backend.siced.teste.sicred.model.DadosUsuarioSicredEntradaModel;
import br.com.backend.siced.teste.sicred.model.DadosUsuarioSicredSaidaModel;
import br.com.backend.siced.teste.sicred.service.CsvService;
import br.com.backend.siced.teste.sicred.service.ReceitaService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@SpringBootApplication
public class SincronizacaoReceita {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SincronizacaoReceita.class, args);
        CsvService csvService = new CsvService();
        ReceitaService receitaService = new ReceitaService();

        for (int i =0 ; i < args.length; i ++){
            if (args[i].toLowerCase(Locale.ROOT).contains(".csv")){
                try {
                    csvService.escreverArquivo(lerCsvEProcessarDados(csvService.lerCsv(args[i]), receitaService));
                } catch (CsvRequiredFieldEmptyException e) {
                    e.printStackTrace();
                } catch (CsvDataTypeMismatchException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Processamento Concluido");

        // Exemplo como chamar o "serviço" do Banco Central.
        // ReceitaService receitaService = new ReceitaService();
        // receitaService.atualizarConta("0101", "123456", 100.50, "A");
    }

    private static List<DadosUsuarioSicredSaidaModel> lerCsvEProcessarDados(List<DadosUsuarioSicredEntradaModel> dadosUsuarioSicredEntradaModels1, ReceitaService receitaService) throws IOException {
        List<DadosUsuarioSicredSaidaModel> dadosUsuarioSicredSaidaModels = new ArrayList();
        List<DadosUsuarioSicredEntradaModel> dadosUsuarioSicredEntradaModels = dadosUsuarioSicredEntradaModels1;
        dadosUsuarioSicredEntradaModels.forEach(dados -> {
            try {
                Boolean retorno =  receitaService.atualizarConta(dados.getAgencia(),dados.getConta(),Double.valueOf(dados.getSaldo()),dados.getStatus());
                DadosUsuarioSicredSaidaModel dadosUsuarioSicredSaidaModel = new DadosUsuarioSicredSaidaModel(dados,retorno);
                dadosUsuarioSicredSaidaModels.add(dadosUsuarioSicredSaidaModel);
            } catch (InterruptedException e) {
                DadosUsuarioSicredSaidaModel dadosUsuarioSicredSaidaModel = new DadosUsuarioSicredSaidaModel(dados,false);
                dadosUsuarioSicredSaidaModels.add(dadosUsuarioSicredSaidaModel);
            }
        });
        return dadosUsuarioSicredSaidaModels;
    }

}
