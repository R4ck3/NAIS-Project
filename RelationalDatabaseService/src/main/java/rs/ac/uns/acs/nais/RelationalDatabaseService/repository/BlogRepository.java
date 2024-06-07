package rs.ac.uns.acs.nais.RelationalDatabaseService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.RelationalDatabaseService.model.Blog;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByCategory(String category);
}
