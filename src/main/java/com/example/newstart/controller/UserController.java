package com.example.newstart.controller;

import com.example.newstart.model.Books;
import com.example.newstart.model.Price;
import com.example.newstart.model.Users;
import com.example.newstart.repo.BooksRepository;
import com.example.newstart.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private UsersRepository repository;
    private BooksRepository bookRepository;
    private Users loginStatus;


    @Autowired
    public UserController(UsersRepository repository,BooksRepository bookRepository){
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @PostMapping(value = "/login",
            consumes = {
            MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
            MediaType.APPLICATION_JSON_VALUE
            })
    public ResponseEntity<Users> userLogin(@RequestBody Users loginDetail) {
        loginStatus = loginDetail;
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public List<Users> getUser() {
        List<Users> user = null;
        if (loginStatus != null) {
            user = repository.findByUsernameAndPassword(loginStatus.getUsername(), loginStatus.getPassword());
            return user;
        } else {
            return null;
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<Users> deleteUser() {
        try {
            if(loginStatus != null) {
                repository.deleteByUsernameAndPassword(loginStatus.getUsername(), loginStatus.getPassword());
                loginStatus = null;
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
        }
        return null;
    }

    @PostMapping(value = "/users",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    public ResponseEntity<Users> userUpload(@RequestBody Users uploadDetail) {
        repository.save(uploadDetail);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/users/orders",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    public ResponseEntity<Price> orderUser(@RequestBody Users uploadDetail) {
        System.out.println(uploadDetail);
        if(loginStatus != null) {
            Books book = null;
            float sum = 0f;
            for(String i : uploadDetail.getBook()) {
                int idNum = Integer.parseInt(i);
                book = bookRepository.findByIdWherePrice(idNum);
                sum += book.getPrice();
            }
            Price totalPrice = new Price();
            String formattedString = String.format("%.02f", sum);
            totalPrice.setPrice(formattedString);

//            return ResponseEntity.status(HttpStatus.OK).body("Price: " + sum);
            return new ResponseEntity(totalPrice,HttpStatus.OK);
        }
        return null;
    }
    @GetMapping("books")
    public List<Books> booksList() {
        return bookRepository.findAllOrderByIsreommendedAndName();
    }
}
