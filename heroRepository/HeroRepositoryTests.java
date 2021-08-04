package heroRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HeroRepositoryTests {

    private HeroRepository actual;
    private static final String HERO_ONE_NAME = "Legolas";
    private static final String HERO_TWO_NAME = "Gimli";
    private static final int HERO_ONE_LEVEL = 100;
    private static final int HERO_TWO_LEVEL = 101;
    private Hero heroOne;
    private Hero heroTwo;
    Hero expected;

    @Before
    public void initTestObjects(){
        actual = new HeroRepository();
        heroOne = new Hero(HERO_ONE_NAME, HERO_ONE_LEVEL);
        heroTwo = new Hero(HERO_TWO_NAME, HERO_TWO_LEVEL);
    }

    @Test
    public void constructorWorksProperly(){
        Assert.assertNotNull(actual);
    }

    @Test
    public void getCountReturnsZeroIfEmptyRepository(){
        int expected = 0;
        Assert.assertEquals(expected, actual.getCount());
    }

    @Test
    public void getCountReturnsOneIfOneElement(){
        actual.create(heroOne);
        int expected = 1;
        Assert.assertEquals(expected, actual.getCount());
    }

    @Test
    public void createAddsHeroToRepositoryAndReturnsInfoAboutIt(){
        String expected = String.format(
                "Successfully added hero %s with level %d", HERO_ONE_NAME, HERO_ONE_LEVEL);
        Assert.assertEquals(expected,actual.create(heroOne));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createThrowsIfHeroWithThatNameAlreadyExist(){
        actual.create(heroOne);
        actual.create(new Hero(HERO_ONE_NAME, HERO_TWO_LEVEL));
    }

    @Test(expected = NullPointerException.class)
    public void createThrowsIfHeroIsNull(){
        actual.create(null);
    }

    @Test(expected = NullPointerException.class)
    public void removeThrowsIfNameIsNull(){
        actual.remove(null);
    }

    @Test(expected = NullPointerException.class)
    public void removeThrowsIfNameIsEmpty(){
        actual.remove(" ");
    }

    @Test
    public void removeReturnsTrueIfHeroRemoved(){
        actual.create(heroOne);
        Assert.assertTrue(actual.remove(HERO_ONE_NAME));
    }

    @Test
    public void removeReturnsFalseIfHeroNotRemoved(){
        actual.create(heroOne);
        Assert.assertFalse(actual.remove(HERO_TWO_NAME));
    }

    @Test
    public void getHeroWithHighestLevelReturnsNullIfEmptyRepository(){
        Assert.assertNull(actual.getHeroWithHighestLevel());
    }

    @Test
    public void getHeroWithHighestLevelWorksProperly(){
        actual.create(heroOne);
        actual.create(heroTwo);
        expected = heroTwo;
        assertEqualsHeroes(expected, actual.getHeroWithHighestLevel());
    }

    @Test
    public void getHeroWithHighestLevelReturnsTheHeroIfOnlyOneInRepository(){
        actual.create(heroOne);
        expected = heroOne;
        assertEqualsHeroes(expected, actual.getHeroWithHighestLevel());
    }

    @Test
    public void getHeroReturnsTheHeroWithGivenName(){
        actual.create(heroOne);
        expected = heroOne;
        assertEqualsHeroes(expected, actual.getHero(HERO_ONE_NAME));
    }

    @Test
    public void getHeroReturnsNullIfNoSuchHero(){
        actual.create(heroOne);
        Assert.assertNull(actual.getHero(HERO_TWO_NAME));
    }

    @Test
    public void getHeroesReturnsUnmodifiableCollection(){
        actual.create(heroOne);
        actual.create(heroTwo);
        List<Hero> expected = new ArrayList<>();
        expected.add(heroOne);
        expected.add(heroTwo);
        Assert.assertEquals(expected.size(), actual.getCount());
        assertEqualsHeroes(expected.get(0), actual.getHero(HERO_ONE_NAME));
        assertEqualsHeroes(expected.get(1), actual.getHero(HERO_TWO_NAME));

    }

    @Test
    public void getHeroesReturnsEmptyCollectionIfEmptyRepository(){
        int expected = 0;
        Assert.assertEquals(expected, actual.getHeroes().size());
    }

    private void assertEqualsHeroes(Hero expected, Hero actual) {
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getLevel(), actual.getLevel());
    }

}
