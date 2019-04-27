package org.lala.profile.person.web;

import org.apache.commons.validator.routines.EmailValidator;
import org.lala.profile.accounts.config.CurrentUser;
import org.lala.profile.accounts.repository.AccountsRepository;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.accounts.vo.AccountRole;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.person.vo.PersonDto;
import org.lala.profile.person.vo.PersonWithProjects;
import org.lala.profile.person.vo.PersonWithProjectsDto;
import org.lala.profile.products.groups.service.ProductGroupService;
import org.lala.profile.projects.repository.ProjectRepository;
import org.lala.profile.projects.vo.Project;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/persons", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class PersonController {

    private PersonRepository personRepository;

    private ProductGroupService productGroupService;

    private ModelMapper modelMapper;

    private ProjectRepository projectRepository;

    private AccountsRepository accountsRepository;

    public PersonController(PersonRepository personRepository, ProductGroupService productGroupService, ModelMapper modelMapper, ProjectRepository projectRepository, AccountsRepository accountsRepository) {
        this.personRepository = personRepository;
        this.productGroupService = productGroupService;
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
        this.accountsRepository = accountsRepository;
    }

    @GetMapping
    public ResponseEntity getAllPersons() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @GetMapping(value = "/{email}/products")
    public ResponseEntity getAllProductsByPerson(@PathVariable String email, @CurrentUser Account currentUser) {
        if (EmailValidator.getInstance().isValid(email)) {
//            if (currentUser == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
            return ResponseEntity.ok(productGroupService.getProductsByEmail(email));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{email}/projects")
    public ResponseEntity getAllProjectsByPerson(@PathVariable String email, @CurrentUser Account currentUser) {
        if (EmailValidator.getInstance().isValid(email)) {
            Person byEmail = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " is not found!!"));
            PersonWithProjects personWithProjects = new PersonWithProjects();
            modelMapper.map(byEmail, personWithProjects);

            Account optionalAccount = accountsRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " is not found!!"));
            List<Project> projects = projectRepository.findByOwner(optionalAccount).orElse(new ArrayList<>());
            personWithProjects.setProjects(projects);

            return ResponseEntity.ok(personWithProjects);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{email}")
    public ResponseEntity modifyPersonAndProjects(@PathVariable String email,
                                                  @RequestBody @Valid PersonWithProjectsDto personWithProjectsDto,
                                                  Errors errors,
                                                  @CurrentUser Account currentUser) {
        if (EmailValidator.getInstance().isValid(email)) {
            Person existsPerson = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "is not found!!"));
            Account owner = accountsRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + "is not found!!"));

            // 권한체크
            boolean isAdmin = currentUser.getRoles().stream().filter(r -> r.equals(AccountRole.ADMIN)).count() == 1 ? true : false;
            if (!isAdmin && !currentUser.getEmail().equals(email)) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }

            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(errors);
            }

            // person 먼저 수정
            this.modelMapper.map(personWithProjectsDto, existsPerson);
            personRepository.save(existsPerson);

            // project 삭제 후 재등록
            List<Project> savedProjects = null;
            if (!CollectionUtils.isEmpty(personWithProjectsDto.getProjects())) {
                projectRepository.deleteByOwner(owner);
                savedProjects = new ArrayList<>();
                for (Project p : personWithProjectsDto.getProjects()) {
                    p.setOwner(owner);
                    Project savedProject = projectRepository.save(p);
                    savedProjects.add(savedProject);
                }
            }

            PersonWithProjects savedObject = new PersonWithProjects();
            this.modelMapper.map(existsPerson, savedObject);
            savedObject.setProjects(savedProjects);

            return ResponseEntity.ok(savedObject);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity getPerson(@PathVariable String email, @CurrentUser Account currentUser) {
        if (EmailValidator.getInstance().isValid(email)) {
//            if (currentUser == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
            return ResponseEntity.ok(personRepository.findByEmail(email));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity modifyPersion(@PathVariable Integer id, @RequestBody @Valid PersonDto personDto, Errors errors
//            , @CurrentUser Account currentUser) {
//        Optional<Person> byId = personRepository.findById(id);
//        if (byId.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(errors);
//        }
//
//        Person existsPerson = byId.get();
//        // 어드민 혹은 자기 자신만 수정 가능
//        boolean isAdmin = currentUser.getRoles().stream().filter(r -> r.equals(AccountRole.ADMIN)).count() == 1 ? true : false;
//        if (!isAdmin && !existsPerson.getEmail().equals(currentUser.getEmail())) {
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        }
//
//        this.modelMapper.map(personDto, existsPerson);
//
//        Person savedPerson = personRepository.save(existsPerson);
//        return ResponseEntity.ok(savedPerson);
//    }
}
