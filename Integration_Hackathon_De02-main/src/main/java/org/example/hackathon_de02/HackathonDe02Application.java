package org.example.hackathon_de02;

import org.example.hackathon_de02.service.DatabaseInitializeService;
import org.example.hackathon_de02.service.RAGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class HackathonDe02Application implements CommandLineRunner {

    @Autowired
    private DatabaseInitializeService databaseInitializeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RAGService ragService;

    public static void main(String[] args) {
        SpringApplication.run(HackathonDe02Application.class, args);
    }

    @Override
    public void run(String... args) {
        databaseInitializeService.initializeDatabase();

        try {
            Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM vector_store", Integer.class);
            if (count != null && count == 0) {
                System.out.println("Vector store is empty, starting to ingest PDF...");
                int chunks = ragService.ingestCinemaPdf();
                System.out.println("PDF ingested successfully: " + chunks + " chunks.");
            } else {
                System.out.println("Vector store already contains data (" + count + " rows). Skip ingestion.");
            }
        } catch (Exception e) {
            System.out.println("Cannot initialize vector_store/ingest PDF: " + e.getMessage());
        }
    }
}
