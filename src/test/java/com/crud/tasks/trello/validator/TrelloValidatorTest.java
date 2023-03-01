package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloValidatorTest {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void validateTrelloBoards() {

        List<TrelloBoard> trelloBoards = List.of(
                new TrelloBoard("1", "test", new ArrayList<>()),
                new TrelloBoard("2", "NewBoard", new ArrayList<>())
        );

        List<TrelloBoard> trelloBoardsAfterValidation = trelloValidator.validateTrelloBoards(trelloBoards);

        assertEquals(1, trelloBoardsAfterValidation.size());
        assertEquals(trelloBoards.get(1).getName(), trelloBoardsAfterValidation.get(0).getName());
    }
}