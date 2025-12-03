package com.safecap.controller;

import com.safecap.dto.UsuarioDTO;
import com.safecap.dto.LoginDTO;
import com.safecap.dto.TokenResponseDTO;
import com.safecap.model.Usuario;
import com.safecap.model.Role;
import com.safecap.service.UsuarioService;
import com.safecap.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Operation(summary = "Autentica o usuário e gera o token JWT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = LoginDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n  \"email\": \"teste@fiap.com\",\n  \"senha\": \"123456\"\n}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token gerado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        logger.info("Tentativa de login para: {}", dto.getEmail());

        return usuarioService.findByEmail(dto.getEmail())
                .filter(u -> encoder.matches(dto.getSenha(), u.getSenha()))
                .map(u -> {
                    logger.info("Login bem-sucedido para {}", u.getEmail());
                    String token = jwtUtil.generateToken(u.getEmail());
                    return ResponseEntity.ok(new TokenResponseDTO(token));
                })
                .orElseGet(() -> {
                    logger.warn("Falha no login para {}", dto.getEmail());
                    return ResponseEntity.status(401).build();
                });
    }

    @Operation(summary = "Registra um novo usuário",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = UsuarioDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n  \"nome\": \"João Silva\",\n  \"email\": \"joao@fiap.com\",\n  \"senha\": \"123456\"\n}"
                            )
                    )
            )
    )
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@Valid @RequestBody UsuarioDTO dto) {
        logger.info("Registrando novo usuário: {}", dto.getEmail());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encoder.encode(dto.getSenha()));
        usuario.setRole(Role.USER);

        return ResponseEntity.ok(usuarioService.save(usuario));
    }
}
