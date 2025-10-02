package LTW_GraphQL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import LTW_GraphQL.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
