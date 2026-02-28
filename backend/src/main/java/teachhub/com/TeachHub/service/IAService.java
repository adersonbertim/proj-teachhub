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

    public  IAService(ChatModel chatModel, LogIARepository logIARepository) {
        this.chatModel = chatModel;
        this.logIARepository = logIARepository;
    }

    public String pergunta(String pergunta, Usuario usuario){
        // System Prompt: Definimos a personalidade da IA
        String instrucaoMestre = "Você é o assistente oficial do TeachHub. " +
                "Sua missão é ajudar alunos a encontrar cursos e professores a gerenciar postagens. " +
                "Responda de forma gentil e técnica. Pergunta do usuário: ";

        // Chamada para o ChatGPT
        String resposta = chatModel.call(instrucaoMestre + pergunta);

        //Salvando os logs
        LogIA logs = new LogIA();
        logs.setPergunta(pergunta);
        logs.setResposta(resposta);
        logs.setData(LocalDateTime.now());
        logs.setUser(usuario);
        logIARepository.save(logs);

        return resposta;
    }

    public List<LogIA> historico(Usuario usuario){
        return  logIARepository.findByUserOrderByDataDesc(usuario);
    }
}
