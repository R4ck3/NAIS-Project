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
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.UserService;



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

    private final UserService userService;

    public BlogService(BlogRepository blogRepository, UserService userService) {
        this.blogRepository = blogRepository;
        this.userService = userService;
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

    public List<Blog> findByCountryAndDateRange(String country, String startDate, String endDate) {
        return blogRepository.findByCountryAndDateRange(country, startDate, endDate);
    }

    public List<Blog> findByAuthorIdAndDateRange(String authorId, String startDate, String endDate) {
        return blogRepository.findByAuthorIdAndDateRange(authorId, startDate, endDate);
    }

    public byte[] exportBlogsByDescriptionPhrasePDF(String phrase, String startDate, String endDate, String authorId) throws IOException {
        List<Blog> blogs = blogRepository.searchByDescriptionPhrasePDF(phrase, startDate, endDate);
        List<Blog> blogsByAuthor = blogRepository.findByAuthorId(authorId);
        String authorFullName = userService.getFullNameById(authorId);

        return generatePdfBytes(blogs, blogsByAuthor, startDate, endDate, authorFullName);
    }

    private byte[] generatePdfBytes(List<Blog> blogs, List<Blog> blogsByAuthor, String startDate, String endDate, String authorFullName) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Document document = new Document();
    document.setMargins(20, 20, 20, 20); 

    String filename = "blogs_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".pdf";

    PdfWriter.getInstance(document, byteArrayOutputStream);
    document.open();

    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Font.BOLD);

    Paragraph title = new Paragraph("BLOGS REPORT", titleFont);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);

    Paragraph description = new Paragraph("Report for blogs created between " + startDate + " and " + endDate, FontFactory.getFont(FontFactory.HELVETICA, 12));
    description.setAlignment(Element.ALIGN_CENTER);
    document.add(description);

    document.add(new Paragraph("\n"));

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


    Paragraph title2 = new Paragraph("BLOGS REPORT BY AUTHOR", titleFont);
    title2.setAlignment(Element.ALIGN_CENTER);
    document.add(title2);

    Paragraph description2 = new Paragraph("Report for blogs created by author: " + authorFullName, FontFactory.getFont(FontFactory.HELVETICA, 12));
    description2.setAlignment(Element.ALIGN_CENTER);
    document.add(description2);

    document.add(new Paragraph("\n"));

    PdfPTable reportTable2 = new PdfPTable(4);
    reportTable2.setWidthPercentage(100);

    Font headerFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
    PdfPCell headerCell12 = new PdfPCell(new Paragraph("Title", headerFont2));
    PdfPCell headerCell22 = new PdfPCell(new Paragraph("Description", headerFont2));
    PdfPCell headerCell32 = new PdfPCell(new Paragraph("Category", headerFont2));
    PdfPCell headerCell42 = new PdfPCell(new Paragraph("Created At", headerFont2));

    headerCell12.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell22.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell32.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell42.setBackgroundColor(new Color(110, 231, 234, 255));

    reportTable2.addCell(headerCell12);
    reportTable2.addCell(headerCell22);
    reportTable2.addCell(headerCell32);
    reportTable2.addCell(headerCell42);

    for (Blog blog : blogsByAuthor) {
        reportTable2.addCell(blog.getTitle());
        reportTable2.addCell(blog.getDescription());
        reportTable2.addCell(blog.getCategory());
        reportTable2.addCell(blog.getCreatedAt());
    }

    document.add(reportTable2);

    document.close();

    return byteArrayOutputStream.toByteArray();
    }

    private byte[] generatePdfBytes2(List<Blog> blogsByCountry, List<Blog> blogsByAuthor, String startDate, String endDate, String authorFullName) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4.rotate());
    document.setMargins(20, 20, 20, 20); 

    String filename = "blogs_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".pdf";

    PdfWriter.getInstance(document, byteArrayOutputStream);
    document.open();

    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Font.BOLD);

    Paragraph title = new Paragraph("BLOGS REPORT", titleFont);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);

    Paragraph description = new Paragraph("Report for blogs created between " + startDate + " and " + endDate, FontFactory.getFont(FontFactory.HELVETICA, 12));
    description.setAlignment(Element.ALIGN_CENTER);
    document.add(description);

    document.add(new Paragraph("\n"));

    PdfPTable reportTable = new PdfPTable(5); 
    reportTable.setWidthPercentage(100);

    float[] columnWidths = {5, 15, 5, 5, 5}; 
    reportTable.setWidths(columnWidths);

    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
    PdfPCell headerCell1 = new PdfPCell(new Paragraph("Title", headerFont));
    PdfPCell headerCell2 = new PdfPCell(new Paragraph("Description", headerFont));
    PdfPCell headerCell3 = new PdfPCell(new Paragraph("Category", headerFont));
    PdfPCell headerCell4 = new PdfPCell(new Paragraph("Created At", headerFont));
    PdfPCell headerCell5 = new PdfPCell(new Paragraph("Country", headerFont));


    headerCell1.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell2.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell3.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell4.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell5.setBackgroundColor(new Color(110, 231, 234, 255));


    reportTable.addCell(headerCell1);
    reportTable.addCell(headerCell2);
    reportTable.addCell(headerCell3);
    reportTable.addCell(headerCell4);
    reportTable.addCell(headerCell5);


    for (Blog blog : blogsByCountry) {
        reportTable.addCell(blog.getTitle());
        reportTable.addCell(blog.getDescription());
        reportTable.addCell(blog.getCategory());
        reportTable.addCell(blog.getCreatedAt());
        reportTable.addCell(blog.getCountry());

    }

    document.add(reportTable);


    Paragraph title2 = new Paragraph("BLOGS REPORT BY AUTHOR", titleFont);
    title2.setAlignment(Element.ALIGN_CENTER);
    document.add(title2);

    Paragraph description2 = new Paragraph("Report for blogs created between" + startDate + " and" + endDate +  " by author: " + authorFullName, FontFactory.getFont(FontFactory.HELVETICA, 12));
    description2.setAlignment(Element.ALIGN_CENTER);
    document.add(description2);

    document.add(new Paragraph("\n"));

    PdfPTable reportTable2 = new PdfPTable(5); 
    reportTable2.setWidthPercentage(100);

    reportTable2.setWidths(columnWidths);

    Font headerFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
    PdfPCell headerCell12 = new PdfPCell(new Paragraph("Title", headerFont2));
    PdfPCell headerCell22 = new PdfPCell(new Paragraph("Description", headerFont2));
    PdfPCell headerCell32 = new PdfPCell(new Paragraph("Category", headerFont2));
    PdfPCell headerCell42 = new PdfPCell(new Paragraph("Created At", headerFont2));
    PdfPCell headerCell52 = new PdfPCell(new Paragraph("Country", headerFont2));


    headerCell12.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell22.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell32.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell42.setBackgroundColor(new Color(110, 231, 234, 255));
    headerCell52.setBackgroundColor(new Color(110, 231, 234, 255));


    reportTable2.addCell(headerCell12);
    reportTable2.addCell(headerCell22);
    reportTable2.addCell(headerCell32);
    reportTable2.addCell(headerCell42);
    reportTable2.addCell(headerCell52);


    for (Blog blog : blogsByAuthor) {
        reportTable2.addCell(blog.getTitle());
        reportTable2.addCell(blog.getDescription());
        reportTable2.addCell(blog.getCategory());
        reportTable2.addCell(blog.getCreatedAt());
        reportTable2.addCell(blog.getCountry());
    }

    document.add(reportTable2);

    document.close();

    return byteArrayOutputStream.toByteArray();
}



    public byte[] exportBlogsByCountryAndAuthorPDF(String country, String startDate, String endDate, String authorId) throws IOException {
        List<Blog> blogsByCountry = blogRepository.findByCountryAndDateRange(country, startDate, endDate);
        List<Blog> blogsByAuthor = blogRepository.findByAuthorIdAndDateRange(authorId, startDate, endDate);
        String authorFullName = userService.getFullNameById(authorId);

        return generatePdfBytes2(blogsByCountry, blogsByAuthor, startDate, endDate, authorFullName);
    }
}
