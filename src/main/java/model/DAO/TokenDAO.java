package model.DAO;

import model.Token;
import model.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenDAO {
    private final TokenRepository repository;

    public TokenDAO(TokenRepository repository) {
        this.repository = repository;
    }

    public Token save(Token token) {
        //TODO save
        try {
            return repository.save(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete() {
        //TODO delete
    }

    public void update() {
        //TODO update
    }

    public Token findById(Long id) {
        //TODO findById
        return null;
    }

    public List<Token> findAll() {
        //TODO findAll
        return null;
    }
}
