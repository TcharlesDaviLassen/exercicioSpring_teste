package com.example.exercicio.service.serviceImpl;

import com.example.exercicio.DTO.UsuarioDTO;
import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.errorsUtils.customRuntimeExempion.CustomException;
import com.example.exercicio.repository.UsuarioFlywayRepository;
import com.example.exercicio.repository.repositoryDAOImpl.UsuarioFlywayRepositoryDAOImpl;
import com.example.exercicio.service.UsuarioFlywayService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UsuarioFlywayServiceImpl implements UsuarioFlywayService {

    private final UsuarioFlywayRepository usuarioRepository;
    private final UsuarioFlywayRepositoryDAOImpl usuarioFlywayRepositoryDAO;
    private final EntityManager entityManager;
    @Autowired
    public UsuarioFlywayServiceImpl(UsuarioFlywayRepository usuarioRepository, UsuarioFlywayRepositoryDAOImpl usuarioFlywayRepositoryDAO, EntityManager entityManager) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioFlywayRepositoryDAO = usuarioFlywayRepositoryDAO;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<UsuarioFlyway> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public UsuarioFlyway create(UsuarioFlyway usuarioFlyway) {
        Date dateGMT = new Date();
        TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getDefault().toZoneId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        String dataFormatada = dateFormat.format(dateGMT);

        usuarioFlyway.setData(dataFormatada);

        //        List<UsuarioFlyway> findNomeEmail = usuarioRepository.findAll();
        //        for (int i = 0; i < findNomeEmail.size() ; i++) {
        //            if (usuarioFlyway.getNome().equals(findNomeEmail.get(i).getNome()) || usuarioFlyway.getEmail().equals(findNomeEmail.get(i).getEmail())) {
        //                throw new IllegalArgumentException("Nome e email não podem ser iguais ao que já estão cadastrados");
        //            }
        //        }

        return usuarioRepository.save(usuarioFlyway);

    }



    @Override
    public UsuarioFlyway salvarUsuarioFlyway(UsuarioFlyway usuarioFlyway) {
        // Suponha que você tenha uma data em GMT como java.util.Date
        Date dateGMT = new Date();

        // Defina o fuso horário desejado (por exemplo, "America/New_York")
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

        // Crie um SimpleDateFormat com o fuso horário desejado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);

        // Converta a data para a representação do fuso horário desejado
        String dataFormatada = dateFormat.format(dateGMT);

        // Analise a string para criar um objeto Date
        //  Date date = dateFormat.parse(dataFormatada);

        //        // Crie um objeto Calendar para a data e hora atual
        //        Calendar calendar = Calendar.getInstance();
        //
        //        // Defina o fuso horário desejado (por exemplo, "America/New_York")
        //        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
        //
        //        // Atribua o fuso horário ao objeto Calendar
        //        calendar.setTimeZone(timeZone);
        //
        //        // Obtém a data e hora atual com o fuso horário especificado
        //        Calendar dataComFuso = Calendar.getInstance(timeZone);

        List<UsuarioFlyway> findNomeEmail = usuarioRepository.findAll();

        for (int i = 0; i < findNomeEmail.size() ; i++) {
            if (usuarioFlyway.getNome().equals(findNomeEmail.get(i).getNome()) || usuarioFlyway.getEmail().equals(findNomeEmail.get(i).getEmail())) {
                throw new CustomException("Nome e email não podem ser iguais ao que já estão cadastrados", HttpStatus.CONFLICT);
            }
        }

        return usuarioRepository.save(usuarioFlyway);
    }

    @Override
    public List<UsuarioFlyway> findByAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioFlyway edit(UsuarioFlyway usuarioFlyway) {

        var findUser = usuarioRepository.findById(usuarioFlyway.getId());

        Date dateGMT = new Date();

        // Defina o fuso horário desejado (por exemplo, "America/New_York")
        TimeZone timeZone = TimeZone.getTimeZone("America/New_York");

        // Crie um SimpleDateFormat com o fuso horário desejado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(timeZone);

        // Converta a data para a representação do fuso horário desejado
        String dataFormatada = dateFormat.format(dateGMT);

        if (findUser.isPresent()) {
            findUser.get().setNome(usuarioFlyway.getNome());
            findUser.get().setNumero(usuarioFlyway.getNumero());
            findUser.get().setEmail(usuarioFlyway.getEmail());
            //            findUser.get().setData(usuarioFlyway.getData().toString().replace("T", " "));
            //            findUser.get().setData(dataFormatada);
            findUser.get().setUsuarioEnumTypeEnum(usuarioFlyway.getUsuarioEnumTypeEnum());

            return usuarioRepository.save(findUser.get());
        }

        return null;
    }


    @Override
    public void deleteUser(Long id) {
        var findIdDelete = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do usuário inválido: " + id));

        usuarioRepository.deleteById(findIdDelete.getId());

    }

    @Override
    public List<UsuarioFlyway> findByEnum(UsuarioEnumType usuarioFlyway) {
        return usuarioRepository.findByUsuarioEnumTypeEnum(usuarioFlyway);
    }

    @Override
    public List<UsuarioFlyway> findByNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    @Override
    public List<UsuarioFlyway> findByNumero(String numero) {
        return usuarioRepository.findByNumero(numero);
    }

    @Override
    public List<UsuarioFlyway> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<UsuarioFlyway> findByData(String data) {
        return usuarioRepository.findByData(data);
    }

    public List<UsuarioFlyway> filtrarUsuariosType(UsuarioEnumType enumType) {
        return usuarioRepository.findByUsuarioEnumTypeEnum(enumType);
    }

    public List<UsuarioFlyway> filtrarUsuarios(UsuarioFlyway filtro) {
        return usuarioFlywayRepositoryDAO.filtrarUsuarios(filtro);
    }

    @Override
    public List<UsuarioFlyway> findByNomeAndEmail(String nome, String email) {
        return usuarioRepository.findByNomeAndEmail(nome, email);
    }


    public List<String> getEnumListFromDatabase() {
        // Substitua "UsuarioEntity" pelo nome da sua entidade que contém a enumeração.
        String sql = "SELECT DISTINCT usuario_enum_type_enum FROM usuario_flyway";

        Query query = entityManager.createNativeQuery(sql);
        List<String> enumNames = query.getResultList();

        return enumNames;
    }
}
