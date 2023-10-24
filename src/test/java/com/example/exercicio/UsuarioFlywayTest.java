package com.example.exercicio;

import com.example.exercicio.entities.UsuarioFlyway;

import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.repository.UsuarioFlywayRepository;
import com.example.exercicio.service.serviceImpl.UsuarioFlywayServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

//@DataJpaTest
@SpringBootTest
public class UsuarioFlywayTest {

    @Autowired
    private UsuarioFlywayServiceImpl usuarioFlywayServiceImpl;

    @Autowired
    private UsuarioFlywayRepository usuarioFlywayRepository;

    @Test
    public void testBookCRUD() {

        //        // Create
        //        UsuarioFlyway usuarioFlyway = new UsuarioFlyway();
        //        usuarioFlyway.setNome("Sample Bookss");
        //        usuarioFlyway.setEmail("testess@teste.com");
        //        usuarioFlyway.setNumero("123456");
        //        usuarioFlyway.setData(new Date().toString().toLowerCase(Locale.ROOT));
        //        usuarioFlyway.setUsuarioEnumTypeEnum(UsuarioEnumType.NOME);
        //        usuarioFlyway = usuarioFlywayServiceImpl.salvarUsuarioFlyway(usuarioFlyway);
        //
        //        Assertions.assertNotNull(usuarioFlyway.getId());
        //
        //        // Read
        //        Optional<UsuarioFlyway> retrievedUser = usuarioFlywayServiceImpl.findById(usuarioFlyway.getId());
        //        Assertions.assertNotNull(retrievedUser);
        //
        //        Assertions.assertEquals("Sample Bookss", retrievedUser.get().getNome());
        //        Assertions.assertEquals("testess@teste.com", retrievedUser.get().getEmail());
        //
        //        // Update
        //        retrievedUser.get().setNome("Sample Book Update");
        //        retrievedUser.get().setEmail("Updated Authorss");
        //        retrievedUser.get().setNumero("123456789");
        //        retrievedUser.get().setData(new Date().toString().toLowerCase(Locale.ROOT));
        //        retrievedUser.get().setUsuarioEnumTypeEnum(UsuarioEnumType.NOME);
        //
        //        Optional<UsuarioFlyway> updatedBook = Optional.ofNullable(usuarioFlywayServiceImpl.edit(retrievedUser.get()));
        //
        //        Assertions.assertEquals("Updated Authorss", updatedBook.get().getEmail());
        //        Assertions.assertEquals("Sample Book Update", updatedBook.get().getNome());
        //        Assertions.assertEquals(updatedBook.get().getData(), updatedBook.get().getData());
        //        Assertions.assertEquals("123456789", updatedBook.get().getNumero());
        //
        //        // Delete
        //        usuarioFlywayServiceImpl.deleteUser(updatedBook.get().getId());
        //        Optional<UsuarioFlyway> deletedBook = usuarioFlywayServiceImpl.findById(updatedBook.get().getId());
        //
        //        Assertions.assertNull(deletedBook.get());

        // Create
        UsuarioFlyway usuarioFlyway = new UsuarioFlyway();
        usuarioFlyway.setNome("Sample User");
        usuarioFlyway.setEmail("sample@test.com");
        usuarioFlyway.setNumero("123456");
        usuarioFlyway.setData(new Date().toString().toLowerCase(Locale.ROOT));
        usuarioFlyway.setUsuarioEnumTypeEnum(UsuarioEnumType.NOME);
        usuarioFlyway = usuarioFlywayServiceImpl.salvarUsuarioFlyway(usuarioFlyway);

        Assert.assertNotNull(usuarioFlyway.getId());

        // Read
        Optional<UsuarioFlyway> retrievedUser = usuarioFlywayServiceImpl.findById(usuarioFlyway.getId());
        Assert.assertTrue(retrievedUser.isPresent());

        Assertions.assertEquals("Sample User", retrievedUser.get().getNome());
        Assertions.assertEquals("sample@test.com", retrievedUser.get().getEmail());

        // Update
        retrievedUser.get().setNome("Updated User");
        retrievedUser.get().setEmail("updated@test.com");
        retrievedUser.get().setNumero("123456789");
        retrievedUser.get().setData(new Date().toString().toLowerCase(Locale.ROOT));
        retrievedUser.get().setUsuarioEnumTypeEnum(UsuarioEnumType.NOME);

        Optional<UsuarioFlyway> updatedUser = Optional.ofNullable(usuarioFlywayServiceImpl.edit(retrievedUser.get()));

        Assertions.assertEquals("updated@test.com", updatedUser.get().getEmail());
        Assertions.assertEquals("Updated User", updatedUser.get().getNome());
        Assertions.assertEquals(retrievedUser.get().getData(), updatedUser.get().getData());
        Assertions.assertEquals("123456789", updatedUser.get().getNumero());

        // Delete
        usuarioFlywayServiceImpl.deleteUser(updatedUser.get().getId());
        Optional<UsuarioFlyway> deletedUser = usuarioFlywayServiceImpl.findById(updatedUser.get().getId());

        Assert.assertFalse(deletedUser.isPresent());
    }
}



