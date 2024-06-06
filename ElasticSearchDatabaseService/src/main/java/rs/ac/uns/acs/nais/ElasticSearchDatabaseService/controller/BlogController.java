package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.BlogService;

import java.io.IOException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/blogs.json")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/createBlog")
    public Blog createBlog(@RequestBody Blog blog) {
        return blogService.createBlog(blog);
    }

    @GetMapping("/findAll")
    public List<Blog> findAll() {
        return blogService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogById(@PathVariable String id) {
        blogService.deleteBlogById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> findBlogById(@PathVariable String id) {
        Optional<Blog> blog = blogService.findBlogById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findByTitleOrAuthorId")
    public List<Blog> findByTitleOrAuthorId(@RequestParam(value = "title") String title,
                                               @RequestParam(value = "authorId") String authorId) {
        return blogService.findByTitleOrAuthorId(title, authorId);
    }

    @GetMapping("/findByTitleContainingOrAuthorIdContaining")
    public List<Blog> findByTitleContainingOrAuthorIdContaining(@RequestParam(value = "title") String title,
                                                                   @RequestParam(value = "authorId") String authorId) {
        return blogService.findByTitleContainingOrAuthorIdContaining(title, authorId);
    }

    @GetMapping("/findByCustomQuery")
    public List<Blog> findByCustomQuery(@RequestParam(value = "query") String query) {
        return blogService.findByCustomQuery(query);
    }

    @GetMapping("/searchByDescriptionPhrase")
    public List<Blog> searchByDescriptionPhrase(@RequestParam(value = "phrase") String phrase) {
        return blogService.searchByDescriptionPhrase(phrase);
    }

    @GetMapping("/searchByTitleOrDescriptionFuzzy")
    public List<Blog> searchByTitleOrDescriptionFuzzy(@RequestParam(value = "searchTerm") String searchTerm) {
        return blogService.searchByTitleOrDescriptionFuzzy(searchTerm);
    }

    @GetMapping("/author/{authorId}")
        public List<Blog> getBlogsByAuthorId(@PathVariable String authorId) {
            return blogService.findByAuthorId(authorId);
    }

    @GetMapping("/findByAuthorIdAndCategoryAndTitle")
        public List<Blog> findByAuthorIdAndCategoryAndTitle(
                @RequestParam(value = "authorId") String authorId,
                @RequestParam(value = "category") String category,
                @RequestParam(value = "title") String title) {

            return blogService.findByAuthorIdAndCategoryAndTitle(authorId, category, title);
    }

    @PutMapping("/{id}/updateDescription")
    public ResponseEntity<String> updateBlogDescription(@PathVariable String id, @RequestBody String newDescription) {
        ResponseEntity<String> responseEntity;
        try {
            ResponseEntity<Blog> response = blogService.updateBlogDescription(id, newDescription.trim());
            String updatedDescription = response.getBody().getDescription();
            responseEntity = ResponseEntity.ok().body(updatedDescription);
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;
    }

    @GetMapping("/findAllOrderByCreatedAtDesc")
    public List<Blog> findAllOrderByCreatedAtDesc() {
        return blogService.findAllOrderByCreatedAtDesc();
    }

    @GetMapping("/findByAuthorIdAndCategoryAndTitleNoEM")
        public List<Blog> findByAuthorIdAndCategoryAndTitleNoEM(
            @RequestParam(value = "authorId") String authorId,
            @RequestParam(value = "category") String category,
            @RequestParam(value = "title") String title) {

            return blogService.findByAuthorIdAndCategoryAndTitleNoEM(authorId, category, title);
    }

    @GetMapping("/findByCategoryAndDateRange")
    public List<Blog> findByCategoryAndDateRange(
        @RequestParam(value = "category") String category,
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate) {

        return blogService.findByCategoryAndDateRange(category, startDate, endDate);
    }

    @GetMapping("/findByDynamicQuery")
    public List<Blog> findByDynamicQuery(@RequestParam(value = "title") String title,
        @RequestParam(value = "category") String category,
        @RequestParam(value = "description") String description,
        @RequestParam(value = "country") String country,
        @RequestParam(value = "authorId") String authorId,
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate) {

    return blogService.findByDynamicQuery(title, category, description, country, authorId, startDate, endDate);
    }

    @GetMapping("/findByDynamicQuery2")
    public List<Blog> findByDynamicQuery2(@RequestParam(value = "title") String title,
        @RequestParam(value = "category") String category,
        @RequestParam(value = "description") String description,
        @RequestParam(value = "country") String country,
        @RequestParam(value = "authorId") String authorId,
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate) {
            
    return blogService.findByDynamicQuery2(title, category, description, country, authorId, startDate, endDate);
    }

    @GetMapping("/searchByDescriptionPhrasePDF")
    public List<Blog> searchByDescriptionPhrasePDF(@RequestParam(value = "phrase") String phrase, @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) {
        return blogService.searchByDescriptionPhrasePDF(phrase, startDate, endDate);
    }

    @GetMapping("/getBlogsByCountryAndDateRange")
    public List<Blog> getBlogsByCountryAndDateRange(
        @RequestParam(value = "country") String country,
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate
    ) {
        return blogService.findByCountryAndDateRange(country, startDate, endDate);
    }

    @GetMapping("/getBlogsByAuthorIdAndDateRange")
    public List<Blog> getBlogsByAuthorIdAndDateRange(
        @RequestParam(value = "authorId") String authorId,
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate
    ) {
        return blogService.findByAuthorIdAndDateRange(authorId, startDate, endDate);
    }


    @GetMapping(value = "/export-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportPdf(@RequestParam(value = "searchPhrase") String searchPhrase, @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate, @RequestParam(value = "authorId") String authorId) {
        try {
            List<Blog> blogs = blogService.searchByDescriptionPhrasePDF(searchPhrase, startDate, endDate);

            byte[] pdfContents = blogService.exportBlogsByDescriptionPhrasePDF(searchPhrase, startDate, endDate, authorId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "blogs_report.pdf");

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .body(pdfContents);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/export-pdf2", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> exportPdf2(@RequestParam(value = "country") String country, @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate, @RequestParam(value = "authorId") String authorId) {
        try {
            List<Blog> blogsByCountry = blogService.findByCountryAndDateRange(country, startDate, endDate);

            byte[] pdfContents = blogService.exportBlogsByCountryAndAuthorPDF(country, startDate, endDate, authorId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "blogs_report.pdf");

            return ResponseEntity.ok()
                                 .headers(headers)
                                 .body(pdfContents);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


