package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.BlogRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IBlogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.Collections; 
import java.util.Comparator;


@Service
public class BlogService implements IBlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    public void deleteBlogById(String id) {
        blogRepository.deleteById(id);
    }

    public Optional<Blog> findBlogById(String id) {
        return blogRepository.findById(id);
    }

    public List<Blog> findByTitleOrAuthorId(String title, String authorId) {
        return blogRepository.findByTitleOrAuthorId(title, authorId);
    }

    public List<Blog> findByTitleContainingOrAuthorIdContaining(String title, String authorId) {
        return blogRepository.findByTitleContainingOrAuthorIdContaining(title, authorId);
    }

    public List<Blog> findByCustomQuery(String query) {
            return blogRepository.findByCustomQuery(query);
    }

    public List<Blog> searchByDescriptionPhrase(String phrase) {
        return blogRepository.searchByDescriptionPhrase(phrase);
    }

    public List<Blog> searchByTitleOrDescriptionFuzzy(String searchTerm) {
        return blogRepository.searchByTitleOrDescriptionFuzzy(searchTerm);
    }

    public List<Blog> findByAuthorId(String authorId) {
        return blogRepository.findByAuthorId(authorId);
    }

    public List<Blog> findByAuthorIdAndCategoryAndTitle(String authorId, String category, String title) {
        return blogRepository.findByAuthorIdAndCategoryAndTitle(authorId, category, title);
    }

    public ResponseEntity<Blog> updateBlogDescription(String blogId, String newDescription) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blog.setDescription(newDescription);
            blogRepository.save(blog);
            return ResponseEntity.ok(blog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<Blog> findAllOrderByCreatedAtDesc() {
        List<Blog> blogs = blogRepository.findAll();
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }

    public List<Blog> findByAuthorIdAndCategoryAndTitleNoEM(String authorId, String category, String title) {
        return blogRepository.findByAuthorIdAndCategoryAndTitleNoEM(authorId, category, title);
    }

    public List<Blog> findByCategoryAndDateRange(String category, String startDate, String endDate) {
        List<Blog> blogs = blogRepository.findByCategoryAndDateRange(category, startDate, endDate);
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }

    public List<Blog> findByDynamicQuery(String title, String category, String description, String country, String authorId, String startDate, String endDate) {
        List<Blog> blogs = blogRepository.findByDynamicQuery(title, category, description, country, authorId, startDate, endDate);
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }

    public List<Blog> findByDynamicQuery2(String title, String category, String description, String country, String authorId, String startDate, String endDate) {
        List<Blog> blogs = blogRepository.findByDynamicQuery2(title, category, description, country, authorId, startDate, endDate);
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
        return blogs;
    }

    @Override
    public List<Blog> searchByDescriptionPhrasePDF(String phrase, String startDate, String endDate) {
        return blogRepository.searchByDescriptionPhrasePDF(phrase, startDate, endDate);
    }

    public byte[] exportBlogsByDescriptionPhrasePDF(String phrase, String startDate, String endDate) throws IOException {
        List<Blog> blogs = blogRepository.searchByDescriptionPhrasePDF(phrase, startDate, endDate);
        return generatePdfBytes(blogs);
    }

    private byte[] generatePdfBytes(List<Blog> blogs) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        String filename = "blogs_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".pdf";

        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Font.BOLD);

        Paragraph title = new Paragraph("BLOGS REPORT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        PdfPTable reportTable = new PdfPTable(4);
        reportTable.setWidthPercentage(100);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
        PdfPCell headerCell1 = new PdfPCell(new Paragraph("Title", headerFont));
        PdfPCell headerCell2 = new PdfPCell(new Paragraph("Description", headerFont));
        PdfPCell headerCell3 = new PdfPCell(new Paragraph("Category", headerFont));
        PdfPCell headerCell4 = new PdfPCell(new Paragraph("Created At", headerFont));

        headerCell1.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell2.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell3.setBackgroundColor(new Color(110, 231, 234, 255));
        headerCell4.setBackgroundColor(new Color(110, 231, 234, 255));

        reportTable.addCell(headerCell1);
        reportTable.addCell(headerCell2);
        reportTable.addCell(headerCell3);
        reportTable.addCell(headerCell4);

        for (Blog blog : blogs) {
            reportTable.addCell(blog.getTitle());
            reportTable.addCell(blog.getDescription());
            reportTable.addCell(blog.getCategory());
            reportTable.addCell(blog.getCreatedAt());
        }

        document.add(reportTable);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

}
