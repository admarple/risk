package com.github.admarple.risk.gameplay.conquest;

import com.github.admarple.risk.gameplay.Phase;
import com.github.admarple.risk.gameplay.Risk;
import com.github.admarple.risk.model.Player;

import lombok.Data;
import lombok.NonNull;

@Data
public class AttackPhase implements Phase<AttackPhaseCommand> {

    @NonNull private final Player player;
    @NonNull private final Risk risk;
    private boolean doneAttacking = false;

    @Override
    public AttackPhaseCommand getCommand() {
        return new AttackPhaseCommand();
    }

    @Override
    public void perform(AttackPhaseCommand command) {
        boolean cardAwarded = false;
        while (!isDoneAttacking()) {
            AttackStep nextAttack = new AttackStep(risk, player);
            nextAttack.play();
            if (nextAttack.isYielded()) {
                doneAttacking = true;
            }
            if (nextAttack.isVictory()) {
                cardAwarded = true;
            }
        }
        if (cardAwarded) {
            player.getHand().add(risk.getBoard().draw());
        }
    }
}
