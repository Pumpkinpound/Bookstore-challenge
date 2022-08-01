package com.example.newstart.repo;

import com.example.newstart.model.Books;
import com.example.newstart.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books, Integer> {
//    List<Books> findAllOrderByIsrecommendedDescAndNameAsc();
    @Query(value = "SELECT * FROM booklist_table ORDER BY is_recommended DESC,name ASC", nativeQuery = true)
    List<Books> findAllOrderByIsreommendedAndName();

    @Query(value = "SELECT * FROM booklist_table WHERE id = ?1",nativeQuery = true)
    Books findByIdWherePrice(Integer id);

}
