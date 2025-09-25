package com.example.challengejavasprint2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.challengejavasprint2.model.Moto;
import com.example.challengejavasprint2.model.PerfilUsuario;
import com.example.challengejavasprint2.repository.MotoRepository;
import com.example.challengejavasprint2.service.PerfilUsuarioService;
import com.example.challengejavasprint2.util.GithubUserModelHelper;

import jakarta.validation.Valid;

@Controller
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        long totalMotos = motoRepository.count();
        long motosDisponiveis = motoRepository.countByStatus("DISPONIVEL");
        long motosEmUso = motoRepository.countByStatus("EM_USO");
        long motosManutencao = motoRepository.countByStatus("MANUTENCAO");

        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("motosDisponiveis", motosDisponiveis);
        model.addAttribute("motosEmUso", motosEmUso);
        model.addAttribute("motosManutencao", motosManutencao);

        GithubUserModelHelper.addGithubUserInfo(model, authentication);

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/motos")
    public String listMotos(Model model, @RequestParam(value = "status", required = false) String status, Authentication authentication) {
        if (status != null && !status.isEmpty()) {
            model.addAttribute("motos", motoRepository.findByStatus(status));
        } else {
            model.addAttribute("motos", motoRepository.findAll());
        }
        
        GithubUserModelHelper.addGithubUserInfo(model, authentication);
        return "motos/list";
    }

    @GetMapping("/motos/new")
    public String newMoto(Model model, Authentication authentication) {
        model.addAttribute("moto", new Moto());
        GithubUserModelHelper.addGithubUserInfo(model, authentication);
        return "motos/form";
    }

    @GetMapping("/motos/edit/{id}")
    public String editMoto(@PathVariable Long id, Model model, Authentication authentication) {
        Moto moto = motoRepository.findById(id).orElse(null);
        if (moto == null) {
            return "redirect:/motos";
        }
        model.addAttribute("moto", moto);
        GithubUserModelHelper.addGithubUserInfo(model, authentication);
        return "motos/form";
    }

    @PostMapping("/motos/save")
    public String saveMoto(@Valid @ModelAttribute Moto moto, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            GithubUserModelHelper.addGithubUserInfo(model, authentication);
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
    public String changeStatus(@PathVariable("id") Long id, @RequestParam("newStatus") String newStatus) {
        Moto moto = motoRepository.findById(id).orElse(null);
        if (moto != null) {
            moto.setStatus(newStatus);
            motoRepository.save(moto);
        }
        return "redirect:/motos";
    }

    @GetMapping("/sobre")
    public String sobre(Model model, Authentication authentication) {
        GithubUserModelHelper.addGithubUserInfo(model, authentication);
        return "sobre";
    }

    @GetMapping("/usuario")
    public String usuario(Model model, Authentication authentication) {
        GithubUserModelHelper.addGithubUserInfo(model, authentication);

        String githubUsername = GithubUserModelHelper.getGithubUsername(authentication);
        String githubName = GithubUserModelHelper.getGithubName(authentication);

        if (githubUsername != null) {
            PerfilUsuario perfil = perfilUsuarioService.buscarOuCriarPerfil(githubUsername, githubName);
            model.addAttribute("perfil", perfil);

            model.addAttribute("cargos", new String[]{
                "Analista de Roteirização Logística",
                "Analista de Comunicação Sênior",
                "Analista de Planejamento e Análise Financeira",
                "Gestor de Sinistros",
                "Analista de Sistemas",
                "Gerente",
                "City Manager",
                "Gerente de Projetos",
                "Coordenador de Logística"
            });
        }
        return "usuario";
    }

    @PostMapping("/usuario/salvar")
    public String salvarPerfil(@ModelAttribute PerfilUsuario perfil, Authentication authentication) {
        String githubUsername = GithubUserModelHelper.getGithubUsername(authentication);
        String githubName = GithubUserModelHelper.getGithubName(authentication);

        if (githubUsername != null) {
            PerfilUsuario perfilExistente = perfilUsuarioService.buscarOuCriarPerfil(githubUsername, githubName);
            perfilUsuarioService.atualizarPerfil(perfilExistente, perfil, githubName);
        }
        return "redirect:/usuario?sucesso=true";
    }

    @GetMapping("/painel")
    public String painel(Model model, Authentication authentication) {
        return home(model, authentication);
    }
}
