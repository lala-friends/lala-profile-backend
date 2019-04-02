package org.lala.profile.person.web;

import org.apache.commons.validator.routines.EmailValidator;
import org.lala.profile.accounts.config.CurrentUser;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.accounts.vo.AccountRole;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.person.vo.PersonDto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(personRepository.findByEmail(email));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity modifyPersion(@PathVariable Integer id, @RequestBody @Valid PersonDto personDto, Errors errors
            , @CurrentUser Account currentUser) {
        Optional<Person> byId = personRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Person existsPerson = byId.get();
        // 어드민 혹은 자기 자신만 수정 가능
        boolean isAdmin = currentUser.getRoles().stream().filter(r -> r.equals(AccountRole.ADMIN)).count() == 1 ? true : false;
        if (!isAdmin && !existsPerson.getEmail().equals(currentUser.getEmail())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        this.modelMapper.map(personDto, existsPerson);

        Person savedPerson = personRepository.save(existsPerson);
        return ResponseEntity.ok(savedPerson);
    }
}
