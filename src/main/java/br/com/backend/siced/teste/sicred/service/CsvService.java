package br.com.backend.siced.teste.sicred.service;

import br.com.backend.siced.teste.sicred.model.DadosUsuarioSicredEntradaModel;
import br.com.backend.siced.teste.sicred.model.DadosUsuarioSicredSaidaModel;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    public List<DadosUsuarioSicredEntradaModel> lerCsv(String fle) throws IOException {
        List<DadosUsuarioSicredEntradaModel> dadosUsuarioSicredEntradaModels = new ArrayList();

        Reader reader = Files.newBufferedReader(Paths.get(fle));
        CsvToBean<DadosUsuarioSicredEntradaModel> csvToBean = new CsvToBeanBuilder(reader)
                .withType(DadosUsuarioSicredEntradaModel.class)
                .withSeparator(';')
                .build();

        dadosUsuarioSicredEntradaModels = csvToBean.parse();
        dadosUsuarioSicredEntradaModels.forEach(dados -> {
            dados.setSaldo(dados.getSaldo().replace(",","."));
            dados.setConta(dados.getConta().replace("-",""));
        });


        return dadosUsuarioSicredEntradaModels;
    }


    public void escreverArquivo(List<DadosUsuarioSicredSaidaModel> dadosUsuarioSicredSaidaModels) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        dadosUsuarioSicredSaidaModels.forEach(dados ->{
            dados.setSaldo(dados.getSaldo().replace(".",","));
        });
        Writer writer = Files.newBufferedWriter(Paths.get("retorno.csv"));
        StatefulBeanToCsv<DadosUsuarioSicredSaidaModel> beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

        beanToCsv.write(dadosUsuarioSicredSaidaModels);

        writer.flush();
        writer.close();
    }
}
