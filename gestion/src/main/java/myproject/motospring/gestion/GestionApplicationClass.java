package myproject.motospring.gestion;


import myproject.motospring.gestion.dto.RaceDTO;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class GestionApplicationClass {
    private static String serviceUrl = "http://localhost:8084/race/";
    private static String race = " {\n" +
            "        \"name\": \"Raliuqq\",\n" +
            "        \"engineCapacity\": \"CM_500\"\n" +
            "    }";
    private static String race2 = " {\n" +
            "        \"name\": \"Raliuqq\",\n" +
            "        \"engineCapacity\": \"CM_125\"\n" +
            "    }";
    private static String raceUpdated = " {\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"MEOW-MEOW\",\n" +
            "        \"engineCapacity\": \"CM_500\"\n" +
            "    }";

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

        HttpClient client = HttpClient.newHttpClient();

        //Add
        HttpRequest postReq = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(race))
                .build();
        String responseAdd = client
                .send(postReq, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Added:" + responseAdd);

        //Add
        HttpRequest postReq2 = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(race))
                .build();
        String responseAdd2 = client
                .send(postReq2, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Added:" + responseAdd2);

        //Add
        HttpRequest postReq3 = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(race))
                .build();
        String responseAdd3 = client
                .send(postReq3, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Added:" + responseAdd3);

        //Add
        HttpRequest postReq4 = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(race2))
                .build();
        String responseAdd4 = client
                .send(postReq4, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Added:" + responseAdd4);

        //GetAll
        HttpRequest getReg = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl + "all"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        String responseGet = client.send(getReg, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Get All: " + responseGet);

        //Delete
        HttpRequest deleteReq = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl + "2"))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        String responseDelete = client
                .send(deleteReq, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Deleted" + responseDelete);

        //GetAll
        HttpRequest getReg2 = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl + "all"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        String responseGet2 = client.send(getReg2, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Get All: " + responseGet2);

        //Update
        HttpRequest updateReq = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(raceUpdated))
                .build();
        String responseUpdate = client
                .send(updateReq, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("ModifiedTo:" + responseUpdate);

        //GetAll
        HttpRequest getReg3 = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl + "all"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        String responseGet3 = client.send(getReg3, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Get All: " + responseGet3);

        //SearchByEngCap
        HttpRequest getRegSearch = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl + "search?engineSize=CM_500"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        String responseGetSearch = client.send(getRegSearch, HttpResponse.BodyHandlers.ofString())
                .body();
        System.out.println("Search by engineSize: " + responseGetSearch);
    }

    //
//        List<URI> targets = Arrays.asList(
//                new URI(serviceUrl)
//        );
//        HttpClient client = HttpClient.newHttpClient();
//        var futures = targets.stream()
//                .map(target -> {
//                    try {
//                        return client
//                                .sendAsync(
//                                        HttpRequest.newBuilder(  target)
//                                                .uri(target)
//                                                .header("Content-Type", "application/json")
//                                                .POST(HttpRequest.BodyPublishers.ofString(race))
//                                                .build(),
//                                        HttpResponse.BodyHandlers.ofString())
//                                .thenApply(response -> response.body())
//                                .get();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    } catch (ExecutionException e) {
//                        throw new RuntimeException(e);
//                    }
//                }).collect(Collectors.toList());
//
//        System.out.println(futures);
}
