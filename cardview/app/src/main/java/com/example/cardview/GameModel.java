package com.example.cardview;

public class GameModel {
    private  String game_name;
    private  int imge;

    public GameModel(String game_name, int imge) {
        this.game_name = game_name;
        this.imge = imge;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getImge() {
        return imge;
    }

    public void setImge(int imge) {
        this.imge = imge;
    }
}
