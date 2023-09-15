package com.example.viewpager;

import android.icu.text.CaseMap;

public enum PageModel  {

          RED(R.string.red, R.layout.layout1),
          BLUE(R.string.blue,R.layout.layour2),
          GREEN(R.string.green,R.layout.layout3);

          private int title;
          private  int layout;

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    PageModel(int title, int layout) {
        this.title = title;
        this.layout = layout;
    }
}

