package model.DAO;

import model.Token;
import model.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Component
public class TokenDAO {
    private final TokenRepository repository;

    public TokenDAO(TokenRepository repository) {
        this.repository = repository;
    }

    public Token save(Token token) {
        try {
            return repository.save(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Token token) {
        repository.delete(token);
    }

    public void update(Token token) {
        //TODO update
    }

    public Optional<Token> findById(Long id) {
        return repository.findById(id);
    }

    public List<Token> findAll() {
        return repository.findAll();
    }
}
