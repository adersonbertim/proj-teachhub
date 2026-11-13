package teachhub.com.TeachHub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    // So para registrar um log
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final String remetente;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String remetente) {
        this.mailSender = mailSender;
        this.remetente = remetente;
    }

    public void enviarEmail(String destino, String assunto, String texto) {
        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom(remetente);
        mensagem.setTo(destino);
        mensagem.setSubject(assunto);
        mensagem.setText(texto);
        enviarMensagem(mensagem);
    }

    // Mensagens de envio

    private void enviarMensagem(SimpleMailMessage mensagem) {
        try {
            mailSender.send(mensagem);
        } catch (MailException e) {
            logger.error("Não foi possível enviar o email de notificação ({})", e.getClass().getSimpleName());
        }
    }

    public void enviarEmailContaCriada(String destino, String nome) {
        enviarEmail(destino, "Bem-vindo(a) ao TeachHub!",
                "Olá, " + nome + "! Sua conta no TeachHub foi criada com sucesso. "
                        + "Você já pode acessar a plataforma para compartilhar materiais e trocar conhecimentos."
                        + "\n\nEquipe TeachHub");
    }

    public void enviarEmailContaExcluida(String destino, String nome) {
        enviarEmail(destino, "Sua conta no TeachHub foi excluída",
                "Olá, " + nome + ". A exclusão da sua conta no TeachHub foi concluída. "
                        + "Obrigado por ter feito parte da nossa comunidade.\n\nEquipe TeachHub");
    }

    public void enviarEmailSenhaAlterada(String destino, String nome) {
        enviarEmail(destino, "Sua senha no TeachHub foi alterada",
                "Olá, " + nome + ". A senha da sua conta no TeachHub foi alterada com sucesso. "
                        + "Se você não realizou essa alteração, sua conta pode estar comprometida."
                        + "\n\nEquipe TeachHub");
    }

    public void enviarEmailPostagemCriada(String destino, String nome, String titulo) {
        enviarEmail(destino, "Sua postagem foi publicada no TeachHub",
                "Olá, " + nome + "! Sua postagem “" + titulo + "” foi publicada com sucesso no TeachHub."
                        + "\n\nEquipe TeachHub");
    }

    public void enviarEmailPostagemExcluida(String destino, String nome, String titulo) {
        enviarEmail(destino, "Sua postagem foi excluída do TeachHub",
                "Olá, " + nome + ". Sua postagem “" + titulo + "” foi excluída do TeachHub."
                        + "\n\nEquipe TeachHub");
    }

    public void enviarEmailPostagemComentada(String destino, String nome, String titulo, String nomeAutorComentario) {
        enviarEmail(destino, "Sua postagem recebeu um comentário",
                "Olá, " + nome + "! " + nomeAutorComentario + " comentou na sua postagem “" + titulo + "”. "
                        + "Acesse o TeachHub para conferir a conversa.\n\nEquipe TeachHub");
    }
}
