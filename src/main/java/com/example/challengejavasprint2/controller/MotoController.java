package com.example.challengejavasprint2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
import com.example.challengejavasprint2.repository.PerfilUsuarioRepository;

import jakarta.validation.Valid;

@Controller
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        // Dashboard com estatísticas
        long totalMotos = motoRepository.count();
        long motosDisponiveis = motoRepository.countByStatus("DISPONIVEL");
        long motosEmUso = motoRepository.countByStatus("EM_USO");
        long motosManutencao = motoRepository.countByStatus("MANUTENCAO");
        
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("motosDisponiveis", motosDisponiveis);  
        model.addAttribute("motosEmUso", motosEmUso);
        model.addAttribute("motosManutencao", motosManutencao);
        
        // Adicionar informações do usuário GitHub se logado via OAuth2
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("githubUser", oauth2User);
            model.addAttribute("userAvatar", oauth2User.getAttribute("avatar_url"));
            model.addAttribute("userName", oauth2User.getAttribute("name"));
            model.addAttribute("userLogin", oauth2User.getAttribute("login"));
        }
        
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/motos")
    public String listMotos(Model model, @RequestParam(required = false) String status, Authentication authentication) {
        if (status != null && !status.isEmpty()) {
            model.addAttribute("motos", motoRepository.findByStatus(status));
        } else {
            model.addAttribute("motos", motoRepository.findAll());
        }
        
        // Adicionar informações do usuário GitHub se logado via OAuth2
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("githubUser", oauth2User);
            model.addAttribute("userAvatar", oauth2User.getAttribute("avatar_url"));
            model.addAttribute("userName", oauth2User.getAttribute("name"));
            model.addAttribute("userLogin", oauth2User.getAttribute("login"));
        }
        
        return "motos/list";
    }

    @GetMapping("/motos/new")
    public String newMoto(Model model, Authentication authentication) {
        model.addAttribute("moto", new Moto());
        
        // Adicionar informações do usuário GitHub se logado via OAuth2
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("githubUser", oauth2User);
            model.addAttribute("userAvatar", oauth2User.getAttribute("avatar_url"));
            model.addAttribute("userName", oauth2User.getAttribute("name"));
            model.addAttribute("userLogin", oauth2User.getAttribute("login"));
        }
        
        return "motos/form";
    }

    @GetMapping("/motos/edit/{id}")
    public String editMoto(@PathVariable Long id, Model model, Authentication authentication) {
        Moto moto = motoRepository.findById(id).orElse(null);
        if (moto == null) {
            return "redirect:/motos";
        }
        model.addAttribute("moto", moto);
        
        // Adicionar informações do usuário GitHub se logado via OAuth2
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("githubUser", oauth2User);
            model.addAttribute("userAvatar", oauth2User.getAttribute("avatar_url"));
            model.addAttribute("userName", oauth2User.getAttribute("name"));
            model.addAttribute("userLogin", oauth2User.getAttribute("login"));
        }
        
        return "motos/form";
    }

    @PostMapping("/motos/save")
    public String saveMoto(@Valid @ModelAttribute Moto moto, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            // Adicionar informações do usuário GitHub se logado via OAuth2 (em caso de erro)
            if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                model.addAttribute("githubUser", oauth2User);
                model.addAttribute("userAvatar", oauth2User.getAttribute("avatar_url"));
                model.addAttribute("userName", oauth2User.getAttribute("name"));
                model.addAttribute("userLogin", oauth2User.getAttribute("login"));
            }
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
    public String sobre(Model model, Authentication authentication) {
        // Adicionar informações do usuário GitHub se logado via OAuth2
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("githubUser", oauth2User);
            model.addAttribute("userAvatar", oauth2User.getAttribute("avatar_url"));
            model.addAttribute("userName", oauth2User.getAttribute("name"));
            model.addAttribute("userLogin", oauth2User.getAttribute("login"));
        }
        return "sobre";
    }
    
    @GetMapping("/usuario")
    public String usuario(Model model, Authentication authentication) {
        // Adicionar informações do usuário GitHub se logado via OAuth2
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String githubUsername = oauth2User.getAttribute("login");
            String githubName = oauth2User.getAttribute("name");
            
            // Buscar ou criar perfil do usuário
            PerfilUsuario perfil = perfilUsuarioRepository.findByGithubUsername(githubUsername)
                .orElseGet(() -> {
                    PerfilUsuario novoPerfil = new PerfilUsuario(githubUsername, githubName);
                    return perfilUsuarioRepository.save(novoPerfil);
                });
            
            model.addAttribute("githubUser", oauth2User);
            model.addAttribute("userAvatar", oauth2User.getAttribute("avatar_url"));
            model.addAttribute("userName", githubName);
            model.addAttribute("userLogin", githubUsername);
            model.addAttribute("perfil", perfil);
            
            // Lista de cargos disponíveis
            model.addAttribute("cargos", new String[]{
                "Desenvolvedor Frontend", 
                "Desenvolvedor Backend", 
                "Desenvolvedor Full Stack",
                "DevOps Engineer",
                "Analista de Sistemas",
                "Arquiteto de Software",
                "Tech Lead",
                "Gerente de Projetos",
                "Product Owner",
                "Scrum Master",
                "UI/UX Designer",
                "QA Engineer",
                "Data Scientist",
                "Analista de Dados",
                "Gerente de Frota",
                "Coordenador de Logística"
            });
        }
        return "usuario";
    }
    
    @PostMapping("/usuario/salvar")
    public String salvarPerfil(@ModelAttribute PerfilUsuario perfil, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String githubUsername = oauth2User.getAttribute("login");
            String githubName = oauth2User.getAttribute("name");
            
            // Buscar perfil existente ou criar novo
            PerfilUsuario perfilExistente = perfilUsuarioRepository.findByGithubUsername(githubUsername)
                .orElseGet(() -> {
                    PerfilUsuario novoPerfil = new PerfilUsuario();
                    novoPerfil.setGithubUsername(githubUsername);
                    return novoPerfil;
                });
            
            // Atualizar dados (mantém o nome do GitHub se não foi alterado)
            perfilExistente.setNomeCompleto(perfil.getNomeCompleto() != null && !perfil.getNomeCompleto().trim().isEmpty() 
                ? perfil.getNomeCompleto() : githubName);
            perfilExistente.setEmail(perfil.getEmail());
            perfilExistente.setTelefone(perfil.getTelefone());
            perfilExistente.setCargo(perfil.getCargo());
            
            perfilUsuarioRepository.save(perfilExistente);
        }
        
        return "redirect:/usuario?sucesso=true";
    }
    
    @GetMapping("/painel")
    public String painel(Model model, Authentication authentication) {
        return home(model, authentication); // Redireciona para a home que já tem o dashboard
    }
}
