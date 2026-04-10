package teachhub.com.TeachHub.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import teachhub.com.TeachHub.model.log_ia.LogIA;
import teachhub.com.TeachHub.model.log_ia.LogIARepository;
import teachhub.com.TeachHub.model.usuarios.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IAService {
    private final ChatModel chatModel;
    private final LogIARepository logIARepository;

    public IAService(ChatModel chatModel, LogIARepository logIARepository) {
        this.chatModel = chatModel;
        this.logIARepository = logIARepository;
    }

    public String pergunta(String pergunta, Usuario usuario) {
        String instrucaoMestre = "Você é o assistente oficial do TeachHub. " +
                "Sua missão é ajudar alunos a encontrar cursos e professores a gerenciar postagens. " +
                "Responda de forma gentil e técnica. Pergunta do usuário: ";

        // 1. Chamada para a IA (Isso deve funcionar se a API Key no properties estiver ok)
        String resposta = chatModel.call(instrucaoMestre + pergunta);

        // 2. PROTEÇÃO: Só salva no banco se houver um usuário logado
        // Sem isso, o JPA tenta salvar 'null' no banco e gera Erro 500
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

    public List<LogIA> historico(Usuario usuario) {
        // 3. PROTEÇÃO: Se não há usuário, não há histórico para buscar
        if (usuario == null) {
            return List.of(); // Retorna lista vazia em vez de dar erro
        }
        return logIARepository.findByUserOrderByDataDesc(usuario);
    }
}