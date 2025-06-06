package br.com.fiap.safecap.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Base64;

public class GenerateJwtKey {
    public static void main(String[] args) {
        // Gerando uma chave segura de 512 bits para HS512
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        
        // Convertendo a chave para Base64 para fácil configuração
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        
        System.out.println("Chave JWT gerada (Base64):");
        System.out.println(encodedKey);
        
        // Exemplo de como usar a chave
        System.out.println("\nExemplo de uso no application.properties:");
        System.out.println("jwt.secret=" + encodedKey);
    }
} 