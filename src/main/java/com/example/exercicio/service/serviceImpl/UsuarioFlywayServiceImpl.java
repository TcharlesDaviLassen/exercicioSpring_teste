package com.example.exercicio.service.serviceImpl;

import com.example.exercicio.DTO.UsuarioDTO;
import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.enumType.UsuarioEnumType;
import com.example.exercicio.errorsUtils.customRuntimeExempion.CustomException;
import com.example.exercicio.repository.UsuarioFlywayRepository;
import com.example.exercicio.repository.repositoryDAOImpl.UsuarioFlywayRepositoryDAOImpl;
import com.example.exercicio.service.UsuarioFlywayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class UsuarioFlywayServiceImpl implements UsuarioFlywayService {

    private final UsuarioFlywayRepository usuarioRepository;
    private final UsuarioFlywayRepositoryDAOImpl usuarioFlywayRepositoryDAO;
    @Autowired
    public UsuarioFlywayServiceImpl(UsuarioFlywayRepository usuarioRepository, UsuarioFlywayRepositoryDAOImpl usuarioFlywayRepositoryDAO) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioFlywayRepositoryDAO = usuarioFlywayRepositoryDAO;
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

        List<UsuarioFlyway> findNomeEmail = usuarioRepository.findAll();

        for (int i = 0; i < findNomeEmail.size() ; i++) {
            if (usuarioFlyway.getNome().equals(findNomeEmail.get(i).getNome()) || usuarioFlyway.getEmail().equals(findNomeEmail.get(i).getEmail())) {
                throw new IllegalArgumentException("Nome e email não podem ser iguais ao que já estão cadastrados");
            }
        }

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

        usuarioFlyway.setData(dataFormatada);
        return usuarioRepository.save(usuarioFlyway);
    }

    @Override
    public List<UsuarioFlyway> findByAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public UsuarioFlyway edit(UsuarioFlyway usuarioFlyway) {

        var findUser = usuarioRepository.findById(usuarioFlyway.getId());

        if (findUser.isPresent()) {
            findUser.get().setNome(usuarioFlyway.getNome());
            findUser.get().setNumero(usuarioFlyway.getNumero());
            findUser.get().setEmail(usuarioFlyway.getEmail());
            findUser.get().setData(usuarioFlyway.getData());
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

    public List<UsuarioFlyway> filtrarUsuarios(UsuarioFlyway filtro) {
        return usuarioFlywayRepositoryDAO.filtrarUsuarios(filtro);
    }

    @Override
    public List<UsuarioFlyway> findByNomeAndEmail(String nome, String email) {
        return usuarioRepository.findByNomeAndEmail(nome, email);
    }
}
