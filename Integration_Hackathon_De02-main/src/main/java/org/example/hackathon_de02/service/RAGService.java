package org.example.hackathon_de02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final VectorStore vectorStore;

    public int ingestCinemaPdf() {
        TikaDocumentReader reader = new TikaDocumentReader(
                new ClassPathResource("De02_CinemaStar_ThongTin.pdf"));

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(500)
                .withMinChunkSizeChars(200)
                .build();

        List<Document> documents = splitter.apply(reader.read());
        vectorStore.add(documents);
        return documents.size();
    }

    public List<Document> search(String query) {
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(4)
                        .build());
    }
}
