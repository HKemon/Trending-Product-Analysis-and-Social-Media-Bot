import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Member;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;

import java.util.ArrayList;
import java.util.List;

public class TESTCLASS {
    public static void main(String[] args) {
        String trelloKey = "b02d5e6a4657d5b6f34d33511519780e";
        String trelloAccessToken = "c143cef55f94982c7d17b78f7c178189b16b4c5033397cbcc1a2b1674b8336cd";
        Trello trelloApi = new TrelloImpl(trelloKey, trelloAccessToken, new ApacheHttpClient());

        Board board = trelloApi.getBoard("TrTJ1ya6");
        System.out.println(board.getName());
        List<Member> lists = board.fetchMembers();
        for (Member list : lists) {
//            list.getName();
            System.out.println(list.getFullName());
        }
    }
}
