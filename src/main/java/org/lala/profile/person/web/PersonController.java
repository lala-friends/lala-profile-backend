package org.lala.profile.person.web;

import org.apache.commons.validator.routines.EmailValidator;
import org.lala.profile.accounts.config.CurrentUser;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.person.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/persons", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class PersonController {

    private PersonRepository personRepository;

    private ModelMapper modelMapper;

    public PersonController(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity getAllPersons() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity getPerson(@PathVariable String email, @CurrentUser Account currentUser) {
        if (EmailValidator.getInstance().isValid(email)) {
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok(personRepository.findByEmail(email));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
