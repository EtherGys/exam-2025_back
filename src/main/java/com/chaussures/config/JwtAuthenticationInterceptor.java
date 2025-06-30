package com.chaussures.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.chaussures.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtService jwtService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Exclure les endpoints publics de l'authentification
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        if (isPublicEndpoint(requestURI)) {
            return true;
        }
        
        // Récupérer le token depuis le header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token manquant\"}");
            return false;
        }
        
        String token = authHeader.substring(7); // Enlever "Bearer "
        
        try {
            // Valider le token
            String email = jwtService.extractEmail(token);
            Long userId = jwtService.extractUserId(token);
            String role = jwtService.extractUserRole(token);
            
            if (jwtService.validateToken(token, email)) {
                // Vérification du rôle Barmaker pour les endpoints sensibles
                if (requestURI.startsWith("/api/cartes") && ("POST".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))) {
                    if (!"BARMaker".equalsIgnoreCase(role)) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\":\"Accès réservé aux barmakers\"}");
                        return false;
                    }
                }
                // Ajouter les informations utilisateur à la requête
                request.setAttribute("userId", userId);
                request.setAttribute("userEmail", email);
                request.setAttribute("userRole", role);
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token invalide ou expiré\"}");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erreur JWT: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token invalide\",\"details\":\"" + e.getMessage() + "\"}");
            return false;
        }
    }
    
    /**
     * Définit les endpoints publics qui ne nécessitent pas d'authentification
     */
    private boolean isPublicEndpoint(String requestURI) {
        return requestURI.startsWith("/api/clients/login") ||
               requestURI.startsWith("/api/clients/validate-token") ||
               requestURI.startsWith("/api/test") ||
               requestURI.startsWith("/health") ||
               requestURI.startsWith("/info") ||
               requestURI.equals("/") ||
               (requestURI.startsWith("/api/clients") && requestURI.matches(".*/\\d+$")); // GET /api/clients/{id}
    }
} 