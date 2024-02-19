package com.bigdata.backend.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// import com.bigdata.backend.models.Import;
import com.bigdata.backend.repositories.ImportRepository;

@Service
public class ImportService {
    private final ImportRepository importRepository;

    @Autowired
    public ImportService(ImportRepository importRepository) {
        this.importRepository = importRepository;
    }

    // public List<Person> doSomethingOnNeo() {
    //     personRepository.deleteAll();

    //     Person greg = new Person("Greg");
    //     Person roy = new Person("Roy");
    //     Person craig = new Person("Craig");

    //     personRepository.save(greg);
    //     personRepository.save(roy);
    //     personRepository.save(craig);

    //     greg = personRepository.findByName(greg.getName());
    //     greg.worksWith(roy);
    //     greg.worksWith(craig);
    //     personRepository.save(greg);

    //     roy = personRepository.findByName(roy.getName());
    //     roy.worksWith(craig);
    //     // We already know that roy works with greg
    //     personRepository.save(roy);

    //     // We already know craig works with roy and greg

    //     List<Person> teammates = personRepository.findByTeammatesName(greg.getName());

    //     return teammates;
    // };

    public void importCsv (MultipartFile file)
    {
        this.importRepository.loadCsv();
    }
}
