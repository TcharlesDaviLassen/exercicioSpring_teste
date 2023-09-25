package com.example.exercicio.service.serviceImpl;

import com.example.exercicio.entities.UsuarioFlyway;
import com.example.exercicio.errorsUtils.CustomExceptionHandler;
import com.example.exercicio.errorsUtils.customRuntimeExempion.CustomException;
import com.example.exercicio.errorsUtils.customRuntimeExempion.ResourceFoundExceptionWithHttpStatus;
import com.example.exercicio.repository.UsuarioFlywayRepository;
import com.example.exercicio.service.UsuarioFlywayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class UsuarioFlywayServiceImpl implements UsuarioFlywayService {
    private final UsuarioFlywayRepository usuarioRepository;

    @Autowired
    public UsuarioFlywayServiceImpl(UsuarioFlywayRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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

        UsuarioFlyway findNomeEmail = usuarioRepository.findByNomeAndEmail(usuarioFlyway.getNome(), usuarioFlyway.getEmail());

        if (usuarioFlyway.getNome().equals(findNomeEmail.getNome()) || usuarioFlyway.getEmail().equals(findNomeEmail.getEmail())) {
            throw new ResourceFoundExceptionWithHttpStatus(HttpStatus.CONFLICT, "Nome e email não podem ser iguais ao que já estão cadastrados");
        }

        usuarioFlyway.setData(dataFormatada);
        return usuarioRepository.save(usuarioFlyway);
    }

    @Override
    public List<UsuarioFlyway> findByAll() {
        return usuarioRepository.findAll();
    }
}
