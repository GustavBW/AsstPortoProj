package guwan21.common.data.entityparts;

import guwan21.common.data.entities.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EnemyRecordTest {

    @Test
    void contains() {
        EnemyRecord record = new EnemyRecord(Asteroid.class, Player.class);
        assertTrue(record.contains(new Asteroid()));
        assertTrue(record.contains(new Player()));
        //However, super classes or unlisted classes should not return true
        assertFalse(record.contains(new Entity()));
        assertFalse(record.contains(new Bullet()));
    }

    @Test
    void verifyThatRecordIsUsed(){
        LifePart lp = new LifePart(1,1);
        EnemyRecord er = mock(EnemyRecord.class);
        Entity victim = mock(Entity.class);
        when(victim.getPart(EnemyRecord.class)).thenReturn(er);
        lp.verifyHit(mock(Entity.class), victim);
        //Verify that the EnemyRecord is checked
        verify(er, times(1)).contains(any(Entity.class));
    }

    @Test
    void verifyThatLifePartIsNotHitOnFriendlyHit(){
        LifePart lp = new LifePart(1,1);
        EnemyRecord er = new EnemyRecord(Player.class);
        Entity victim = mock(Entity.class);

        when(victim.getPart(EnemyRecord.class)).thenReturn(er);
        assertFalse(lp.verifyHit(mock(Entity.class), victim));
    }

    @Test
    void verifyThatIsEnemyIfParentIs(){
        EnemyRecord er = new EnemyRecord(Player.class);
        Bullet offender = new Bullet();
        offender.setParent(new Player());
        Entity victim = mock(Entity.class);
        when(victim.getPart(EnemyRecord.class)).thenReturn(er);
        LifePart lp = new LifePart(1,1);

        //when hit, although the bullet is not an enemy of the victim
        //its parent is a Player, so it shall still count
        assertTrue(lp.verifyHit(offender,victim));
    }
}