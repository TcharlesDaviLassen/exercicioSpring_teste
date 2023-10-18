package com.example.exercicio.service.serviceImpl;

import com.example.exercicio.DTO.User;
import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.repository.UsuarioFlywayRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiTransactionExampleService {

    @Autowired
    private UsuarioFlywayRepository userRepository;

    @Transactional
    public void performSuccessfulTransaction() {
        //  User user1 = new User("User1", "user@teste.com", "999999999", "teste", false);
        UsuarioFlyway user1 = new UsuarioFlyway(1L,"User1", "999999999", "user@teste.com", "", UsuarioEnumType.E);
        userRepository.save(user1);
    }

    @Transactional
    public void performFailedTransaction() {
        //        try {
            UsuarioFlyway user2 = new UsuarioFlyway(1L,"User2", "999999999", "userss@teste.com", "", UsuarioEnumType.E);
            userRepository.save(user2);
        //        } catch (Exception e) {
        //             System.out.println(e.getMessage());
        //        }

        // Lança uma exceção intencionalmente para simular uma falha
          throw new RuntimeException("Erro durante a operação transacional");
    }
}
