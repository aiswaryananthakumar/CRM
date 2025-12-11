package com.techietact.crm.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techietact.crm.entity.Register;
import com.techietact.crm.service.RegisterService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/list")
    public String listRegisters(Model model) {
        List<Register> registers = registerService.getAll();
        model.addAttribute("registers", registers);
        return "register-list";
    }

    @GetMapping("/add")
    public String addRegisterPage(Model model) {
        model.addAttribute("register", new Register());
        return "register-form";
    }

    @PostMapping("/save")
    public String saveRegister(@Valid @ModelAttribute("register") Register register,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register-form";
        }

        registerService.save(register);
        redirectAttributes.addFlashAttribute("success", "Registered successfully! Please login now.");
        return "redirect:/login";
    }

    @GetMapping("/update")
    public String updatePage(@RequestParam("id") int id, Model model) {
        model.addAttribute("register", registerService.get(id));
        return "register-form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("register") Register register,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register-form";
        }

        registerService.update(register);
        redirectAttributes.addFlashAttribute("success", "Updated successfully!");
        return "redirect:/register/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        registerService.softDelete(id);
        redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        return "redirect:/register/list";
    }
}
