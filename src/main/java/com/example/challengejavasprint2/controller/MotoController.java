package com.example.challengejavasprint2.controller;

import com.example.challengejavasprint2.model.Moto;
import com.example.challengejavasprint2.repository.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Dashboard com estatísticas
        long totalMotos = motoRepository.count();
        long motosDisponiveis = motoRepository.countByStatus("DISPONIVEL");
        long motosEmUso = motoRepository.countByStatus("EM_USO");
        long motosManutencao = motoRepository.countByStatus("MANUTENCAO");
        
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("motosDisponiveis", motosDisponiveis);  
        model.addAttribute("motosEmUso", motosEmUso);
        model.addAttribute("motosManutencao", motosManutencao);
        
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/motos")
    public String listMotos(Model model, @RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            model.addAttribute("motos", motoRepository.findByStatus(status));
        } else {
            model.addAttribute("motos", motoRepository.findAll());
        }
        return "motos/list";
    }

    @GetMapping("/motos/new")
    public String newMoto(Model model) {
        model.addAttribute("moto", new Moto());
        return "motos/form";
    }

    @GetMapping("/motos/edit/{id}")
    public String editMoto(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id).orElse(null);
        if (moto == null) {
            return "redirect:/motos";
        }
        model.addAttribute("moto", moto);
        return "motos/form";
    }

    @PostMapping("/motos/save")
    public String saveMoto(@Valid @ModelAttribute Moto moto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "motos/form";
        }
        motoRepository.save(moto);
        return "redirect:/motos";
    }

    @GetMapping("/motos/delete/{id}")
    public String deleteMoto(@PathVariable Long id) {
        motoRepository.deleteById(id);
        return "redirect:/motos";
    }
    
    @PostMapping("/motos/changeStatus/{id}")
    public String changeStatus(@PathVariable Long id, @RequestParam String newStatus) {
        Moto moto = motoRepository.findById(id).orElse(null);
        if (moto != null) {
            moto.setStatus(newStatus);
            motoRepository.save(moto);
        }
        return "redirect:/motos";
    }
    
    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
    
    @GetMapping("/usuario")
    public String usuario() {
        return "usuario";
    }
    
    @GetMapping("/painel")
    public String painel(Model model) {
        return home(model); // Redireciona para a home que já tem o dashboard
    }
}
