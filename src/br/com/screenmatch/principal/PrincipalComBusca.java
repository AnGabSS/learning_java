package br.com.screenmatch.principal;

import br.com.screenmatch.modelos.Titulo;
import br.com.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.util.Scanner;


public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um filme para busca: ");
        String resposta = scanner.nextLine();

        String endereco = "http://www.omdbapi.com/?t=" + resposta.replace(" ", "+") + "&apikey=98fa1575";
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client
                    .send(request, BodyHandlers.ofString());
            String json = response.body();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();
            //Titulo meuTitulo = gson.fromJson(json, Titulo.class);
            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(meuTituloOmdb);
            // try{
            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println("Filme já convertido");
            System.out.println(meuTitulo);
        } catch(NumberFormatException n){
            System.out.println("Aconteceu um erro: ");
            n.printStackTrace();
            System.out.println(n.getMessage());
        } catch(IllegalArgumentException e){
            System.out.println("Algum erro no argumento passado para busca");
            System.out.println(e.getMessage());
        }

        System.out.println("O programa finalizou corretamente!");

    }
}
