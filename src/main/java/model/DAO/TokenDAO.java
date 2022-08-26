package model.DAO;

import exceptions.NotAuthorizedException;
import model.Token;
import model.repository.TokenRepository;
import org.springframework.stereotype.Component;

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

    public void update(Token token) throws NotAuthorizedException {
        Token foundToken = findTokenByUserToken(token.getUserToken());
        foundToken = token;
        repository.save(foundToken);
    }

    public Optional<Token> findById(Long id) {
        return repository.findById(id);
    }

    public List<Token> findAll() {
        return repository.findAll();
    }

    public Token findTokenByUserToken(String userToken) throws NotAuthorizedException {
        for (Token tokenFound : repository.findAll()) {
            if (tokenFound.getUserToken().equals(userToken)) {
                return tokenFound;
            }
        }
        throw new NotAuthorizedException();
    }
}
