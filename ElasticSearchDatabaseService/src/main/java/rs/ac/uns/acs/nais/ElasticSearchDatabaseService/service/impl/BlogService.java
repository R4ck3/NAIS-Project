package rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.model.Blog;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.dto.BlogDTO;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.dto.BlogDTO2;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.repository.BlogRepository;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.IBlogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rs.ac.uns.acs.nais.ElasticSearchDatabaseService.service.impl.UserService;


import java.util.stream.Collectors;
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
import java.util.Map;

@Service
public class BlogService implements IBlogService {

    private final BlogRepository blogRepository;

    private final UserService userService;

    public BlogService(BlogRepository blogRepository, UserService userService) {
        this.blogRepository = blogRepository;
        this.userService = userService;
    }

     public Blog updateBlog(String blogId, Blog updatedBlog) {
        Optional<Blog> existingBlogOptional = blogRepository.findById(blogId);
        if (existingBlogOptional.isPresent()) {
            Blog existingBlog = existingBlogOptional.get();
            
            existingBlog.setAuthorId(updatedBlog.getAuthorId());
            existingBlog.setCategory(updatedBlog.getCategory());
            existingBlog.setTitle(updatedBlog.getTitle());
            existingBlog.setDescription(updatedBlog.getDescription());
            existingBlog.setCountry(updatedBlog.getCountry());
            existingBlog.setCreatedAt(updatedBlog.getCreatedAt());
            
            return blogRepository.save(existingBlog);
        } else {
            return null;
        }
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

    public BlogDTO2 searchByDescriptionPhraseWithAggs(String phrase) {
    List<Blog> blogs = blogRepository.searchByDescriptionPhraseWithAggs(phrase);

    Map<String, Long> authorBlogCounts = countByAuthor(blogs);
    Map<String, Long> countryCounts = countByCountry(blogs);
    Map<String, Long> categoryCounts = countByCategory(blogs);

    BlogDTO2 blogDTO = new BlogDTO2();
    blogDTO.setBlogs(blogs);
    blogDTO.setAuthorBlogCounts(authorBlogCounts);
    blogDTO.setCountryCounts(countryCounts);
    blogDTO.setCategoryCounts(categoryCounts);

    return blogDTO;
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

    private Map<String, Long> countByCountry(List<Blog> blogs) {
        return blogs.stream()
                .collect(Collectors.groupingBy(Blog::getCountry, Collectors.counting()));
    }

    private Map<String, Long> countByCategory(List<Blog> blogs) {
        return blogs.stream()
                .collect(Collectors.groupingBy(Blog::getCategory, Collectors.counting()));
    }

    private Map<String, Long> countByAuthor(List<Blog> blogs) {
        return blogs.stream().collect(Collectors.groupingBy(Blog::getAuthorId, Collectors.counting()));
    }

    // public List<Blog> findByCategoryAndDateRange(String category, String startDate, String endDate) {
    //     List<Blog> blogs = blogRepository.findByCategoryAndDateRange(category, startDate, endDate);
    //     blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());
    //     return blogs;
    // }

    public BlogDTO findByCategoryAndDateRange(String category, String startDate, String endDate) {
        List<Blog> blogs = blogRepository.findByCategoryAndDateRange(category, startDate, endDate);
        blogs.sort(Comparator.comparing(Blog::getCreatedAt).reversed());

        Map<String, Long> countryCounts = countByCountry(blogs);

        Map<String, Long> categoryCounts = countByCategory(blogs);

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setBlogs(blogs);
        blogDTO.setCountryCounts(countryCounts);
        blogDTO.setCategoryCounts(categoryCounts);

        return blogDTO;
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

    public List<Blog> findBlogsByCategoryAndDateRange(String category, String startDate, String endDate) {
        return blogRepository.findBlogsByCategoryAndDateRange(category, startDate, endDate);
    }

    public byte[] exportPDF1(String phrase, String startDate, String endDate, String authorId, String category) throws IOException {
        List<Blog> blogs = blogRepository.searchByDescriptionPhrasePDF(phrase, startDate, endDate);
        List<Blog> blogsByAuthor = blogRepository.findByAuthorId(authorId);
        List<Blog> blogsByCategory = blogRepository.findBlogsByCategoryAndDateRange(category, startDate, endDate);
        String authorFullName = userService.getFullNameById(authorId);

        return generatePdfBytes(blogs, blogsByAuthor, blogsByCategory, startDate, endDate, authorFullName, category);
    }

    private byte[] generatePdfBytes(List<Blog> blogs, List<Blog> blogsByAuthor, List<Blog> blogsByCategory, String startDate, String endDate, String authorFullName, String category) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    Document document = new Document();
    document.setMargins(40, 40, 40, 40); 

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

    headerCell12.setBackgroundColor(new Color(255, 0, 0, 255));
    headerCell22.setBackgroundColor(new Color(255, 0, 0, 255));
    headerCell32.setBackgroundColor(new Color(255, 0, 0, 255));
    headerCell42.setBackgroundColor(new Color(255, 0, 0, 255));

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


    Paragraph title3 = new Paragraph("BLOGS REPORT BY CATEGORY", titleFont);
    title3.setAlignment(Element.ALIGN_CENTER);
    document.add(title3);

    Paragraph description3 = new Paragraph("Report for blogs by category: " + category + " created between " + startDate + " and " + endDate , FontFactory.getFont(FontFactory.HELVETICA, 12));
    description3.setAlignment(Element.ALIGN_CENTER);
    document.add(description3);

    document.add(new Paragraph("\n"));

    PdfPTable reportTable3 = new PdfPTable(4);
    reportTable3.setWidthPercentage(100);

    Font headerFont3 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
    PdfPCell headerCell13 = new PdfPCell(new Paragraph("Title", headerFont3));
    PdfPCell headerCell23 = new PdfPCell(new Paragraph("Description", headerFont3));
    PdfPCell headerCell33 = new PdfPCell(new Paragraph("Category", headerFont3));
    PdfPCell headerCell43 = new PdfPCell(new Paragraph("Created At", headerFont3));

    headerCell13.setBackgroundColor(new Color(0, 255, 0, 255));
    headerCell23.setBackgroundColor(new Color(0, 255, 0, 255));
    headerCell33.setBackgroundColor(new Color(0, 255, 0, 255));
    headerCell43.setBackgroundColor(new Color(0, 255, 0, 255));

    reportTable3.addCell(headerCell13);
    reportTable3.addCell(headerCell23);
    reportTable3.addCell(headerCell33);
    reportTable3.addCell(headerCell43);

    for (Blog blog : blogsByCategory) {
        reportTable3.addCell(blog.getTitle());
        reportTable3.addCell(blog.getDescription());
        reportTable3.addCell(blog.getCategory());
        reportTable3.addCell(blog.getCreatedAt());
    }

    document.add(reportTable3);

    document.close();

    return byteArrayOutputStream.toByteArray();
    }

    public byte[] generatePdfBytes2(List<Blog> blogsByCountry, List<Blog> blogsByAuthor, BlogDTO blogsByCategory,
                                    String startDate, String endDate, String authorFullName) throws IOException {
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

        PdfPTable reportTable1 = createTable("BLOGS BY COUNTRY", blogsByCountry);
        document.add(reportTable1);

        PdfPTable reportTable2 = createTable("BLOGS BY AUTHOR", blogsByAuthor);
        document.add(reportTable2);

        PdfPTable reportTable3 = createTable("BLOGS BY CATEGORY", blogsByCategory.getBlogs());
        document.add(reportTable3);

        addCounts(document, "Country Counts", blogsByCategory.getCountryCounts());

        addCounts(document, "Category Counts", blogsByCategory.getCategoryCounts());

        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private PdfPTable createTable(String title, List<Blog> blogs) {
        PdfPTable reportTable = new PdfPTable(5);
        reportTable.setWidthPercentage(100);

        float[] columnWidths = {5, 15, 5, 5, 5};
        reportTable.setWidths(columnWidths);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);
        PdfPCell titleCell = new PdfPCell(new Paragraph(title, headerFont));
        titleCell.setColspan(5);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setBackgroundColor(new Color(110, 231, 234, 255));
        reportTable.addCell(titleCell);

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

        for (Blog blog : blogs) {
            reportTable.addCell(blog.getTitle());
            reportTable.addCell(blog.getDescription());
            reportTable.addCell(blog.getCategory());
            reportTable.addCell(blog.getCreatedAt());
            reportTable.addCell(blog.getCountry());
        }

        return reportTable;
    }

    private void addCounts(Document document, String title, Map<String, Long> counts) throws DocumentException {
        Paragraph titleParagraph = new Paragraph(title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);

        PdfPTable countTable = new PdfPTable(2);
        countTable.setWidthPercentage(50);
        countTable.setHorizontalAlignment(Element.ALIGN_CENTER);

        Font countFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            countTable.addCell(new PdfPCell(new Paragraph(entry.getKey(), countFont)));
            countTable.addCell(new PdfPCell(new Paragraph(String.valueOf(entry.getValue()), countFont)));
        }

        document.add(countTable);
    }

    public byte[] exportBlogsByCountryAndAuthorPDF(String country, String category, String startDate, String endDate, String authorId) throws IOException {
        List<Blog> blogsByCountry = blogRepository.findByCountryAndDateRange(country, startDate, endDate);
        List<Blog> blogsByAuthor = blogRepository.findByAuthorIdAndDateRange(authorId, startDate, endDate);
        List<Blog> blogsByCategory = blogRepository.findByCategoryAndDateRange(category, startDate, endDate);

        
        String authorFullName = userService.getFullNameById(authorId);


        Map<String, Long> countryCounts = countByCountry(blogsByCategory);

        Map<String, Long> categoryCounts = countByCategory(blogsByCategory);

        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setBlogs(blogsByCategory);
        blogDTO.setCountryCounts(countryCounts);
        blogDTO.setCategoryCounts(categoryCounts);

        return generatePdfBytes2(blogsByCountry, blogsByAuthor, blogDTO, startDate, endDate, authorFullName);
    }
}
