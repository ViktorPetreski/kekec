package com.example.kekec.model.web;

import com.example.kekec.model.jpa.*;
import com.example.kekec.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class UserController {

    private SpendingService spendingService;
    private CandidateService candidateService;
    private ContactInfoService contactInfoService;
    private InstallmentService installmentService;
    private PaymentInfoService paymentInfoService;
    private QueryService queryService;
    private InstructorService instructorService;
    private DrivingLessonService drivingLessonService;
    private UserService userService;

    @Autowired
    public UserController(SpendingService spendingService, CandidateService candidateService, ContactInfoService contactInfoService,
                          InstallmentService installmentService, PaymentInfoService paymentInfoService, QueryService queryService,
                          InstructorService instructorService, DrivingLessonService drivingLessonService, UserService userService) {
        this.spendingService = spendingService;
        this.candidateService = candidateService;
        this.contactInfoService = contactInfoService;
        this.installmentService = installmentService;
        this.paymentInfoService = paymentInfoService;
        this.queryService = queryService;
        this.instructorService = instructorService;
        this.drivingLessonService = drivingLessonService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/user/addCandidate"}, method = RequestMethod.GET)
    public String showAddCandidate(Model model) {
        model.addAttribute("pageFragment", "addCandidate");
        return "index";
    }


    @RequestMapping(value = {"/user/addCandidate"}, method = RequestMethod.POST)
    public String addCandidate(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
                               @RequestParam Double totalSum, @RequestParam String date, @RequestParam Integer numberOfInstallments, @RequestParam String ssn,
                               @RequestParam String drivingCategory, @RequestParam Integer numberOfLessons) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime startingTime = LocalDateTime.parse(date, formatter);
        PaymentInfo paymentInfo = paymentInfoService.createPaymentInfo(totalSum, numberOfInstallments, startingTime);
        ContactInfo contactInfo = contactInfoService.createContactInfo(firstName, lastName, phone);
        Candidate candidate = candidateService.createCandidate(contactInfo, paymentInfo, ssn, drivingCategory, numberOfLessons);

        return "redirect:/user/allCandidates";

    }

    @RequestMapping(value = {"/user/allCandidates"}, method = RequestMethod.GET)
    public String showAllCandidates(Model model) {

        List<Candidate> candidates = candidateService.getAll();
        for (Candidate c : candidates) {
            candidateService.checkDebt(c.id);
        }

        List<Candidate> lateCandidates = candidateService.getCandidatesNotPaid();
        List<Instructor> instructors = instructorService.getAll();
        model.addAttribute("candidates", candidates);
        model.addAttribute("instructors", instructors);
        model.addAttribute("lateCandidates", lateCandidates);
        model.addAttribute("pageFragment", "candidates");

        return "index";
    }

    @RequestMapping(value = {"/user/candidate/{id}/paySpending"}, method = RequestMethod.POST)
    public String paySpending(@RequestParam Long additionalSpendingId, @PathVariable Long id) {
        spendingService.paySpending(additionalSpendingId);
        candidateService.paySpending(id, additionalSpendingId);
        return "redirect:/user/allCandidates";
    }

    @RequestMapping(value = {"/user/candidate/{id}/payment/{pId}/addSpending"}, method = RequestMethod.GET)
    public String showAddSpending(Model model, @PathVariable("id") Long candidateId, @PathVariable("pId") Long paymentInfoId) {
        model.addAttribute("candidateId", candidateId);
        model.addAttribute("paymentInfoId", paymentInfoId);
        model.addAttribute("pageFragment", "addSpending");
        return "index";
    }

    @RequestMapping(value = {"/user/candidate/{id}/payment/{pId}/addSpending"}, method = RequestMethod.POST)
    public String addSpending(@PathVariable("pId") Long paymentInfoId, @RequestParam Integer lessonNumber,
                              @RequestParam String description, @RequestParam Double price, @PathVariable("id") Long candidateId) {

        if (description.trim().isEmpty()) {
            description = lessonNumber + " часови";
            Candidate candidate = candidateService.getById(candidateId);
            candidateService.updateCandidate(candidateId, candidate.paymentInfo, candidate.contactInfo, candidate.ssn, candidate.numberOfLessons + lessonNumber, candidate.drivingCategory);
        }
        AdditionalSpending additionalSpending = spendingService.createSpending(description, price, false);
        paymentInfoService.addSpending(paymentInfoId, additionalSpending.id);
        // candidateService.checkDebt(candidateId);
        return "redirect:/user/allCandidates";
    }

    @RequestMapping(value = {"/user/search"}, method = RequestMethod.GET)
    public String search(
            @RequestParam String query,
            Model model
    ) {
        List<Candidate> candidates = queryService.searchCandidate(query);

        model.addAttribute("candidates", candidates);
        model.addAttribute("query", query);

        return "index";
    }


    @RequestMapping(value = {"/user/removeCandidate/{id}"}, method = RequestMethod.POST)
    public String changeCandidate(Model model, @PathVariable Long id) {
        candidateService.removeCandidate(id);
        return "redirect:/user/allCandidates";
    }

    @RequestMapping(value = {"/user/updateCandidate/{id}"}, method = RequestMethod.GET)
    public String showUpdateCandidate(Model model, @PathVariable Long id) {
        Candidate candidate = candidateService.getById(id);
        model.addAttribute("candidate", candidate);
        model.addAttribute("contactInfo", candidate.contactInfo);
        model.addAttribute("paymentInfo", candidate.paymentInfo);
        model.addAttribute("pageFragment", "updateCandidate");
        return "index";
    }

    @RequestMapping(value = {"/user/updateCandidate/{id}"}, method = RequestMethod.POST)
    public String updateCandidate(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
                                  @RequestParam Double totalSum, @RequestParam Integer numberOfLessons, @RequestParam Integer numberOfInstallments,
                                  @PathVariable Long id, @RequestParam String drivingCategory, @RequestParam String ssn) {

        Candidate candidate = candidateService.getById(id);
        PaymentInfo paymentInfo = paymentInfoService.updatePaymentInfo(totalSum, numberOfInstallments, candidate.paymentInfo.id);
        ContactInfo contactInfo = contactInfoService.updateContactInfo(candidate.contactInfo.id, firstName, lastName, phone);
        candidateService.updateCandidate(candidate.id, paymentInfo, contactInfo, ssn, numberOfLessons, drivingCategory);

        return "redirect:/user/allCandidates";
    }

    @RequestMapping(value = {"/user/installment/{id}/addPrice"}, method = RequestMethod.POST)
    public String addPriceToInstallment(@PathVariable Long id, @RequestParam Double price, @RequestParam Long candidateId) {

        Installment installment = installmentService.getById(id);
        installmentService.updateInstallment(id, price, installment.dueDate, installment.startingDate);
        installmentService.payInstallment(id);

        //candidateService.checkDebt(candidateId);

        return "redirect:/user/allCandidates";
    }

    @RequestMapping(value = {"/user/candidate/{id}/addDrivingLesson"}, method = RequestMethod.GET)
    public String showPaySpending(Model model, @PathVariable("id") Long candidateId) {
        Candidate candidate = candidateService.getById(candidateId);
        model.addAttribute("contactInfo", candidate.contactInfo);
        model.addAttribute("candidate", candidate);
        List<Instructor> instructors = instructorService.getAll();
        model.addAttribute("instructors", instructors);
        model.addAttribute("pageFragment", "addDrivingLesson");
        return "index";
    }

    @RequestMapping(value = {"/user/candidate/{id}/addDrivingLesson"}, method = RequestMethod.POST)
    public String paySpending(@PathVariable("id") Long candidateId, @RequestParam String date, @RequestParam Long instructorId, @RequestParam Integer lessonType) {
        drivingLessonService.addDrivingLesson(candidateId, instructorId, date, lessonType);
        return "redirect:/user/allCandidates";
    }


    @RequestMapping(value = {"/user/addInstructor"}, method = RequestMethod.GET)
    public String showAddInstructor(Model model) {
        model.addAttribute("pageFragment", "addInstructor");
        return "index";
    }

    @RequestMapping(value = {"/user/addInstructor"}, method = RequestMethod.POST)
    public String addInstructor(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone) {
        instructorService.createInstructor(firstName, lastName, phone);
        return "redirect:/user/allCandidates";
    }

    @RequestMapping(value = {"/user/candidate/{id}/lessonsOverview"}, method = RequestMethod.GET)
    public String showCandidateLessonsOverview(Model model, @PathVariable("id") Long candidateId) {
        List<DrivingLesson> drivingLessons = queryService.getDrivingLessonsForCandidate(candidateId);
        Candidate candidate = candidateService.getById(candidateId);
        model.addAttribute("drivingLessons", drivingLessons);
        model.addAttribute("contactInfo", candidate.contactInfo);
        model.addAttribute("pageFragment", "lessonsOverview");
        return "index";
    }

    @RequestMapping(value = {"/user/instructor/{id}/searchByMonth"}, method = RequestMethod.GET)
    public String showInstructorLessonsOverview(Model model, @PathVariable("id") Long instructorId) {
        String date = "";

        Map<String, List<DrivingLesson>> drivingLessons = queryService.getDrivingLessonsForInstructorForMonth(instructorId, date);
        Instructor instructor = instructorService.getById(instructorId);
        model.addAttribute("instructorId", instructorId);
        model.addAttribute("date", queryService.parseDate(date));
        model.addAttribute("sortedLessons", drivingLessons);
        model.addAttribute("contactInfo", instructor.contactInfo);
        model.addAttribute("pageFragment", "instructorLessonsOverview");
        return "index";
    }

    @RequestMapping(value = {"/user/instructor/{id}/searchByMonth"}, method = RequestMethod.POST)
    public String showInstructorLessonsOverviewByMonth(Model model, @PathVariable("id") Long instructorId, @RequestParam String date) {

        Map<String, List<DrivingLesson>> drivingLessons = queryService.getDrivingLessonsForInstructorForMonth(instructorId, date);

        Instructor instructor = instructorService.getById(instructorId);
        model.addAttribute("instructorId", instructorId);
        model.addAttribute("date", queryService.parseDate(date));
        model.addAttribute("sortedLessons", drivingLessons);
        model.addAttribute("contactInfo", instructor.contactInfo);
        model.addAttribute("pageFragment", "instructorLessonsOverview");
        return "index";
    }


    @RequestMapping(value = {"/user/candidate/{id}/gotLicence"}, method = RequestMethod.GET)
    public String gotLicence(@PathVariable("id") Long candidateId) {
        candidateService.markAsLegalDriver(candidateId);
        return "redirect:/user/allCandidates";
    }


    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model, HttpSession session){
        model.addAttribute("pageFragment", "login");
        return "index";
    }



    @RequestMapping(value="/admin/registration", method = RequestMethod.GET)
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("pageFragment", "registration");
        return "index";
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public String createNewUser(Model model, @Valid User user, BindingResult bindingResult) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Веќе постои корисник со тоа корисничко име");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageFragment", "registration");
        } else {
            userService.saveUser(user);
            model.addAttribute("successMessage", "Корисникот е регистриран");
            model.addAttribute("user", new User());
            model.addAttribute("pageFragment", "registration");

        }
        return "index";

    }


}
