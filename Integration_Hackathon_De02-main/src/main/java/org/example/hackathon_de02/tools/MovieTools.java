package org.example.hackathon_de02.tools;

import lombok.RequiredArgsConstructor;
import org.example.hackathon_de02.model.entity.Movie;
import org.example.hackathon_de02.repository.MovieRepository;
import org.example.hackathon_de02.service.RAGService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieTools {

    private final MovieRepository movieRepository;
    private final RAGService ragService;

    @Tool(name = "searchMovieByName", description = "Tra cứu phim theo tên hoặc một phần tên phim.")
    public List<String> searchMovieByName(
            @ToolParam(description = "Tên phim cần tìm") String name) {
        return movieRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::formatMovie)
                .toList();
    }

    @Tool(name = "searchMovieByGenre", description = "Tra cứu phim theo thể loại phim.")
    public List<String> searchMovieByGenre(
            @ToolParam(description = "Tên thể loại, ví dụ: hành động, hài, kinh dị") String genre) {
        return movieRepository.findByGenre_NameContainingIgnoreCase(genre)
                .stream()
                .map(this::formatMovie)
                .toList();
    }

    @Tool(name = "searchCinemaInfo", description = "Tra cứu thông tin CinemaStar như địa chỉ rạp, giờ hoạt động, chính sách và phương thức thanh toán từ tài liệu PDF.")
    public String searchCinemaInfo(
            @ToolParam(description = "Nội dung cần tra cứu về rạp chiếu phim") String query) {
        List<String> results = ragService.search(query).stream()
                .map(document -> document.getText())
                .filter(text -> text != null && !text.isBlank())
                .toList();

        return results.isEmpty()
                ? "Không tìm thấy thông tin phù hợp trong tài liệu CinemaStar."
                : String.join("\n\n", results);
    }

    private String formatMovie(Movie movie) {
        String genre = movie.getGenre() != null ? movie.getGenre().getName() : "Chưa có thể loại";
        return String.format("%s | Thể loại: %s | Giá: %s | Còn: %d",
                movie.getName(), genre, movie.getPrice(), movie.getStock());
    }
}
