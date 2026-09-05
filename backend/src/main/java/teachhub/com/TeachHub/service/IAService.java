package teachhub.com.TeachHub.service;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.model.log_ia.LogIA;
import teachhub.com.TeachHub.model.log_ia.LogIARepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IAService {
    private final ChatModel chatModel;
    private final LogIARepository logIARepository;

    private static final String INSTRUCAO_MESTRE =
            "Você é o assistente oficial do TeachHub. " +
                    "Sua missão é ajudar alunos a encontrar cursos e professores a gerenciar postagens. " +
                    "Responda de forma gentil e técnica.";

    private static final int LIMITE_TROCAS_HISTORICO = 10;

    public IAService(ChatModel chatModel, LogIARepository logIARepository) {
        this.chatModel = chatModel;
        this.logIARepository = logIARepository;
    }

    public String pergunta(String pergunta, Usuario usuario) {
        List<Message> mensagens = new ArrayList<>();
        mensagens.add(new SystemMessage(INSTRUCAO_MESTRE));
        mensagens.addAll(montarHistoricoComoMensagens(usuario));
        mensagens.add(new UserMessage(pergunta));

        String resposta = chatModel.call(new Prompt(mensagens))
                .getResult()
                .getOutput()
                .getContent();

        if (usuario != null && usuario.getId() != null) {
            LogIA logs = new LogIA();
            logs.setPergunta(pergunta);
            logs.setResposta(resposta);
            logs.setData(LocalDateTime.now());
            logs.setUser(usuario);
            logIARepository.save(logs);
        }

        return resposta;
    }

    private List<Message> montarHistoricoComoMensagens(Usuario usuario) {
        if (usuario == null) {
            return List.of();
        }

        List<LogIA> historico = logIARepository.findByUserOrderByDataDesc(usuario);

        List<LogIA> recentes = historico.stream()
                .limit(LIMITE_TROCAS_HISTORICO)
                .toList()
                .reversed();

        List<Message> mensagens = new ArrayList<>();
        for (LogIA log : recentes) {
            mensagens.add(new UserMessage(log.getPergunta()));
            mensagens.add(new AssistantMessage(log.getResposta()));
        }
        return mensagens;
    }

    public List<LogIA> historico(Usuario usuario) {
        if (usuario == null) {
            return List.of();
        }
        return logIARepository.findByUserOrderByDataDesc(usuario);
    }
}