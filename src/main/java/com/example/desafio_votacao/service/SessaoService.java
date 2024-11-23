package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.exception.ValidationException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaService pautaService;

    /**
     * Abre uma nova sessão para uma pauta específica, com duração opcional.
     *
     * @param pautaId o ID da pauta para a qual a sessão será aberta
     * @param duracaoEmMinutos a duração da sessão em minutos (se não fornecido, será 1 minuto)
     * @return a sessão criada
     * @throws ValidationException caso o ID da pauta seja inválido ou se já existir uma sessão aberta para a pauta
     */
    public Sessao abrirSessao(Long pautaId, Integer duracaoEmMinutos) {
        if (pautaId == null) {
            throw new ValidationException("O ID da pauta é obrigatório.");
        }

        Pauta pauta = pautaService.buscarPautaPorId(pautaId);

        if (sessaoRepository.existsByPautaIdAndFimAfter(pautaId, LocalDateTime.now())) {
            throw new ValidationException("Já existe uma sessão aberta para a pauta informada.");
        }

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(duracaoEmMinutos != null ? duracaoEmMinutos : 1));

        return sessaoRepository.save(sessao);
    }

    /**
     * Busca uma sessão pelo ID.
     *
     * @param id o ID da sessão a ser buscada
     * @return a sessão encontrada
     * @throws ValidationException caso o ID seja inválido ou caso a sessão não seja encontrada
     */
    public Sessao buscarSessaoPorId(Long id) {
        if (id == null) {
            throw new ValidationException("O ID da sessão é obrigatório.");
        }

        return sessaoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Sessão", id)
        );
    }

    /**
     * Verifica se uma sessão está aberta.
     *
     * @param sessaoId o ID da sessão a ser verificada
     * @return true se a sessão estiver aberta, caso contrário false
     * @throws ValidationException caso a sessão esteja fechada
     */
    public boolean isSessaoAberta(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);
        boolean isAberta = LocalDateTime.now().isBefore(sessao.getFim());
        if (!isAberta) {
            throw new ValidationException("A sessão está fechada.");
        }
        return isAberta;
    }

    /**
     * Lista todas as sessões.
     *
     * @return uma lista contendo todas as sessões
     */
    public List<Sessao> listarTodasSessoes() {
        return sessaoRepository.findAll();
    }

    /**
     * Lista todas as sessões abertas (com fim posterior à data e hora atual).
     *
     * @return uma lista contendo as sessões abertas
     */
    public List<Sessao> listarSessoesAbertas() {
        return sessaoRepository.findByFimAfter(LocalDateTime.now());
    }
}