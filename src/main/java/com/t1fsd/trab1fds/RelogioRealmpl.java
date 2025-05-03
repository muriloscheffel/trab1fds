package com.t1fsd.trab1fds;

import java.time.LocalDateTime;

public class RelogioRealmpl implements Relogio {
    @Override
    public int getHora() {
        return LocalDateTime.now().getHour();
    }

    @Override
    public int getMinuto() {
        return LocalDateTime.now().getMinute();
    }

}
