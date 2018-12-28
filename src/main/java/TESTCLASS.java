import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;

import java.util.List;

public class TESTCLASS {
    public static void main(String[] args) {
        String applicationKey = "b02d5e6a4657d5b6f34d33511519780e";
        String userToken = "c143cef55f94982c7d17b78f7c178189b16b4c5033397cbcc1a2b1674b8336cd";
        String boardId = "TrTJ1ya6";
//        Trello trello = new TrelloImpl("myApiKey", "myToken");
        Trello trelloApi = new TrelloImpl(applicationKey, userToken, new ApacheHttpClient());
//
        Board board = trelloApi.getBoard(boardId);
//        System.out.println(board.getBoa);
        Card card = new Card();
        card.setName("demo");

        trelloApi.createCard("5abe82f7964e27e90f6403f8", card);

//        Board trTJ1ya6 = trelloApi.getBoard("TrTJ1ya6");
////        trTJ1ya6.setName("emonemon");
//        Card boardCard = trelloApi.getBoardCard("TrTJ1ya6", "5c26235ca9970548e6c695f6");
//        System.out.println(boardCard.getDesc());
//
//        List<Card> cards = trelloApi.getBoardCards("TrTJ1ya6");
//        List<TList> lists = board.fetchLists();
//        for (TList list : lists) {
//            System.out.println(list.getId() + " " + list.getName());
//        }
//
//        for (Card list : cards) {
//            System.out.println(list.getName() + " " + list.getId());
//        }
    }
}
