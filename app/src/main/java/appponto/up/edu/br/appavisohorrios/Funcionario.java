package appponto.up.edu.br.appavisohorrios;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * Created by biafo on 15/06/2018.
 */

public class Funcionario implements Serializable {

    private Integer id;
    private String Nome;
    private String Email;
    private String HoraEntrada;
    private String HoraSaida;
    private String HoraAlmoco;


    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHoraEntrada() {
        return HoraEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        HoraEntrada = horaEntrada;
    }

    public String getHoraSaida() {
        return HoraSaida;
    }

    public void setHoraSaida(String horaSaida) {
        HoraSaida = horaSaida;
    }

    public String getHoraAlmoco() {
        return HoraAlmoco;
    }

    public void setHoraAlmoco(String horaAlmoco) {
        HoraAlmoco = horaAlmoco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
