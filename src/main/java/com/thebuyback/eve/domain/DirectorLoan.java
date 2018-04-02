package com.thebuyback.eve.domain;

import org.springframework.data.annotation.Id;

public class DirectorLoan {
    @Id
    private String player;
    private Long loan;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(final String player) {
        this.player = player;
    }

    public Long getLoan() {
        return loan;
    }

    public void setLoan(final Long loan) {
        this.loan = loan;
    }
}
