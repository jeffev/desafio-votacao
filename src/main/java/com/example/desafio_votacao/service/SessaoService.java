package com.example.desafio_votacao.service;

import com.example.desafio_votacao.exception.ResourceNotFoundException;
import com.example.desafio_votacao.exception.ValidationException;
import com.example.desafio_votacao.model.Pauta;
import com.example.desafio_votacao.model.Sessao;
import com.example.desafio_votacao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Sessao abrirSessao(Long pautaId, Integer duracaoEmMinutos) {
        validarId(pautaId);

        Pauta pauta = pautaService.buscarPautaPorId(pautaId);
        LocalDateTime now = LocalDateTime.now();

        if (sessaoRepository.existsByPautaIdAndFimAfter(pautaId, now)) {
            throw new ValidationException("Já existe uma sessão aberta para a pauta informada.");
        }

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setInicio(now);
        sessao.setFim(now.plusMinutes(duracaoEmMinutos != null ? duracaoEmMinutos : 1));

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
        validarId(id);

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
        LocalDateTime now = LocalDateTime.now();

        boolean isAberta = now.isBefore(sessao.getFim());
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
    public List<Sessao> listarTodasSessoes(Pageable pageable) {
        return sessaoRepository.findAll(pageable).getContent();
    }

    /**
     * Lista todas as sessões abertas (com fim posterior à data e hora atual).
     *
     * @return uma lista contendo as sessões abertas
     */
    public List<Sessao> listarSessoesAbertas(Pageable pageable) {
        return sessaoRepository.findByFimAfter(LocalDateTime.now(), pageable);
    }

    /**
     * Valida se o ID é nulo e lança uma exceção em caso positivo.
     *
     * @param id o ID a ser validado
     * @throws ValidationException caso o ID seja nulo
     */
    private void validarId(Long id) {
        if (id == null) {
            throw new ValidationException("O ID é obrigatório.");
        }
    }
}
