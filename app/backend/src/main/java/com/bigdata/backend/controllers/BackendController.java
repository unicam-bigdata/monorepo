package com.bigdata.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigdata.backend.models.Person;
import com.bigdata.backend.services.PersonService;

@RestController
public class BackendController {

    private final PersonService personService;

    @Autowired
    public BackendController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public List<Person> index() {
        return this.personService.doSomethingOnNeo();
    }
}
